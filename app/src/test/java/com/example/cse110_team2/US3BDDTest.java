package com.example.cse110_team2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;

@RunWith(RobolectricTestRunner.class)
public class US3BDDTest {
    /*
    @Test
    public void testScenario1NoLocs() {
        // Uses mocks to test that when a phone is oriented to face the west with no locs the
        // compass is properly rotated.
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch((MainActivity.class));
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);

       MutableLiveData<Float> mockDataSource = new MutableLiveData<Float>();
        scenario.onActivity(activity -> {

            // Launch activity
            MyLocation mockLocation = activity.getLocation();
            mockLocation.setLat(32);
            mockLocation.setLon(-117);

            activity.setOrientationMock(mockDataSource);
            mockDataSource.setValue((float) Math.PI / 2);
            activity.mockCompassUpdate();

            ImageView compass = (ImageView) activity.findViewById(R.id.compassImage);
            float rot = compass.getRotation();
            assertEquals(-90, rot, 0.5);
        });
    }

    @Test
    public void testScenario2_1Locs() {
        // Uses mocks to test that when a phone is oriented to face the west the compass
        // and locations are properly rotated.
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch((MainActivity.class));
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);

        MutableLiveData<Float> mockDataSource = new MutableLiveData<Float>();
        scenario.onActivity(activity -> {
            activity.setOrientationMock(mockDataSource);

            // Launch activity
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

            mockDataSource.setValue(0.0F);
            activity.mockCompassUpdate();

            ImageView locImg = (ImageView) activity.findViewById(R.id.redImage);
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams)
                    locImg.getLayoutParams();
            float ogAngle = lp.circleAngle;


            mockDataSource.setValue((float) -Math.PI / 2);
            activity.mockCompassUpdate();

            ImageView compass = (ImageView) activity.findViewById(R.id.compassImage);
            float rot = compass.getRotation();
            assertEquals(90, rot, 0.5);
            lp = (ConstraintLayout.LayoutParams) locImg.getLayoutParams();
            float newAngle = lp.circleAngle;
            assertEquals(ogAngle - Utilities.radToDeg(-Math.PI/2),
                    newAngle, 0.5);

        });
    }
    
     */
}
