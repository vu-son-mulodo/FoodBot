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

import android.view.View;
import android.widget.EditText;
import com.f2prateek.foodbot.R;
import com.f2prateek.foodbot.ui.EntryDetailActivity;
import com.squareup.spoon.Spoon;
import com.squareup.timessquare.CalendarPickerView;

import static org.fest.assertions.api.ANDROID.assertThat;


/**
 * Tests for displaying a specific {@link com.f2prateek.foodbot.ui.MainActivity} item
 */
public class EntryDetailActivityTest extends ActivityTest<EntryDetailActivity> {

    private EditText description;
    private EditText calories;
    private CalendarPickerView picker;
    private View done;

    public EntryDetailActivityTest() {
        super(EntryDetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        description = (EditText) activity.findViewById(R.id.entry_edit_description);
        calories = (EditText) activity.findViewById(R.id.entry_edit_calories);
        picker = (CalendarPickerView) activity.findViewById(R.id.calendar_view);
        done = activity.findViewById(R.id.actionbar_done);
    }

    /**
     * Verify failure when entries are incomplete.
     */
    public void testEmptyEntry() {
        Spoon.screenshot(getActivity(), "initial_state");

        // Make sure the initial state does not show any errors.
        assertThat(description).hasNoError();
        assertThat(calories).hasNoError();

        // Click the "done" button.
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                done.performClick();
            }
        });
        instrumentation.waitForIdleSync();

        Spoon.screenshot(activity, "empty_form_error");
        assertThat(description).hasError(R.string.invalid_entry_empty);
        assertThat(calories).hasError(R.string.invalid_entry_empty);
    }

    /**
     * Verify failure when only calories are incomplete
     */
    public void testEntryEmptyCalories() {
        Spoon.screenshot(getActivity(), "initial_state");

        // Make sure the initial state does not show any errors.
        assertThat(calories).hasNoError();
        assertThat(description).hasNoError();

        // Click the "login" button.
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                description.setText("test_description");
            }
        });
        instrumentation.waitForIdleSync();

        Spoon.screenshot(activity, "set_description");

        // Click the "done" button.
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                done.performClick();
            }
        });
        instrumentation.waitForIdleSync();

        Spoon.screenshot(activity, "empty_form_error");
        assertThat(description).hasNoError();
        assertThat(calories).hasError(R.string.invalid_entry_empty);
    }

    /**
     * Verify failure when only description is incomplete
     */
    public void testEntryEmptyDescription() {
        Spoon.screenshot(getActivity(), "initial_state");

        // Make sure the initial state does not show any errors.
        assertThat(calories).hasNoError();
        assertThat(description).hasNoError();

        // Click the "login" button.
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                calories.setText("23.54");
            }
        });
        instrumentation.waitForIdleSync();

        Spoon.screenshot(activity, "set_calories");

        // Click the "done" button.
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                done.performClick();
            }
        });
        instrumentation.waitForIdleSync();

        Spoon.screenshot(activity, "empty_form_error");
        assertThat(calories).hasNoError();
        assertThat(description).hasError(R.string.invalid_entry_empty);
    }

    /**
     * Verify failure on incorrect calories
     */
    public void testInvalidCalories() {
        Spoon.screenshot(getActivity(), "initial_state");

        // Make sure the initial state does not show any errors.
        assertThat(calories).hasNoError();
        assertThat(description).hasNoError();

        // Click the "login" button.
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                calories.setText("not a float!");
                description.setText("test");
            }
        });
        instrumentation.waitForIdleSync();

        Spoon.screenshot(activity, "set_data");

        // Click the "done" button.
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                done.performClick();
            }
        });
        instrumentation.waitForIdleSync();

        Spoon.screenshot(activity, "invalid_form_error");
        assertThat(calories).hasError(R.string.invalid_entry_number);
        assertThat(description).hasNoError();
    }

}
