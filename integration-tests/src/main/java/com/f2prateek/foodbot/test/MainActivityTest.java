package com.f2prateek.foodbot.test;

import android.test.ActivityInstrumentationTestCase2;

import com.f2prateek.foodbot.ui.MainActivity;
import com.squareup.spoon.Spoon;


/**
 * Tests for displaying a specific {@link MainActivity} item
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    /**
     * Create test for {@link MainActivity}
     */
    public MainActivityTest() {
        super(MainActivity.class);
    }

    /**
     * Verify activity exists
     */
    public void testMainActivityExists() {
        Spoon.screenshot(getActivity(), "initial_state");
        assertNotNull(getActivity());
    }
}
