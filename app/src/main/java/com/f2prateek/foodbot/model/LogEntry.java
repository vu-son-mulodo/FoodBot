package com.f2prateek.foodbot.model;

import android.content.ContentValues;
import android.database.Cursor;

import static com.f2prateek.foodbot.model.FoodBotTable.*;


/**
 * A class representing one long entry
 * TODO : comparators for sorting by date, calories
 */
public class LogEntry {

    private String description;
    private float calories;
    private long date;
    private int _id;

    public LogEntry() {
    }

    public LogEntry(String description, float calories, long date, int id) {
        this.description = description;
        this.calories = calories;
        this.date = date;
        this._id = id;
    }

    public LogEntry(String description, float calories, long date) {
        this.description = description;
        this.calories = calories;
        this.date = date;
        _id = -1; // no id yet
    }

    public static ContentValues entryToCV(LogEntry entry) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, entry.getDescription());
        values.put(COLUMN_CALORIES, entry.getCalories());
        values.put(COLUMN_DATE, entry.getDate());
        return values;
    }

    public static LogEntry cursorToEntry(Cursor cursor) {
        String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
        long date = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
        float calories = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_CALORIES));

        return new LogEntry(description, calories, date, id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

}
