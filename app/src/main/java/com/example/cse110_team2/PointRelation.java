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

    /*
    * Got information from: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
    * @
     */
    public double distanceCalculation(double otherLat, double otherLon){

        final int R = 6371; // Radius of the earth
        final double metersToMiles = 1609.344;
        double latDistance = Math.toRadians(otherLat - myCoords.getLat());
        double lonDistance = Math.toRadians(otherLon - myCoords.getLon());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(myCoords.getLat())) * Math.cos(Math.toRadians(otherLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return Math.sqrt(distance)/(metersToMiles);
        // 0 -1
        // 1- 10
        // 10-100
        // 100 - 500
    }

}
