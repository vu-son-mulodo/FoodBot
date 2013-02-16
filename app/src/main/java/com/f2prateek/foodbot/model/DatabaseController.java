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
import android.net.Uri;

import java.util.List;

public class DatabaseController {

    public LogEntry getEntry(Uri uri, Context context) {
        String[] projection = FoodBotTable.COLUMNS_ALL;
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null,
                null);

        LogEntry entry;

        if (cursor != null) {
            cursor.moveToFirst();
            entry = new LogEntry(cursor);
            cursor.close();
        } else {
            entry = null;
        }

        return entry;
    }

    public Uri insertEntry(LogEntry entry, Context context) {
        return context.getContentResolver().insert(
                FoodBotContentProvider.CONTENT_URI, entry.toContentValues());
    }

    public int updateEntry(Uri uri, LogEntry entry, Context context) {
        return context.getContentResolver().update(uri, entry.toContentValues(), null, null);
    }

    public List<LogEntry> getAllEntries() {
        return null;
    }

}
