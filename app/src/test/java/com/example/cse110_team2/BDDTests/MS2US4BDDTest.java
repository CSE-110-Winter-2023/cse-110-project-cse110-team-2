
package com.example.cse110_team2.BDDTests;

import static org.junit.Assert.assertEquals;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import com.example.cse110_team2.AddFriendsActivity;
import com.example.cse110_team2.FriendManager;
import com.example.cse110_team2.InputNameActivity;
import com.example.cse110_team2.MainActivity;
import com.example.cse110_team2.R;
import com.example.cse110_team2.SharedCompassAPI;
import com.example.cse110_team2.User;

import java.util.HashMap;

@RunWith(RobolectricTestRunner.class)
public class MS2US4BDDTest {


    @Test
    public void us4_bdd1_zoomOutTest() {
        ActivityScenario<MainActivity> scenarioLaunch =
                ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);


        scenarioLaunch.onActivity(activity -> {
            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();
            activity.setMock(true);

            SharedCompassAPI api = new SharedCompassAPI();

            //friend within 1 mile
            User friend1 = new User("friend1", "friend1UID", (float)-117.0010,34,"friend1PrivCode");

            //friend within 10-500 miles
            User friend2 = new User("friend2", "friend2UID",(float)-117.00, 37,"friend2PrivCode");

            //friend farther than 500 miles
            User friend3 = new User("friend3", "friend3UID",(float)-117.00, 60,"friend3PrivCode");

            var fm = activity.getFriendManager();
            var fl = fm.getMockFriends();
            fl.add(friend1);
            fl.add(friend2);
            fl.add(friend3);
            activity.compassUpdate(0.0F);

            HashMap<String, View> friend1View = activity.friendMap.get("friend1UID");

            TextView friend1Text = (TextView)friend1View.get("text");
            ConstraintLayout.LayoutParams friend1Layout = (ConstraintLayout.LayoutParams)friend1Text.getLayoutParams();

            assertEquals(friend1Layout.leftMargin,144);
            assertEquals(friend1Layout.topMargin,144);

            HashMap<String, View> friend2View = activity.friendMap.get("friend2UID");

            TextView friend2Text = (TextView)friend2View.get("text");
            ConstraintLayout.LayoutParams friend2Layout = (ConstraintLayout.LayoutParams)friend2Text.getLayoutParams();

            assertEquals(friend2Layout.leftMargin,0);
            assertEquals(friend2Layout.topMargin,0);

            HashMap<String, View> friend3View = activity.friendMap.get("friend3UID");

            TextView friend3Text = (TextView)friend3View.get("text");
            ConstraintLayout.LayoutParams friend3Layout = (ConstraintLayout.LayoutParams)friend3Text.getLayoutParams();

            assertEquals(friend3Layout.leftMargin,0);
            assertEquals(friend3Layout.topMargin,0);


            Button zoomOutBtn = activity.findViewById(R.id.zoomOut);
            zoomOutBtn.performClick();
            zoomOutBtn.performClick();

            activity.compassUpdate(0.0F);


            assertEquals(friend1Layout.leftMargin,144);
            assertEquals(friend1Layout.topMargin,144);

            assertEquals(friend2Layout.leftMargin,144);
            assertEquals(friend2Layout.topMargin, 144);

            assertEquals(friend3Layout.leftMargin,0);
            assertEquals(friend3Layout.topMargin,0);


            zoomOutBtn.performClick();

            activity.compassUpdate(0.0F);

            assertEquals(friend2Layout.leftMargin,144);
            assertEquals(friend2Layout.topMargin, 144);


            assertEquals(friend3Layout.leftMargin,144);
            assertEquals(friend3Layout.topMargin,144);

            activity.setMock(false);
        });
        scenarioLaunch.moveToState(Lifecycle.State.DESTROYED);

    }



    @Test
    public void us4_bdd2_zoomInTest() {
        ActivityScenario<MainActivity> scenarioLaunch =
                ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        scenarioLaunch.onActivity(activity -> {
            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();
            activity.setMock(true);

            SharedCompassAPI api = new SharedCompassAPI();

            //friend within 1 mile
            User friend1 = new User("friend1", "friend1UID", (float) -117.0010, 34, "friend1PrivCode");

            //friend within 10-500 miles
            User friend2 = new User("friend2", "friend2UID", (float) -117.00, 37, "friend2PrivCode");

            //friend farther than 500 miles
            User friend3 = new User("friend3", "friend3UID", (float) -117.00, 60, "friend3PrivCode");

            var fm = activity.getFriendManager();
            var fl = fm.getMockFriends();
            fl.add(friend1);
            fl.add(friend2);
            fl.add(friend3);
            activity.compassUpdate(0.0F);

            Button zoomOutBtn = activity.findViewById(R.id.zoomOut);
            zoomOutBtn.performClick();
            zoomOutBtn.performClick();
            zoomOutBtn.performClick();


            Button zoomInBtn = activity.findViewById(R.id.zoomIn);
            zoomInBtn.performClick();
            zoomInBtn.performClick();
            zoomInBtn.performClick();

            activity.compassUpdate(0.0F);

            HashMap<String, View> friend1View = activity.friendMap.get("friend1UID");

            var friend1Text = (TextView)friend1View.get("text");
            var friend1Layout = (ConstraintLayout.LayoutParams)friend1Text.getLayoutParams();

            assertEquals(friend1Layout.leftMargin,144);
            assertEquals(friend1Layout.topMargin,144);

            var friend2View = activity.friendMap.get("friend2UID");

            var friend2Text = (TextView)friend2View.get("text");
            var friend2Layout = (ConstraintLayout.LayoutParams)friend2Text.getLayoutParams();

            assertEquals(friend2Layout.leftMargin,0);
            assertEquals(friend2Layout.topMargin, 0);

            var friend3View = activity.friendMap.get("friend3UID");

            var friend3Text = (TextView)friend3View.get("text");
            var friend3Layout = (ConstraintLayout.LayoutParams)friend3Text.getLayoutParams();

            assertEquals(friend3Layout.leftMargin,0);
            assertEquals(friend3Layout.topMargin,0);

            activity.setMock(false);


        });

        scenarioLaunch.moveToState(Lifecycle.State.DESTROYED);

    }



}


