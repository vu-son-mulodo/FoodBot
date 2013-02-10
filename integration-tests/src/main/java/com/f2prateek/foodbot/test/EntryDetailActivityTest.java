

package com.f2prateek.foodbot.test;

import static com.f2prateek.foodbot.core.Constants.Extra.NEWS_ITEM;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.f2prateek.foodbot.core.News;
import com.f2prateek.foodbot.ui.EntryDetailActivity;


/**
 * Tests for displaying a specific {@link News} item
 */
public class EntryDetailActivityTest extends ActivityInstrumentationTestCase2<EntryDetailActivity> {

    /**
     * Create test for {@link EntryDetailActivity}
     */
    public EntryDetailActivityTest() {
        super(EntryDetailActivity.class);
    }

    /**
     * Configure intent used to display a {@link News}
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
