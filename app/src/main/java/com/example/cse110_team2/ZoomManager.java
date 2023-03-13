package com.example.cse110_team2;
import android.view.View;

public class ZoomManager {
    int zoomAmount;
    int[] zoomLimit;

    public ZoomManager(){
        zoomAmount = 0;
        zoomLimit = new int[]{1, 10, 500, 50000};
    }

    public int getZoomAmount(){
        return zoomAmount;
    }

    public void zoomOut(){
        zoomAmount += 1;
    }

    public void zoomIn(){
        zoomAmount -= 1;
    }
    public boolean canZoomOut(){
        return zoomAmount != 3;

    }
    public boolean canZoomIn(){
        return zoomAmount != 0;

    }
    public int getMaxZoom(){
        return zoomLimit[zoomAmount];
    }


}
