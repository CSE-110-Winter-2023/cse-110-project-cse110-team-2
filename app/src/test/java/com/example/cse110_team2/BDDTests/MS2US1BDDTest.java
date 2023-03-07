package com.example.cse110_team2.BDDTests;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;


import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.*;

import com.example.cse110_team2.AddFriendsActivity;
import com.example.cse110_team2.InputNameActivity;
import com.example.cse110_team2.R;


@RunWith(RobolectricTestRunner.class)
public class MS2US1BDDTest  {

    @Test
    public void us1_bdd_save_name()   {

        ActivityScenario<InputNameActivity> scenarioLaunch = ActivityScenario.launch(InputNameActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        scenarioLaunch.onActivity(activity -> {
            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();

            TextView name = activity.findViewById(R.id.name);
            name.setText("Julia");
            activity.findViewById(R.id.save).performClick();

            assertEquals("Julia", preferences.getString("user","NA"));
            assertNotNull(preferences.getString("publicID","NA"));
            assertNotNull(preferences.getString("privateID","NA"));
            Intent intent = shadowOf(activity).getNextStartedActivity();
            ShadowIntent shadowIntent = shadowOf(intent);
            assertEquals(AddFriendsActivity.class, shadowIntent.getIntentClass());

            ActivityScenario<AddFriendsActivity> scenarioInput = ActivityScenario.launch(AddFriendsActivity.class);

            scenarioInput.moveToState(Lifecycle.State.CREATED);
            scenarioInput.moveToState(Lifecycle.State.STARTED);

            scenarioInput.onActivity(sActivity -> {
                TextView id = sActivity.findViewById(R.id.ID);

                assertEquals(id.getText(),"ID: " + preferences.getString("publicID","NA"));
                assertNotEquals(id.getText(), "Your ID: ");
                sActivity.finish();

            });
        });


    }
}
