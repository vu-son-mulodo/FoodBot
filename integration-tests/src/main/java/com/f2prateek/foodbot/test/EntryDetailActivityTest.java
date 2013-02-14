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

import android.test.ActivityInstrumentationTestCase2;
import com.f2prateek.foodbot.ui.EntryDetailActivity;
import com.squareup.spoon.Spoon;


/**
 * Tests for displaying a specific {@link com.f2prateek.foodbot.ui.MainActivity} item
 */
public class EntryDetailActivityTest extends ActivityInstrumentationTestCase2<EntryDetailActivity> {

    /**
     * Create test for {@link com.f2prateek.foodbot.ui.EntryDetailActivity}
     */
    public EntryDetailActivityTest() {
        super(EntryDetailActivity.class);
    }

    /**
     * Configure intent used to display a {@link com.f2prateek.foodbot.ui.EntryDetailActivity}
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Intent intent = new Intent();
        // setActivityIntent(intent);
    }

    /**
     * Verify activity exists
     */
    public void testEntryDetailActivityExists() {
        Spoon.screenshot(getActivity(), "initial_state_entry");
        assertNotNull(getActivity());
    }
}
