package com.example.cse110_team2;
import android.view.View;

import java.util.Arrays;

public class ZoomManager {
    int zoomAmount;
    int[] zoomLimit;
    double[] relativePixelWidth;
    double compassWidth;

    public ZoomManager(){
        this.zoomAmount = 1;
        this.zoomLimit = new int[]{1, 10, 500, 50000};
        this.relativePixelWidth = new double[]{200, 100, 66.66, 50};
    }

    public void setCompassWidth(double compassWidth) {
        this.compassWidth = compassWidth;
    }

    public int getZoomAmount(){
        return zoomAmount;
    }

    public double getCompassWidth(){
        return this.compassWidth * .9;
    }
    private double getCompassWidthRatio(){
        return getCompassWidth()/400;
    }

    public double getRadius(double distance){
        int[] zoomSub = Arrays.copyOfRange(this.zoomLimit,0, zoomAmount + 1);
        for(int i = 0; i<zoomSub.length; i += 1){
            if(distance < (double) zoomSub[i]){
                double dist_ratio = distance / (double) zoomSub[i];
                double pixel_val_ring = this.relativePixelWidth[this.zoomAmount];
                double lower_bound = pixel_val_ring * i;
                double upper_bound = pixel_val_ring * (i+1);
                return (((upper_bound - lower_bound) * dist_ratio) + lower_bound) * this.getCompassWidthRatio();

            }
        }
        return 0.00;
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

    public int get_curr_zoom_max(){
        return zoomLimit[zoomAmount];
    }


}
