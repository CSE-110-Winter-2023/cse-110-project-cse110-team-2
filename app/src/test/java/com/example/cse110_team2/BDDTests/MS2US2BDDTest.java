package com.example.cse110_team2.BDDTests;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;


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

@RunWith(RobolectricTestRunner.class)
public class MS2US2BDDTest {

    @Test
    public void us2_bdd1_test() {
        /* MAKE SURE TO ADD EMMA WITH ID 111111 TO SERVER BEFORE RUNNING TEST */
        ActivityScenario<MainActivity> scenarioLaunch =
                ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        scenarioLaunch.onActivity(activity -> {
            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();

           // Click on Add Friends button
            Button addFriendsBtn = activity.findViewById(R.id.add_friend_btn);
            addFriendsBtn.performClick();
            Intent intent = shadowOf(activity).getNextStartedActivity();
            ShadowIntent shadowIntent = shadowOf(intent);

            assertEquals(AddFriendsActivity.class, shadowIntent.getIntentClass());

            ActivityScenario<AddFriendsActivity> addFriendsScenario =
                    ActivityScenario.launch(AddFriendsActivity.class);

            addFriendsScenario.moveToState(Lifecycle.State.CREATED);
            addFriendsScenario.moveToState(Lifecycle.State.STARTED);

            addFriendsScenario.onActivity(afActivity -> {
                TextView uidTxtView = afActivity.findViewById(R.id.friendID);
                uidTxtView.setText("111111");
                Button addUIDBtn = afActivity.findViewById(R.id.add_btn);
                addUIDBtn.performClick();
            });

            FriendManager fm = activity.getFriendManager();
            ArrayList<User> friends = fm.getFriends();
            assertEquals(1, friends.size());
            User us = friends.get(0);
            assertEquals("Emma", us.name);
        });
    }

    @Test
    public void us2_bdd2_test() {
        ActivityScenario<MainActivity> scenarioLaunch =
                ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        scenarioLaunch.onActivity(activity -> {
            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();

            // Mock that we already have two friends
            SharedCompassAPI api = new SharedCompassAPI();
            User friend = api.getUserAsync("111111");
            activity.mockAddFriend(friend);

            // Click on Add Friends button
            Button addFriendsBtn = activity.findViewById(R.id.add_friend_btn);
            addFriendsBtn.performClick();
            Intent intent = shadowOf(activity).getNextStartedActivity();
            ShadowIntent shadowIntent = shadowOf(intent);

            assertEquals(AddFriendsActivity.class, shadowIntent.getIntentClass());

            ActivityScenario<AddFriendsActivity> addFriendsScenario =
                    ActivityScenario.launch(AddFriendsActivity.class);

            addFriendsScenario.moveToState(Lifecycle.State.CREATED);
            addFriendsScenario.moveToState(Lifecycle.State.STARTED);

            addFriendsScenario.onActivity(afActivity -> {
                TextView uidTxtView = afActivity.findViewById(R.id.friendID);
                uidTxtView.setText("123456");
                Button addUIDBtn = afActivity.findViewById(R.id.add_btn);
                addUIDBtn.performClick();
            });

            FriendManager fm = activity.getFriendManager();
            ArrayList<User> friends = fm.getFriends();
            assertEquals(2, friends.size());
            User us = friends.get(0);
            assertEquals("Emma", us.name);
            us = friends.get(1);
            assertEquals("Josh", us.name);
        });
    }
}
