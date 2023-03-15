package com.example.cse110_team2.BDDTests;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import android.os.Handler;


@RunWith(RobolectricTestRunner.class)
public class MS2US6BDDTest {

    @Test
    public void us6_bdd1_test() {

        ActivityScenario<MainActivity> scenarioLaunch =
                ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        scenarioLaunch.onActivity(activity -> {
            ImageView statusIndicator = activity.findViewById(R.id.LocationIndicator);
            TextView statusText = activity.findViewById(R.id.LocationText);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                public void run() {
                    System.out.println("test");
                    assertEquals(statusText.getText(),"Live Location");
                    assertEquals(statusIndicator.getColorFilter(), Color.argb(255, 0, 255, 0));
                }
            }, 5000);
        });
    }

}

