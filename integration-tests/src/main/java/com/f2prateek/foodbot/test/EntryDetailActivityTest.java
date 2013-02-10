package com.f2prateek.foodbot.test;

import android.test.ActivityInstrumentationTestCase2;

import com.f2prateek.foodbot.ui.EntryDetailActivity;


/**
 * Tests for displaying a specific {@link EntryDetailActivity} item
 */
public class EntryDetailActivityTest extends ActivityInstrumentationTestCase2<EntryDetailActivity> {

    /**
     * Create test for {@link EntryDetailActivity}
     */
    public EntryDetailActivityTest() {
        super(EntryDetailActivity.class);
    }

    /**
     * Configure intent used to display a {@link EntryDetailActivity}
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
