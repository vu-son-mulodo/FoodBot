package com.f2prateek.foodbot.ui;

import static com.f2prateek.foodbot.util.AppConstants.STAT_TOTAL_CALORIES;
import static com.f2prateek.foodbot.util.AppConstants.STAT_TOTAL_DAYS;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.f2prateek.foodbot.R;

// This activity simply shows some stats the user
// TODO : add graphs + clean design
public class StatsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		float totalCalories = prefs.getFloat(STAT_TOTAL_CALORIES, 0f);
		float totalDays = prefs.getInt(STAT_TOTAL_DAYS, 0);

		TextView tvCalories = (TextView) findViewById(R.id.tv_label_total_calories);
		tvCalories.setText("Total calorie count is " + totalCalories);

		TextView tvTotalDays = (TextView) findViewById(R.id.tv_label_total_days);
		tvTotalDays.setText("Total days passed is " + totalDays);

		TextView tvAverage = (TextView) findViewById(R.id.tv_label_average_consumption);
		float average = totalDays == 0f ? 0f : (totalCalories / totalDays);
		tvAverage.setText("Average daily calorie intake is " + average);

	}

}
