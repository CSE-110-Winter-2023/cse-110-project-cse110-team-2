package com.example.cse110_team2.UnitTests;

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

import com.example.cse110_team2.MainActivity;

import java.util.HashMap;

@RunWith(RobolectricTestRunner.class)
public class RotateUnitTests {

    @Test
    public void rotateEastTest() {
        // Tests phone rotation to the right 90 (phone is now facing east)
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch((MainActivity.class));
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);

        MutableLiveData<Float> mockDataSource = new MutableLiveData<Float>();

        scenario.onActivity(activity -> {
            activity.setOrientationMock(mockDataSource);
            activity.setMock(true);

            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();

            mockDataSource.setValue(0.0F);
            activity.mockCompassUpdate();
            var friendList = activity.getFriendManager().getMockFriends();

            // TODO penis
            for (int i = 0; i < friendList.size(); i++) {
                var curr_friend = friendList.get(i);

                HashMap<String, View> friendViews = activity.friendMap.get(curr_friend.uid);

                View dotView = friendViews.get("dot");
                View nameView = friendViews.get("text");

                ConstraintLayout.LayoutParams dotLayout =
                        (ConstraintLayout.LayoutParams) dotView.getLayoutParams();
                var nameLayout = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();

                float ogDotAngle = dotLayout.circleAngle;
                float ogNameAngle = nameLayout.circleAngle;

                mockDataSource.setValue((float) Math.PI / 2);
                activity.mockCompassUpdate();

                ConstraintLayout.LayoutParams dotLayoutUpdated =
                        (ConstraintLayout.LayoutParams) dotView.getLayoutParams();
                var nameLayoutUpdated = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();

                float newDotAngle = dotLayoutUpdated.circleAngle;
                float newNameAngle = nameLayoutUpdated.circleAngle;

                assertEquals(ogDotAngle - Math.toDegrees(-Math.PI / 2), newDotAngle, 0.5);
                assertEquals(ogNameAngle - Math.toDegrees(-Math.PI / 2), newNameAngle, 0.5);


                mockDataSource.setValue(0.0F);
                activity.mockCompassUpdate();
            }
        });
    }

    @Test
    public void rotateSouthTest() {
        // Tests phone rotation to the right 90 (phone is now facing east)
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch((MainActivity.class));
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);

        MutableLiveData<Float> mockDataSource = new MutableLiveData<Float>();

        scenario.onActivity(activity -> {
            activity.setOrientationMock(mockDataSource);
            activity.setMock(true);

            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();

            mockDataSource.setValue(0.0F);
            activity.mockCompassUpdate();
            var friendList = activity.getFriendManager().getMockFriends();

            // TODO penis
            for (int i = 0; i < friendList.size(); i++) {
                var curr_friend = friendList.get(i);

                HashMap<String, View> friendViews = activity.friendMap.get(curr_friend.uid);

                View dotView = friendViews.get("dot");
                View nameView = friendViews.get("text");

                ConstraintLayout.LayoutParams dotLayout =
                        (ConstraintLayout.LayoutParams) dotView.getLayoutParams();
                var nameLayout = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();

                float ogDotAngle = dotLayout.circleAngle;
                float ogNameAngle = nameLayout.circleAngle;

                mockDataSource.setValue((float) Math.PI);
                activity.mockCompassUpdate();

                ConstraintLayout.LayoutParams dotLayoutUpdated =
                        (ConstraintLayout.LayoutParams) dotView.getLayoutParams();
                var nameLayoutUpdated = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();

                float newDotAngle = dotLayoutUpdated.circleAngle;
                float newNameAngle = nameLayoutUpdated.circleAngle;

                assertEquals(ogDotAngle - Math.toDegrees(-Math.PI), newDotAngle, 0.5);
                assertEquals(ogNameAngle - Math.toDegrees(-Math.PI), newNameAngle, 0.5);


                mockDataSource.setValue(0.0F);
                activity.mockCompassUpdate();
            }
        });
    }

    @Test
    public void rotateNoneTest() {
        // Tests phone rotation to the right 90 (phone is now facing east)
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch((MainActivity.class));
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);

        MutableLiveData<Float> mockDataSource = new MutableLiveData<Float>();

        scenario.onActivity(activity -> {
            activity.setOrientationMock(mockDataSource);
            activity.setMock(true);

            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();

            mockDataSource.setValue(0.0F);
            activity.mockCompassUpdate();
            var friendList = activity.getFriendManager().getMockFriends();

            // TODO penis
            for (int i = 0; i < friendList.size(); i++) {
                var curr_friend = friendList.get(i);

                HashMap<String, View> friendViews = activity.friendMap.get(curr_friend.uid);

                View dotView = friendViews.get("dot");
                View nameView = friendViews.get("text");

                ConstraintLayout.LayoutParams dotLayout =
                        (ConstraintLayout.LayoutParams) dotView.getLayoutParams();
                var nameLayout = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();

                float ogDotAngle = dotLayout.circleAngle;
                float ogNameAngle = nameLayout.circleAngle;

                mockDataSource.setValue(0.0F);
                activity.mockCompassUpdate();

                ConstraintLayout.LayoutParams dotLayoutUpdated =
                        (ConstraintLayout.LayoutParams) dotView.getLayoutParams();
                var nameLayoutUpdated = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();

                float newDotAngle = dotLayoutUpdated.circleAngle;
                float newNameAngle = nameLayoutUpdated.circleAngle;

                assertEquals(ogDotAngle, newDotAngle, 0.5);
                assertEquals(ogNameAngle, newNameAngle, 0.5);


                mockDataSource.setValue(0.0F);
                activity.mockCompassUpdate();
            }
        });
    }
}
