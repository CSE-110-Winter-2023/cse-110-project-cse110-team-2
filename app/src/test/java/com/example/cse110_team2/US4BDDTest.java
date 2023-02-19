package com.example.cse110_team2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;

@RunWith(RobolectricTestRunner.class)
public class US4BDDTest {

    @Test
    public void MultipleCollisionTest(){
        ActivityScenario<MainActivity> scenarioLaunch = ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        // Assert first part of user story that we launch InputLocation activity
        scenarioLaunch.onActivity(activity -> {
            MyLocation mockLocation = activity.getLocation();
            mockLocation.setLat(32);
            mockLocation.setLon(-117);

            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();
            SharedPreferences.Editor editor = preferences.edit();

            String newLocName = "Pheonix";
            String newLocLat = "33.07";
            String newLocLon = "-111.55";
            editor.putString("locationOneName", newLocName);
            editor.putString("locationOneLat", newLocLat);
            editor.putString("locationOneLon", newLocLon);

            newLocName = "Dallas";
            newLocLat = "33.183";
            newLocLon = "-97";
            editor.putString("locationTwoName", newLocName);
            editor.putString("locationTwoLat", newLocLat);
            editor.putString("locationTwoLon", newLocLon);

            newLocName = "Atlanta";
            newLocLat = "34.24";
            newLocLon = "-83.8";
            editor.putString("locationThreeName", newLocName);
            editor.putString("locationThreeLat", newLocLat);
            editor.putString("locationThreeLon", newLocLon);

            editor.apply();

            activity.onResume();
            ConstraintLayout.LayoutParams image_param = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.redImage).getLayoutParams();
            ConstraintLayout.LayoutParams image_param2 = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.blueImage).getLayoutParams();
            ConstraintLayout.LayoutParams image_param3 = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.yellowImage).getLayoutParams();
            float scale = activity.getResources().getDisplayMetrics().density;

            assertEquals(image_param.circleAngle, image_param2.circleAngle, 10);
            assertEquals(image_param.circleAngle, image_param3.circleAngle, 10);
            assertEquals(image_param2.circleAngle, image_param3.circleAngle, 10);
            assertNotEquals(image_param.circleRadius, image_param2.circleRadius, 9 * scale);
            assertNotEquals(image_param2.circleRadius, image_param3.circleRadius, 9 * scale);
            assertNotEquals(image_param.circleRadius, image_param3.circleRadius, 9 * scale);

        });

    }


    @Test
    public void SingleCollisionsTest(){
        ActivityScenario<MainActivity> scenarioLaunch = ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        // Assert first part of user story that we launch InputLocation activity
        scenarioLaunch.onActivity(activity -> {
            MyLocation mockLocation = activity.getLocation();
            mockLocation.setLat(32);
            mockLocation.setLon(-117);

            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();
            SharedPreferences.Editor editor = preferences.edit();

            String newLocName = "Albuquerque";
            String newLocLat = "35.15";
            String newLocLon = "-107";
            editor.putString("locationOneName", newLocName);
            editor.putString("locationOneLat", newLocLat);
            editor.putString("locationOneLon", newLocLon);

            newLocName = "Pheonix";
            newLocLat = "33.63";
            newLocLon = "-111";
            editor.putString("locationTwoName", newLocName);
            editor.putString("locationTwoLat", newLocLat);
            editor.putString("locationTwoLon", newLocLon);

            newLocName = "Istanbul";
            newLocLat = "41";
            newLocLon = "28";
            editor.putString("locationThreeName", newLocName);
            editor.putString("locationThreeLat", newLocLat);
            editor.putString("locationThreeLon", newLocLon);

            editor.apply();
            activity.onResume();

            ConstraintLayout.LayoutParams image_param = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.redImage).getLayoutParams();
            ConstraintLayout.LayoutParams image_param2 = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.blueImage).getLayoutParams();
            ConstraintLayout.LayoutParams image_param3 = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.yellowImage).getLayoutParams();
            float scale = activity.getResources().getDisplayMetrics().density;

            assertEquals(image_param.circleAngle, image_param2.circleAngle, 10);
            assertNotEquals(image_param.circleAngle, image_param3.circleAngle, 10);
            assertNotEquals(image_param.circleRadius, image_param2.circleRadius, 10 * scale);
        });

    }

    @Test
    public void noCollisionTest(){
        ActivityScenario<MainActivity> scenarioLaunch = ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        // Assert first part of user story that we launch InputLocation activity
        scenarioLaunch.onActivity(activity -> {
            MyLocation mockLocation = activity.getLocation();
            mockLocation.setLat(32);
            mockLocation.setLon(-117);

            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();
            SharedPreferences.Editor editor = preferences.edit();

            String newLocName = "Hawaii";
            String newLocLat = "20.28";
            String newLocLon = "-158";
            editor.putString("locationOneName", newLocName);
            editor.putString("locationOneLat", newLocLat);
            editor.putString("locationOneLon", newLocLon);

            newLocName = "Cancun";
            newLocLat = "21.15";
            newLocLon = "-86.5";
            editor.putString("locationTwoName", newLocName);
            editor.putString("locationTwoLat", newLocLat);
            editor.putString("locationTwoLon", newLocLon);

            newLocName = "Istanbul";
            newLocLat = "41";
            newLocLon = "28";
            editor.putString("locationThreeName", newLocName);
            editor.putString("locationThreeLat", newLocLat);
            editor.putString("locationThreeLon", newLocLon);

            editor.apply();

            activity.onResume();
            ConstraintLayout.LayoutParams image_param = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.redImage).getLayoutParams();
            ConstraintLayout.LayoutParams image_param2 = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.blueImage).getLayoutParams();
            ConstraintLayout.LayoutParams image_param3 = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.yellowImage).getLayoutParams();

            assertNotEquals(image_param.circleAngle, image_param2.circleAngle, 10);
            assertNotEquals(image_param.circleAngle, image_param3.circleAngle, 10);
            assertNotEquals(image_param2.circleAngle, image_param3.circleAngle, 10);
            assertEquals(image_param.circleRadius, image_param2.circleRadius);
            assertEquals(image_param2.circleRadius, image_param3.circleRadius);

        });

    }
}
