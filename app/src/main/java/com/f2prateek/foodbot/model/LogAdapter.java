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
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.f2prateek.foodbot.R;

import static com.f2prateek.foodbot.model.LogEntry.cursorToEntry;

/**
 * A custom {@link CursorAdapter} that generates the view form a {@link Cursor}.
 */
public class LogAdapter extends CursorAdapter {

    public LogAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_entry, parent, false);
        bindView(v, context, cursor);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        LogEntry entry = cursorToEntry(cursor);

        TextView calories = (TextView) view.findViewById(R.id.tv_label_calories);
        TextView description = (TextView) view.findViewById(R.id.tv_label_description);
        TextView date = (TextView) view.findViewById(R.id.tv_label_timestamp);

        calories.setText(entry.getCalories() + "");
        description.setText(entry.getDescription());
        date.setText(DateUtils.getRelativeTimeSpanString(entry.getDate(), System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS));
    }
}
