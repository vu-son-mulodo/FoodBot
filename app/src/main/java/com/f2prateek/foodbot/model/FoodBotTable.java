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

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.f2prateek.foodbot.util.LogUtils.makeLogTag;

/*
 * Table utility class. All logic to upgrading database version should go here.
 * Used by {@link com.f2prateek.foodbot.provider.FoodBotContentProvider}
 * Derived from http://www.vogella.com/articles/AndroidSQLite/article.html#todo
 */
public class FoodBotTable {

    // Database table
    public static final String TABLE_FOODBOT = "foodbot";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String[] COLUMNS_ALL = {COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_DATE, COLUMN_CALORIES};
    private static final String LOGTAG = makeLogTag(FoodBotTable.class);
    // Database creation SQL statement
    //@formatter:off
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FOODBOT
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_CALORIES + " float not null, "
            + COLUMN_DESCRIPTION + " text not null, "
            + COLUMN_DATE + " long not null"
            + ");";
    //@formatter:on

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.d(LOGTAG, "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODBOT);
        onCreate(database);
    }

}