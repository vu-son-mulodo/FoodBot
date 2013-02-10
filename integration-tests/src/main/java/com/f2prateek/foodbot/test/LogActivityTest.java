

package com.f2prateek.foodbot.test;

import android.test.ActivityInstrumentationTestCase2;

import com.f2prateek.foodbot.ui.LogActivity;


/**
 * Test displaying of carousel.
 */
public class LogActivityTest extends ActivityInstrumentationTestCase2<LogActivity> {

    /**
     * Create test for {@link LogActivity}
     */
    public LogActivityTest() {
        super(LogActivity.class);
    }

    /**
     * Verify activity exists
     */
    public void testActivityExists() {
        assertNotNull(getActivity());
    }
}
