/*
 * Copyright 2013 Prateek Srivastava (@f2prateek)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f2prateek.foodbot.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.f2prateek.foodbot.R;
import com.f2prateek.foodbot.model.DatabaseController;
import com.f2prateek.foodbot.model.FoodBotContentProvider;
import com.f2prateek.foodbot.model.LogEntry;
import com.squareup.timessquare.CalendarPickerView;
import roboguice.inject.InjectView;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

import static com.f2prateek.foodbot.util.LogUtils.makeLogTag;

public class EntryDetailActivity extends BaseActivity {

    @Inject
    DatabaseController dbController;

    private static final String LOGTAG = makeLogTag(EntryDetailActivity.class);
    @InjectView(R.id.entry_edit_description)
    EditText mDescriptionText;
    @InjectView(R.id.entry_edit_calories)
    EditText mCaloriesText;
    @InjectView(R.id.calendar_view)
    CalendarPickerView mDatePicker;

    //Uri of the entry being edited, null if this activity is being used ot create a new entry
    Uri mUri;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_entry_detail);

        Bundle extras = getIntent().getExtras();

        // Check from the saved Instance
        mUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(FoodBotContentProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity
        if (extras != null) {
            mUri = extras
                    .getParcelable(FoodBotContentProvider.CONTENT_ITEM_TYPE);
            fillView(mUri);
        } else {
            initializeDatePicker(new Date());
        }

        inflateDoneDiscardBar();
    }

    /**
     * Inflate a "Done/Discard" custom action bar view. Copied from
     * https://code.google.com/p/romannurik-code/source/browse/misc/donediscard
     */
    private void inflateDoneDiscardBar() {
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_custom_view_done_discard, null);
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (saveEntry()) {
                            finish();
                        }
                    }
                });
        customActionBarView.findViewById(R.id.actionbar_discard).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // "Discard"
                        finish();
                    }
                });

        // Show the custom action bar view and hide the normal Home icon and title.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView, new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * Checks to see if user has entered fields correctly.
     *
     * @return true if entry was successfully validated
     */
    private boolean validateEntry() {
        if (TextUtils.isEmpty(mCaloriesText.getText().toString())) {
            mCaloriesText.setError(getString(R.string.invalid_entry_empty));
            return false;
        }

        if (TextUtils.isEmpty(mDescriptionText.getText().toString())) {
            mDescriptionText.setError(getString(R.string.invalid_entry_empty));
            return false;
        }

        // might not be required since, input type is explicity set to
        // numberDecimal - asserting to be sure
        try {
            Float.parseFloat(mCaloriesText.getText().toString());
        } catch (NumberFormatException e) {
            mCaloriesText.setError(getString(R.string.invalid_entry_number));
            return false;
        }
        return true;
    }

    /**
     * Fill the view with data from the Uri
     * Delegates to {@link #fillView(com.f2prateek.foodbot.model.LogEntry entry)}
     *
     * @param uri Data to populate the view
     */
    private void fillView(Uri uri) {
        fillView(dbController.getEntry(uri, this));
    }

    /**
     * Fill the view with the entry
     *
     * @param {@link LogEntry} Data to populate the view
     */
    private void fillView(LogEntry entry) {
        mDescriptionText.setText(entry.getDescription());
        mCaloriesText.setText(entry.getCalories() + "");
        Date curDate = new Date(entry.getDate());
        initializeDatePicker(curDate);
    }

    /**
     * Initialize the date picker to display a range of one month on either side
     * of curDate
     *
     * @param curDate date to use as selection
     */
    private void initializeDatePicker(Date curDate) {
        Calendar pCal = Calendar.getInstance();
        pCal.setTime(curDate);
        pCal.add(Calendar.MONTH, -1);
        Date previousMonth = pCal.getTime();

        Calendar nCal = Calendar.getInstance();
        nCal.setTime(curDate);
        nCal.add(Calendar.MONTH, +1);
        Date nextMonth = nCal.getTime();

        mDatePicker.init(curDate, previousMonth, nextMonth);
        mDatePicker.smoothScrollToPosition(1);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(FoodBotContentProvider.CONTENT_ITEM_TYPE, mUri);
    }

    /**
     * Save the new values to the database. Only save if entry was updated (i.e
     * add an option to accommodate discard)
     */
    private boolean saveEntry() {
        if (!validateEntry()) {
            showInvalidEntryToast();
            return false;
        }

        String description = mDescriptionText.getText().toString();
        float calories = Float.parseFloat(mCaloriesText.getText().toString());
        long date = mDatePicker.getSelectedDate().getTime();

        LogEntry entry = new LogEntry(description, calories, date);

        if (mUri == null) {
            // New
            mUri = dbController.insertEntry(entry, this);
        } else {
            // Update
            dbController.updateEntry(mUri, entry, this);
        }

        return true;

    }

    /**
     * Shows a {@link Toast} message when user has entered an invalid entry
     */
    private void showInvalidEntryToast() {
        Toast.makeText(this, R.string.invalid_entry, Toast.LENGTH_LONG).show();
    }

}
