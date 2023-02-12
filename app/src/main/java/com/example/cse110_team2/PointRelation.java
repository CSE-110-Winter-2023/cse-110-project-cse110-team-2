package com.example.cse110_team2;

public class PointRelation {
    MyLocation myCoords;
    double otherLat;
    double otherLon;

    public PointRelation(MyLocation myCoords, double otherLat, double otherLon){
        this.myCoords = myCoords;
        this.otherLat = otherLat;
        this.otherLon = otherLon;
    }

    public double angleCalculation() {

        double longChange = otherLon - myCoords.getLon();
        double x = Math.sin(longChange) * Math.cos(otherLat);
        double y = Math.cos(myCoords.getLat()) * Math.sin(otherLat) - Math.sin(myCoords.getLat())
                * Math.cos(otherLat) * Math.cos(longChange);
        double bearingAngle = Math.atan2(x, y); //angle in radians
        return ((bearingAngle * 180 / Math.PI) + 360) % 360;
    }
}
