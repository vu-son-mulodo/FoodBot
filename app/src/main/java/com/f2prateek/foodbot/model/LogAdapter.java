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
 *
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
        date.setText(DateUtils.getRelativeTimeSpanString(entry.getDate(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));
    }
}
