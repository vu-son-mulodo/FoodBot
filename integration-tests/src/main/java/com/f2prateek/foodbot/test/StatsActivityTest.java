

package com.f2prateek.foodbot.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import com.f2prateek.foodbot.ui.StatsActivity;
import com.f2prateek.foodbot.R;


/**
 * Tests of displaying the authenticator activity
 */
public class StatsActivityTest extends ActivityInstrumentationTestCase2<StatsActivity> {

    /**
     * Create test for {@link com.f2prateek.foodbot.authenticator.BootstrapAuthenticatorActivity}
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
