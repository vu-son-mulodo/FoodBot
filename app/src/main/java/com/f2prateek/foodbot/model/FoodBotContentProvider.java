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
package com.f2prateek.foodbot.model;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

import static com.f2prateek.foodbot.util.LogUtils.makeLogTag;

/*
 * Content Provider.
 * Not required, but helpful in extending the application to provide data to other apps.
 * Derived from http://www.vogella.com/articles/AndroidSQLite/article.html#todo
 */
public class FoodBotContentProvider extends ContentProvider {

    private static final String LOGTAG = makeLogTag(FoodBotContentProvider.class);

    private FoodBotDatabaseHelper database;

    // Used for the UriMacher
    private static final int LOG_ENTRY = 10;
    private static final int LOG_ENTRY_ID = 20;

    private static final String AUTHORITY = "com.f2prateek.foodbot.model";

    private static final String BASE_PATH = "foodbot_entries";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/log_entries";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/log_entry";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, LOG_ENTRY);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", LOG_ENTRY_ID);
    }

    @Override
    public boolean onCreate() {
        database = new FoodBotDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(FoodBotTable.TABLE_FOODBOT);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case LOG_ENTRY:
                break;
            case LOG_ENTRY_ID:
                // Adding the ID to the original query
                queryBuilder.appendWhere(FoodBotTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case LOG_ENTRY:
                id = sqlDB.insert(FoodBotTable.TABLE_FOODBOT, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case LOG_ENTRY:
                rowsDeleted = sqlDB.delete(FoodBotTable.TABLE_FOODBOT, selection,
                        selectionArgs);
                break;
            case LOG_ENTRY_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(FoodBotTable.TABLE_FOODBOT,
                            FoodBotTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB
                            .delete(FoodBotTable.TABLE_FOODBOT,
                                    FoodBotTable.COLUMN_ID + "=" + id + " and "
                                            + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case LOG_ENTRY:
                rowsUpdated = sqlDB.update(FoodBotTable.TABLE_FOODBOT, values,
                        selection, selectionArgs);
                break;
            case LOG_ENTRY_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(FoodBotTable.TABLE_FOODBOT, values,
                            FoodBotTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB
                            .update(FoodBotTable.TABLE_FOODBOT, values,
                                    FoodBotTable.COLUMN_ID + "=" + id + " and "
                                            + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    /**
     * Checks that all the columns in this projection are present in
     * {@link FoodBotTable}
     *
     * @param projection
     */
    private void checkColumns(String[] projection) {
        String[] available = {FoodBotTable.COLUMN_CALORIES,
                FoodBotTable.COLUMN_DATE, FoodBotTable.COLUMN_DESCRIPTION,
                FoodBotTable.COLUMN_ID};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // Check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }

}
