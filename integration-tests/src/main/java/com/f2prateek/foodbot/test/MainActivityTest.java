package com.f2prateek.foodbot.test;

import android.test.ActivityInstrumentationTestCase2;

import com.f2prateek.foodbot.ui.MainActivity;


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
     * Configure intent used to display a {@link MainActivity}
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
    public void testActivityExists() {
        assertNotNull(getActivity());
    }
}
