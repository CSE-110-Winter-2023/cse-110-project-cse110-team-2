//package com.example.cse110_team2;
//
//import androidx.lifecycle.Lifecycle;
//import androidx.test.core.app.ActivityScenario;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.shadows.ShadowIntent;
//
//import static org.junit.Assert.*;
//import static org.robolectric.Shadows.shadowOf;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.widget.TextView;
//
//@RunWith(RobolectricTestRunner.class)
//public class US1BDDTest {
//
//    @Test
//    public void us1_bdd_save_loc()   {
//        // Set up activities
//        ActivityScenario<MainActivity> scenarioLaunch = ActivityScenario.launch(MainActivity.class);
//
//        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
//        scenarioLaunch.moveToState(Lifecycle.State.STARTED);
//
//        // Assert first part of user story that we launch InputLocation activity
//        scenarioLaunch.onActivity(activity -> {
//            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
//            preferences.edit().clear().commit();
//
//            activity.findViewById(R.id.new_loc_btn).performClick();
//
//            Intent intent = shadowOf(activity).getNextStartedActivity();
//            ShadowIntent shadowIntent = shadowOf(intent);
//            assertEquals(InputLocation.class, shadowIntent.getIntentClass());
//
//            ActivityScenario<InputLocation> scenarioInput = ActivityScenario.launch(InputLocation.class);
//
//            scenarioInput.moveToState(Lifecycle.State.CREATED);
//            scenarioInput.moveToState(Lifecycle.State.STARTED);
//
//            scenarioInput.onActivity(sActivity -> {
//                TextView locName = sActivity.findViewById(R.id.location_name);
//                TextView locLat = sActivity.findViewById(R.id.location_lat);
//                TextView locLon = sActivity.findViewById(R.id.location_lon);
//
//                locName.setText("My House");
//                locLat.setText("14.20313");
//                locLon.setText("21.32688");
//
//                sActivity.findViewById(R.id.location_save_btn).performClick();
//                sActivity.finish();
//
//            });
//
//            assertEquals("My House", preferences.getString("locationOneName", "N/A"));
//            assertEquals("14.20313", preferences.getString("locationOneLat", "N/A"));
//            assertEquals("21.32688", preferences.getString("locationOneLon", "N/A"));
//        });
//
//
//    }
//}
