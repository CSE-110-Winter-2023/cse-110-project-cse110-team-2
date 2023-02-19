package com.example.cse110_team2;

import android.util.Log;

public class PointRelation {
    MyLocation myCoords;

    public PointRelation(MyLocation myCoords){
        this.myCoords = myCoords;
    }

    public double angleCalculation(double otherLat, double otherLon) {
        otherLat = (otherLat*Math.PI)/180;
        otherLon = (otherLon*Math.PI)/180;
        double myLat = (myCoords.getLat()*Math.PI)/180;
        double myLon = (myCoords.getLon()*Math.PI)/180;
        double longChange = otherLon - myLon;
        double x = Math.sin(longChange) * Math.cos(otherLat);
        double y = Math.cos(myLat) * Math.sin(otherLat) - Math.sin(myLat)
                * Math.cos(otherLat) * Math.cos(longChange);
        double bearingAngle = Math.atan2(x, y); //angle in radians
        return ((bearingAngle * 180 / Math.PI) + 360) % 360;
    }
    public void setCoords(MyLocation myloc){
        this.myCoords=myloc;
    }
}
