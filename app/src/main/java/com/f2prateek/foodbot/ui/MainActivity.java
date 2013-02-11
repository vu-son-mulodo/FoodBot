package com.f2prateek.foodbot.ui;

import android.os.Bundle;
import com.f2prateek.foodbot.R;

/**
 * Main Activity for the app
 * On phones, it contains a tabbed pager, displaying the log in one fragment
 * and stats in the other.
 * On tablets, it contains the fragments side by side.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }
}
