/*******************************************************************************
 * Copyright 2013 Prateek Srivastava (@f2prateek)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.f2prateek.foodbot.ui;


import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.f2prateek.foodbot.R;
import com.f2prateek.foodbot.provider.FoodBotContentProvider;
import com.f2prateek.foodbot.provider.FoodBotTable;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import static com.f2prateek.foodbot.util.LogUtils.makeLogTag;

/*
 * This activity manages adding and updating entries.
 * 
 * Code for Done/Discard Bar implemented from https://code.google.com/p/romannurik-code/source/browse/misc/donediscard
 * 
 * Derived from http://www.vogella.com/articles/AndroidSQLite/article.html#todo
 */
public class EntryDetailActivity extends Activity implements View.OnClickListener {

    private static final String LOGTAG = makeLogTag(EntryDetailActivity.class);
    private Uri todoUri;
    // View elements
    private EditText mDescriptionText;
    private EditText mCaloriesText;
    private EditText mCaloriesPerServingText;
    private EditText mServingsText;
    private CalendarPickerView mDatePicker;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_entry_detail);

        mCaloriesText = (EditText) findViewById(R.id.entry_edit_calories);
        mDescriptionText = (EditText) findViewById(R.id.entry_edit_description);
        mCaloriesPerServingText = (EditText) findViewById(R.id.entry_edit_calories_per_serving);
        mServingsText = (EditText) findViewById(R.id.entry_edit_servings);
        mDatePicker = (CalendarPickerView) findViewById(R.id.calendar_view);

        View iv_equal = findViewById(R.id.entry_iv_equal);
        iv_equal.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();

        // Check from the saved Instance
        todoUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(FoodBotContentProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity
        if (extras != null) {
            todoUri = extras
                    .getParcelable(FoodBotContentProvider.CONTENT_ITEM_TYPE);

            fillView(todoUri);
        } else {
            initializeDatePicker(new Date());
        }

        inflateDoneDiscardBar();
    }

    /**
     * Inflate a "Done/Discard" custom action bar view. Copied from
     * https://code.google.com/p/romannurik-code/source/browse/misc/donediscard
     * TODO : discard part
     */
    private void inflateDoneDiscardBar() {

        LayoutInflater inflater = (LayoutInflater) getActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_custom_view_done_discard, null);
        customActionBarView.findViewById(R.id.actionbar_done)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!validateEntry()) {
                            showInvalidEntryCrouton();
                        } else {
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });

        // Show the custom action bar view and hide the normal Home icon and
        // title.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * Checks to see if user has entered fields correctly.
     *
     * @return true if entry was successfully validated
     */
    private boolean validateEntry() {
        if (TextUtils.isEmpty(mCaloriesText.getText().toString())
                || TextUtils.isEmpty(mDescriptionText.getText().toString())) {
            return false;
        }

        // might not be required since, input type is explicity set to
        // numberDecimal - asserting to be sure
        try {
            Float.parseFloat(mCaloriesText.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Fill the view with data from the Uri
     *
     * @param uri Data to populate the view
     */
    private void fillView(Uri uri) {
        String[] projection = {FoodBotTable.COLUMN_DESCRIPTION,
                FoodBotTable.COLUMN_CALORIES, FoodBotTable.COLUMN_DATE};
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();

            mDescriptionText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(FoodBotTable.COLUMN_DESCRIPTION)));
            mCaloriesText.setText(cursor.getFloat(cursor
                    .getColumnIndexOrThrow(FoodBotTable.COLUMN_CALORIES)) + "");

            Date curDate = new Date(cursor.getLong(cursor
                    .getColumnIndexOrThrow(FoodBotTable.COLUMN_DATE)));
            initializeDatePicker(curDate);

            // Always close the cursor
            cursor.close();
        }
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
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();

        outState.putParcelable(FoodBotContentProvider.CONTENT_ITEM_TYPE,
                todoUri);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    /**
     * Save the new values to the database. Only save if entry was updated (i.e
     * add an option to accommodate discard)
     */
    private void saveState() {
        Log.d(LOGTAG, "saveState()");

        if (!validateEntry()) {
            setResult(RESULT_CANCELED);
            return;
        }

        String description = mDescriptionText.getText().toString();
        float calories = Float.parseFloat(mCaloriesText.getText().toString());
        long date = mDatePicker.getSelectedDate().getTime();

        ContentValues values = new ContentValues();
        values.put(FoodBotTable.COLUMN_DESCRIPTION, description);
        values.put(FoodBotTable.COLUMN_CALORIES, calories);
        values.put(FoodBotTable.COLUMN_DATE, date);

        if (todoUri == null) {
            // New todo
            todoUri = getContentResolver().insert(
                    FoodBotContentProvider.CONTENT_URI, values);
        } else {
            // Update todo
            getContentResolver().update(todoUri, values, null, null);
        }
    }

    /**
     * Shows a Crouton message when user has entered an invalid entry
     */
    private void showInvalidEntryCrouton() {
        Toast.makeText(this, R.string.invalid_entry, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.entry_iv_equal) {
            calculateCalories();
        }
    }

    /**
     * Calculate the calories and update the text.
     */
    private void calculateCalories() {
        try {
            int servings = Integer.parseInt(mServingsText.getText().toString());
            float calores_per_serving = Float
                    .parseFloat(mCaloriesPerServingText.getText().toString());

            float calories = servings * calores_per_serving;

            mCaloriesText.setText(calories + "");
        } catch (NumberFormatException e) {
            showInvalidEntryCrouton();
        }
    }
}
