package com.example.cse110_team2;

import static org.junit.Assert.assertEquals;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class InputLocationTest {
/*
    @Test
    public void testAddSingleLocation(){



        ActivityScenario<InputLocation> scenario = ActivityScenario.launch((InputLocation.class));
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();

            TextView location_name = (TextView) activity.findViewById(R.id.location_name);
            TextView location_lat = (TextView) activity.findViewById(R.id.location_lat);
            TextView location_lon = (TextView) activity.findViewById(R.id.location_lon);
            Button save = (Button) activity.findViewById(R.id.location_save_btn);

            location_name.setText("HOME");
            location_lat.setText("12345");
            location_lon.setText("90.76");
            save.performClick();

            assertEquals("HOME", preferences.getString("locationOneName", "N/A"));
            assertEquals("12345", preferences.getString("locationOneLat", "N/A"));
            assertEquals("90.76", preferences.getString("locationOneLon", "N/A"));


        });

    }
*/
}
