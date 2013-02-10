

package com.f2prateek.foodbot.test;

import android.test.ActivityInstrumentationTestCase2;
import com.f2prateek.foodbot.ui.StatsActivity;


/**
 * Tests of displaying the authenticator activity
 */
public class StatsActivityTest extends ActivityInstrumentationTestCase2<StatsActivity> {

    /**
     * Create test for {@link StatsActivity}
     */
    public StatsActivityTest() {
        super(StatsActivity.class);
    }

    /**
     * Verify activity exists
     */
    public void testActivityExists() {
        assertNotNull(getActivity());
    }
}
