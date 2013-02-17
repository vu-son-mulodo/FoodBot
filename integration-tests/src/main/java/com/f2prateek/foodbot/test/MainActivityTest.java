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

package com.f2prateek.foodbot.test;

import android.widget.ListView;
import com.f2prateek.foodbot.model.DatabaseController;
import com.f2prateek.foodbot.ui.MainActivity;
import com.squareup.spoon.Spoon;

import static org.fest.assertions.api.ANDROID.assertThat;

/**
 * Tests for displaying a specific {@link MainActivity} item
 */
public class MainActivityTest extends ActivityTest<MainActivity> {

    DatabaseController dbController;
    private ListView list;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        list = (ListView) activity.findViewById(android.R.id.list);
        dbController = new DatabaseController();
    }

    /**
     * Verify activity displays data
     * TODO : automatically add data
     */
    public void testMainActivityNoData() {
        Spoon.screenshot(activity, "loading");
        assertThat(list).hasCount(0);
        Spoon.screenshot(activity, "done");
    }


}
