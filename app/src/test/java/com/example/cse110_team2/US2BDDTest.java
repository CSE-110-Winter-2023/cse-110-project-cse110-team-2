//package com.example.cse110_team2;
//
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.lifecycle.Lifecycle;
//import androidx.test.core.app.ActivityScenario;
//import java.lang.*;
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
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Button;
//import android.widget.TextView;
//
//@RunWith(RobolectricTestRunner.class)
//public class US2BDDTest {
//
//    @Test
//    public void no_saved_locations_test() {
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
//            String locationOne = preferences.getString("locationOneName", "N/A");
//            String locationTwo = preferences.getString("locationTwoName", "N/A");
//            String locationThree = preferences.getString("locationThreeName", "N/A");
//
//            if (locationOne == "N/A" && locationTwo == "N/A" && locationThree == "N/A"){
//                Intent intent = shadowOf(activity).getNextStartedActivity();
//                ShadowIntent shadowIntent = shadowOf(intent);
//                assertEquals(InputLocation.class, shadowIntent.getIntentClass());
//            }
//        });
//
//    }
//
//    @Test
//    public void saving_empty_location() {
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
//                sActivity.findViewById(R.id.location_save_btn).performClick();
//                sActivity.finish();
//
//            });
//
//            assertEquals("N/A", preferences.getString("locationOneName", "N/A"));
//            assertEquals("N/A", preferences.getString("locationOneLat", "N/A"));
//            assertEquals("N/A", preferences.getString("locationOneLon", "N/A"));
//
//            assertEquals(View.INVISIBLE,activity.findViewById(R.id.redImage).getVisibility());
//            assertEquals(View.INVISIBLE,activity.findViewById(R.id.blueImage).getVisibility());
//            assertEquals(View.INVISIBLE,activity.findViewById(R.id.yellowImage).getVisibility());
//            //CHECK TO SEE THAT ABSOLUTELY NO VALUES HAVE BEEN SET AND NONE OF THE IMAGES ARE VISIBLE
//
//        });
//
//    }
//
//    @Test
//    public void test_multiple_locations_proper_relative_heading(){
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
//                locName.setText("Dad's House");
//                locLat.setText("14.20313");
//                locLon.setText("21.32688");
//
//                sActivity.findViewById(R.id.location_save_btn).performClick();
//                sActivity.finish();
//
//            });
//
//
//            activity.findViewById(R.id.new_loc_btn).performClick();
//            intent = shadowOf(activity).getNextStartedActivity();
//            shadowIntent = shadowOf(intent);
//            assertEquals(InputLocation.class, shadowIntent.getIntentClass());
//
//            scenarioInput = ActivityScenario.launch(InputLocation.class);
//
//            scenarioInput.moveToState(Lifecycle.State.CREATED);
//            scenarioInput.moveToState(Lifecycle.State.STARTED);
//
//            scenarioInput.onActivity(sActivity -> {
//                TextView locName = sActivity.findViewById(R.id.location_name);
//                TextView locLat = sActivity.findViewById(R.id.location_lat);
//                TextView locLon = sActivity.findViewById(R.id.location_lon);
//
//                locName.setText("Mom's House");
//                locLat.setText("87.20313");
//                locLon.setText("25.32688");
//
//                sActivity.findViewById(R.id.location_save_btn).performClick();
//                sActivity.finish();
//
//            });
//
//            activity.onResume();
//            assertEquals("Dad's House", preferences.getString("locationOneName", "N/A"));
//            assertEquals("14.20313", preferences.getString("locationOneLat", "N/A"));
//            assertEquals("21.32688", preferences.getString("locationOneLon", "N/A"));
//
//            assertEquals("Mom's House", preferences.getString("locationTwoName", "N/A"));
//            assertEquals("87.20313", preferences.getString("locationTwoLat", "N/A"));
//            assertEquals("25.32688", preferences.getString("locationTwoLon", "N/A"));
//
//            ConstraintLayout.LayoutParams image_param = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.redImage).getLayoutParams();
//            ConstraintLayout.LayoutParams image_param2 = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.blueImage).getLayoutParams();
//
//            assertEquals(55,(int)(image_param.circleAngle), 0.0);
//            assertEquals(1,(int) image_param2.circleAngle, 0.0);
//
//        });
//    }
//}
//
