package com.example.cse110_team2;
public class MyLocation {
    private double lon;
    private double lat;
    public MyLocation(float lon,float lat){
        this.lon=lon;
        this.lat=lat;
    }
    public void setLon(double lon){
        this.lon=lon;
    }
    public void setLat(double lat){
        this.lat=lat;
    }
    public double getLon(){
        return this.lon;
    }
    public double getLat(){
        return this.lat;
    }
}
