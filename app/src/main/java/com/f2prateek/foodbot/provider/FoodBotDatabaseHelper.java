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
package com.f2prateek.foodbot.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* SQL Database Helper class.
 * Used by {@link com.f2prateek.foodbot.provider.FoodBotContentProvider}
 * Derived from http://www.vogella.com/articles/AndroidSQLite/article.html#todo
 */
public class FoodBotDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "foodbot.db";
	private static final int DATABASE_VERSION = 1;

	public FoodBotDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		FoodBotTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		FoodBotTable.onUpgrade(db, oldVersion, newVersion);
	}

}
