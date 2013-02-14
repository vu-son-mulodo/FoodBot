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
