package com.example.cse110_team2.BDDTests;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import com.example.cse110_team2.AddFriendsActivity;
import com.example.cse110_team2.FriendManager;
import com.example.cse110_team2.InputNameActivity;
import com.example.cse110_team2.MainActivity;
import com.example.cse110_team2.R;
import com.example.cse110_team2.SharedCompassAPI;
import com.example.cse110_team2.User;
import com.example.cse110_team2.ZoomManager;

import java.util.HashMap;

@RunWith(RobolectricTestRunner.class)
public class MS2US4BDDTest {

    @Test
    public void us4_bdd1_zoomOutTest() {
        ZoomManager zm = new ZoomManager();

        assertEquals(zm.getZoomAmount(), 1);
        assertEquals(zm.canZoomOut(), true);
        assertEquals(zm.canZoomIn(), true);

        zm.zoomOut();
        assertEquals(zm.getZoomAmount(), 2);
        assertEquals(zm.canZoomOut(), true);
        assertEquals(zm.canZoomIn(), true);

        zm.zoomOut();
        assertEquals(zm.getZoomAmount(), 3);
        assertEquals(zm.canZoomOut(), false);
        assertEquals(zm.canZoomIn(), true);



    }


    @Test
    public void us4_bdd2_zoomInTest() {
        ZoomManager zm = new ZoomManager();

        assertEquals(zm.getZoomAmount(), 1);
        assertEquals(zm.canZoomOut(), true);
        assertEquals(zm.canZoomIn(), true);

        zm.zoomIn();
        assertEquals(zm.getZoomAmount(), 0);
        assertEquals(zm.canZoomOut(), true);
        assertEquals(zm.canZoomIn(), false);


        zm.zoomOut();
        zm.zoomOut();
        zm.zoomOut();

        assertEquals(zm.getZoomAmount(), 3);
        assertEquals(zm.canZoomOut(), false);
        assertEquals(zm.canZoomIn(), true);

        zm.zoomIn();
        assertEquals(zm.getZoomAmount(), 2);
        assertEquals(zm.canZoomOut(), true);
        assertEquals(zm.canZoomIn(), true);

        zm.zoomIn();
        assertEquals(zm.getZoomAmount(), 1);
        assertEquals(zm.canZoomOut(), true);
        assertEquals(zm.canZoomIn(), true);


        zm.zoomIn();
        assertEquals(zm.getZoomAmount(), 0);
        assertEquals(zm.canZoomOut(), true);
        assertEquals(zm.canZoomIn(), false);



    }


}

