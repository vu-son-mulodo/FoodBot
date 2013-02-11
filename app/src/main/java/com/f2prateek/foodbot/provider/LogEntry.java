package com.f2prateek.foodbot.provider;

import org.joda.time.DateTime;

/**
 *
 */
public class LogEntry {

    private String description;
    private float calories;
    private DateTime date;

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

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
