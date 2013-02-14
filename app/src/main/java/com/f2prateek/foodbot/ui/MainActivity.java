package com.f2prateek.foodbot.ui;

import android.os.Bundle;
import com.f2prateek.foodbot.ui.fragments.LogFragment;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Main Activity for the app
 * On phones, it contains a tabbed pager, displaying the log in one fragment
 * and stats in the other.
 * On tablets, it contains the fragments side by side.
 * <p/>
 * This activity will be notified by LogFragment when user clicks an item, or chooses to add an item.
 * The activity should proceed to show a new activity (on phones) or replace the log fragment (on tablets)
 * for this task.
 */
public class MainActivity extends BaseActivity {

    @Inject
    Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new LogFragment()).commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register ourselves so that we can provide the initial value.
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Always unregister when an object no longer should be on the bus.
        bus.unregister(this);
    }
}
