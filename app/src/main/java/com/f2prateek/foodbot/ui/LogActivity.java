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

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.f2prateek.foodbot.R;
import com.f2prateek.foodbot.provider.FoodBotContentProvider;
import com.f2prateek.foodbot.provider.FoodBotTable;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;

import static com.f2prateek.foodbot.util.AppConstants.STAT_TOTAL_CALORIES;
import static com.f2prateek.foodbot.util.AppConstants.STAT_TOTAL_DAYS;

/*
 * This is the main acitvity.
 * This activity displays a log to the user. It also allows the user to delete an entry.
 * Entries can be added by from the menu bar.
 * Entries can be deleted with a long click.
 * Stats can be viewed by clicking the disc on the menu bar. 
 *  
 * Derived from http://www.vogella.com/articles/AndroidSQLite/article.html#todo
 */
public class LogActivity extends ListActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    // private Cursor cursor;
    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add border for list view items
        setContentView(R.layout.activity_entry_log);
        this.getListView().setDividerHeight(2);
        fillView();
        registerListView();
    }

    /**
     * Register the listView for CAB. Do all the stuff here, keep it clean.
     */
    private void registerListView() {
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            // TODO : do this for multiple events
            long selection = -1;

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Here you can do something when items are
                // selected/de-selected,
                // such as update the title in the CAB
                if (checked) {
                    selection = id;
                }
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an invalidate() request
                return false;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.log_cab_menu, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.delete_entry:
                        deleteEntry(selection);
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are
                // deselected/unchecked.
            }

        });

    }

    /**
     * Fill the activity's view
     */
    private void fillView() {
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[]{FoodBotTable.COLUMN_CALORIES,
                FoodBotTable.COLUMN_DESCRIPTION, FoodBotTable.COLUMN_DATE};
        // Fields on the UI to which we map
        int[] to = new int[]{R.id.tv_label_calories,
                R.id.tv_label_description, R.id.tv_label_timestamp};

        getLoaderManager().initLoader(0, null, this);
        mAdapter = new SimpleCursorAdapter(this, R.layout.entry_row, null,
                from, to, 0);

        setListAdapter(mAdapter);

    }

    // Opens the second activity if an entry is clicked
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        editEntry(id);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {FoodBotTable.COLUMN_ID,
                FoodBotTable.COLUMN_CALORIES, FoodBotTable.COLUMN_DESCRIPTION,
                FoodBotTable.COLUMN_DATE};
        CursorLoader cursorLoader = new CursorLoader(this,
                FoodBotContentProvider.CONTENT_URI, projection, null, null,
                null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
        saveStats(data);
    }

    /**
     * Saves some stats such as :- total calories consumed, # days of logging
     * and average calories daily.
     */
    private void saveStats(Cursor cursor) {
        Date firstDate = new Date();
        Date lastDate = new Date();
        float calorieCount = 0f;
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Date curDate = new Date();
            long date = cursor.getLong(cursor
                    .getColumnIndexOrThrow(FoodBotTable.COLUMN_DATE));
            curDate.setTime(date);

            calorieCount += cursor.getFloat(cursor
                    .getColumnIndexOrThrow(FoodBotTable.COLUMN_CALORIES));

            if (firstDate.after(curDate)) {
                firstDate = curDate;
            }
            if (lastDate.before(curDate)) {
                lastDate = curDate;
            }

        }

        int totalDays = Days.daysBetween(new DateTime(firstDate),
                new DateTime(lastDate)).getDays();

        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this)
                .edit();
        edit.putFloat(STAT_TOTAL_CALORIES, calorieCount);
        edit.putInt(STAT_TOTAL_DAYS, totalDays);
        edit.commit();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        mAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.log_actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_entry:
                createEntry();
                return true;
            case R.id.show_stats:
                showStats();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Start activity to show stats
     */
    private void showStats() {
        Intent i = new Intent(this, StatsActivity.class);
        startActivity(i);
    }

    /**
     * Start activity to create an entry
     */
    private void createEntry() {
        Intent i = new Intent(this, EntryDetailActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    /**
     * Start activity to edit entry with the given id.
     *
     * @param id
     */
    private void editEntry(long id) {
        Uri entryUri = Uri.parse(FoodBotContentProvider.CONTENT_URI + "/" + id);
        Intent i = new Intent(this, EntryDetailActivity.class);
        i.putExtra(FoodBotContentProvider.CONTENT_ITEM_TYPE, entryUri);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    /**
     * Delete an Entry
     */
    private void deleteEntry(long id) {
        Uri uri = Uri.parse(FoodBotContentProvider.CONTENT_URI + "/" + id);
        getContentResolver().delete(uri, null, null);
        fillView();
        Toast.makeText(this, R.string.entry_deleted, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case ACTIVITY_CREATE:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, R.string.entry_added, Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(this, R.string.entry_not_added, Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case ACTIVITY_EDIT:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, R.string.entry_edited, Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(this, R.string.entry_not_edited, Toast.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

	}

}
