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

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;

@RunWith(RobolectricTestRunner.class)
public class RotateCompassTest {
    /*
    @Test
    public void testRotateImgNorthToEast() {
        // Tests phone rotation to the right 90 (phone is now facing east)
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch((MainActivity.class));
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

            ImageView img = (ImageView) activity.findViewById(R.id.compassImage);
            float rot = img.getRotation();
            assertEquals(rot, 0, 0.5);

            // Rotate north to east so mocked azimuth will be
            activity.rotateImg(img, (float) (Math.PI / 2));
            rot = img.getRotation();
            assertEquals(-90, rot, 0.5); // Expect to be rotated 90 counterclock
            // wise to have correct north
            // orientation

        });
    }

    @Test
    public void testRotateImgNorthToWest() {
        // Tests phone rotation to the left 90 (phone is now facing west)
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch((MainActivity.class));
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

            ImageView img = (ImageView) activity.findViewById(R.id.compassImage);
            float rot = img.getRotation();
            assertEquals(rot, 0, 0.5);

            activity.rotateImg(img, (float) -(Math.PI / 2));
            rot = img.getRotation();
            assertEquals(90, rot, 0.5);  // Expect to rotate 90 clockwise to be
            // correct rotation

        });
    }

    @Test
    public void testRotateImg45DegsRight() {
        // Tests phone rotation to the right 45 (phone is now facing north east)
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch((MainActivity.class));
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

            ImageView img = (ImageView) activity.findViewById(R.id.compassImage);
            float rot = img.getRotation();
            assertEquals(rot, 0, 0.5);

            // Rotate north to east so mocked azimuth will be
            activity.rotateImg(img, (float) (Math.PI / 4));
            rot = img.getRotation();
            assertEquals(-45, rot, 0.5); // Expect to be rotated 45 counterclock
            // wise to have correct north
            // orientation

        });
    }

    @Test
    public void testRotateImgNoRotation() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch((MainActivity.class));
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

            ImageView img = (ImageView) activity.findViewById(R.id.compassImage);
            float rot = img.getRotation();
            assertEquals(rot, 0, 0.5);

            // Rotate north to east so mocked azimuth will be
            activity.rotateImg(img, 0.0F);
            rot = img.getRotation();
            assertEquals(0, rot, 0.5);
        });
    }

     */
}