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

package com.f2prateek.foodbot.ui.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.f2prateek.foodbot.R;
import com.f2prateek.foodbot.model.DatabaseModel;
import com.f2prateek.foodbot.model.DatabaseView;
import com.f2prateek.foodbot.model.FoodBotContentProvider;
import com.f2prateek.foodbot.model.LogAdapter;
import com.f2prateek.foodbot.ui.EntryDetailActivity;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockListFragment;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import static com.f2prateek.foodbot.util.LogUtils.makeLogTag;

/**
 * Fragment that displays a log to the user.
 */
public class LogFragment extends RoboSherlockListFragment implements
        DatabaseView<DatabaseModel> {

    private static final String LOGTAG = makeLogTag(LogFragment.class);
    private static final int LOG_LOADER_ID = 1;
    @Inject
    Bus bus;
    private LogAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        fillView();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getSherlockActivity(), EntryDetailActivity.class);
        Uri uri = Uri.parse(FoodBotContentProvider.CONTENT_URI + "/" + id);
        i.putExtra(FoodBotContentProvider.CONTENT_ITEM_TYPE, uri);
        startActivity(i);
    }

    private void fillView() {
        mAdapter = new LogAdapter(getSherlockActivity(), null, false);
        setEmptyText(getSherlockActivity().getResources().getString(R.string.no_entries));
        setListAdapter(mAdapter);
        setListShown(false);
        DatabaseModel dbModel = DatabaseModel.getInstance(getSherlockActivity());
        dbModel.addView(this, getSherlockActivity());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_log, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_add_entry:
                addEntry();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // starts activity to add a new {@link LogEntry}
    private void addEntry() {
        Intent i = new Intent(getSherlockActivity(), EntryDetailActivity.class);
        startActivity(i);
    }

    @Override
    public void update(DatabaseModel model, Cursor data) {
        mAdapter.swapCursor(data);
        if (data != null) {
            setListShown(true);
        } else {
            setListShown(false);
        }
    }
}
