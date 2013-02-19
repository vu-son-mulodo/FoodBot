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

package com.f2prateek.foodbot.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;

public class DatabaseModel<V extends DatabaseView> implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOG_LOADER_ID = 13;
    private static DatabaseModel instance;
    private ArrayList<V> views;
    // Don't make this static
    private Context context;

    public DatabaseModel(Context context) {
        views = new ArrayList<V>();
    }

    //Singleton provider, attaches to application context regardless of c
    public static DatabaseModel getInstance(Context c) {
        if (instance == null) {
            instance = new DatabaseModel(c.getApplicationContext());
        }
        return instance;
    }

    public void addView(V view, Context context) {
        if (!views.contains(view)) {
            views.add(view);
        }
        this.context = context;
        view.getLoaderManager().initLoader(LOG_LOADER_ID, null, this);
    }

    public void deleteView(V view) {
        views.remove(view);
    }

    public void notifyViews(Cursor data) {
        for (V view : views) {
            view.update(this, data);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = FoodBotTable.COLUMNS_ALL;
        CursorLoader cursorLoader = new CursorLoader(context,
                FoodBotContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        notifyViews(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        notifyViews(null);
    }
}
