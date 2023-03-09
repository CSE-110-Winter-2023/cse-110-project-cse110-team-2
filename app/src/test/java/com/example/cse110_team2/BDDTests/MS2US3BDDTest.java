package com.example.cse110_team2.BDDTests;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.*;

import com.example.cse110_team2.AddFriendsActivity;
import com.example.cse110_team2.FriendManager;
import com.example.cse110_team2.InputNameActivity;
import com.example.cse110_team2.MainActivity;
import com.example.cse110_team2.R;
import com.example.cse110_team2.SharedCompassAPI;
import com.example.cse110_team2.User;

import java.util.ArrayList;
import java.util.HashMap;


@RunWith(RobolectricTestRunner.class)
public class MS2US3BDDTest {

    @Test
    public void us3_bdd1_test() {
        /* MAKE SURE TO ADD EMMA WITH ID 111111 TO SERVER BEFORE RUNNING TEST */
        ActivityScenario<MainActivity> scenarioLaunch =
                ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        scenarioLaunch.onActivity(activity -> {
            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();
            activity.setMock(true);
            activity.curr_zoom_max = 1;
            // Mock that we already have two friends
            SharedCompassAPI api = new SharedCompassAPI();
            User friend1 = new User("friend1", "friend1UID", (float)-117.0010,34,"friend1PrivCode");
            User friend2 = new User("friend2", "friend2UID",(float)-117.0000, (float)34.0050,"friend2PrivCode");
            FriendManager friendManager = activity.getFriendManager();
            ArrayList<User> friendList = friendManager.getMockFriends();
            friendList.add(friend1);
            friendList.add(friend2);
            activity.compassUpdate();
            HashMap<String, HashMap<String, View>> friendMap = activity.friendMap;
            View friend1Text = friendMap.get("friend1UID").get("text");
            View friend2Text = friendMap.get("friend2UID").get("text");
            ConstraintLayout.LayoutParams friend1Layout = (ConstraintLayout.LayoutParams) friend1Text.getLayoutParams();
            ConstraintLayout.LayoutParams friend2Layout = (ConstraintLayout.LayoutParams) friend2Text.getLayoutParams();

            assertEquals(friend1Layout.leftMargin,120);
            assertEquals(friend1Layout.topMargin,144);
            assertEquals(friend2Layout.leftMargin,144);
            assertEquals(friend2Layout.topMargin,292);
            activity.setMock(false);
        });
    }

    @Test
    public void us3_bdd2_test() {
        ActivityScenario<MainActivity> scenarioLaunch =
                ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        scenarioLaunch.onActivity(activity -> {
            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();
            activity.setMock(true);
            activity.curr_zoom_max = 1;
            // Mock that we already have two friends
            SharedCompassAPI api = new SharedCompassAPI();
            User friend1 = new User("friend1", "friend1UID", (float)-115.0010,34,"friend1PrivCode");
            User friend2 = new User("friend2", "friend2UID",(float)-117.0000, (float)32.0050,"friend2PrivCode");
            FriendManager friendManager = activity.getFriendManager();
            ArrayList<User> friendList = friendManager.getMockFriends();
            friendList.add(friend1);
            friendList.add(friend2);
            activity.compassUpdate();
            HashMap<String, HashMap<String, View>> friendMap = activity.friendMap;
            View friend1Text = friendMap.get("friend1UID").get("dot");
            View friend2Text = friendMap.get("friend2UID").get("dot");
            ConstraintLayout.LayoutParams friend1Layout = (ConstraintLayout.LayoutParams) friend1Text.getLayoutParams();
            ConstraintLayout.LayoutParams friend2Layout = (ConstraintLayout.LayoutParams) friend2Text.getLayoutParams();

            assertEquals(friend1Layout.circleAngle,89.44, 1e-1);
            assertEquals(friend2Layout.circleAngle,180, 1e-1);
            activity.setMock(false);
        });
    }
    @Test
    public void us3_bdd3_test() {
        ActivityScenario<MainActivity> scenarioLaunch =
                ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        scenarioLaunch.onActivity(activity -> {
            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();
            activity.setMock(true);
            activity.curr_zoom_max = 1;
            // Mock that we already have two friends
            SharedCompassAPI api = new SharedCompassAPI();
            FriendManager friendManager = activity.getFriendManager();
            ArrayList<User> friendList = friendManager.getMockFriends();
            friendList.clear();
            activity.compassUpdate();
            HashMap<String, HashMap<String, View>> friendMap = activity.friendMap;

            assertEquals(friendList.size(), 0);
            activity.setMock(false);
        });
    }
}
