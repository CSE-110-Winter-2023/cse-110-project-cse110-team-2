package com.example.cse110_team2.BDDTests;

import static org.junit.Assert.assertEquals;

import android.content.SharedPreferences;
import android.view.View;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.example.cse110_team2.MainActivity;
import com.example.cse110_team2.User;

import java.util.HashMap;

@RunWith(RobolectricTestRunner.class)
public class MS2US5BDDTest {
    @Test
    public void BDDScenario() {
        ActivityScenario<MainActivity> scenarioLaunch =
                ActivityScenario.launch(MainActivity.class);

        scenarioLaunch.moveToState(Lifecycle.State.CREATED);
        scenarioLaunch.moveToState(Lifecycle.State.STARTED);

        MutableLiveData<Float> mockDataSource = new MutableLiveData<Float>();

        scenarioLaunch.onActivity(activity -> {
            activity.setOrientationMock(mockDataSource);
            activity.setMock(true);

            SharedPreferences preferences = activity.getSharedPreferences("IDvalue", 0);
            preferences.edit().clear().commit();

            User dave = new User("dave", "dave", 0f,
                    -20f, "123-942-2341");

            User mark = new User("mark", "mark", 0f,
                    20f, "123-460-2341");

            var fm = activity.getFriendManager();
            var fl = fm.getMockFriends();
            fl.clear();
            fl.add(dave);
            fl.add(mark);

            activity.mockUpsertLoc("dave", "dave");
            activity.mockUpsertLoc("mark", "mark");

            activity.mockLoc();

            mockDataSource.setValue((float) (Math.PI / 2));
            activity.mockCompassUpdate();

            HashMap<String, View> daveViews = activity.friendMap.get(dave.uid);

            View dotDaveView = daveViews.get("dot");
            var dotDaveLayout = (ConstraintLayout.LayoutParams) dotDaveView.getLayoutParams();
            float dotDaveAngle = dotDaveLayout.circleAngle;

            assertEquals(90f, dotDaveAngle, 0.5);

            HashMap<String, View> markViews = activity.friendMap.get(mark.uid);
            View dotMarkView = markViews.get("dot");
            var dotMarkLayout = (ConstraintLayout.LayoutParams) dotMarkView.getLayoutParams();
            float dotMarkAngle = dotMarkLayout.circleAngle;

            assertEquals(-90f, dotMarkAngle, 0.5);
        });
    }
}
