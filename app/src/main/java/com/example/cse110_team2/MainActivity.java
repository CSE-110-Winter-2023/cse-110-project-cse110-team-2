package com.example.cse110_team2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private PointRelation locationRelater;
    private boolean firstLocUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstLocUpdate = false;
        //TextView lon= findViewById(R.id.Lon);
        //TextView lat= findViewById(R.id.Lat);

        MyLocation myloc = new MyLocation(0,0);
        locationRelater = new PointRelation(myloc);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        ImageView redCircle = (ImageView) findViewById(R.id.redImage);
        ImageView blueCircle = (ImageView) findViewById(R.id.blueImage);
        ImageView yellowCircle = (ImageView) findViewById(R.id.yellowImage);
        redCircle.setVisibility(View.INVISIBLE);
        blueCircle.setVisibility(View.INVISIBLE);
        yellowCircle.setVisibility(View.INVISIBLE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                firstLocUpdate = true;
                myloc.setLon(location.getLongitude());
                myloc.setLat(location.getLatitude());
                compassUpdate();
                //lon.setText("Longitude: " + String.valueOf(myloc.getLon()));
                //lat.setText("Latitude: " + String.valueOf(myloc.getLat()));
            }
        });
    }

    private void compassUpdate() {
        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        String locName;
        String locLat;
        String locLon;
        ImageView circle;
        ImageView legendCircle;
        TextView legendText;

//        First Point
        locName = preferences.getString("locationOneName", "N/A");
        locLat = preferences.getString("locationOneLat", "N/A");
        locLon = preferences.getString("locationOneLon", "N/A");
        circle = (ImageView) findViewById(R.id.redImage);
        legendCircle = (ImageView) findViewById(R.id.redlegendcircle);
        legendText = (TextView) findViewById(R.id.redlegendtext);

        updateSpecificCircle(locName,locLat,locLon,circle,legendCircle,legendText);

        //        Second Point
        locName = preferences.getString("locationTwoName", "N/A");
        locLat = preferences.getString("locationTwoLat", "N/A");
        locLon = preferences.getString("locationTwoLon", "N/A");
        circle = (ImageView) findViewById(R.id.blueImage);
        legendCircle = (ImageView) findViewById(R.id.bluelegendcircle);
        legendText = (TextView) findViewById(R.id.bluelegendtext);

        updateSpecificCircle(locName,locLat,locLon,circle,legendCircle,legendText);

        //        Third Point
        locName = preferences.getString("locationThreeName", "N/A");
        locLat = preferences.getString("locationThreeLat", "N/A");
        locLon = preferences.getString("locationThreeLon", "N/A");
        circle = (ImageView) findViewById(R.id.yellowImage);
        legendCircle = (ImageView) findViewById(R.id.yellowlegendcircle);
        legendText = (TextView) findViewById(R.id.yellowlegendtext);

        updateSpecificCircle(locName,locLat,locLon,circle,legendCircle,legendText);
    }

    private void updateSpecificCircle(String locName, String locLat, String locLon, ImageView circle, ImageView legendCircle, TextView legendText) {
        if (locName != "N/A") {
            Double newAngle = locationRelater.angleCalculation(Double.parseDouble(locLat),Double.parseDouble(locLon));
            legendText.setText(locName);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) circle.getLayoutParams();
            layoutParams.circleAngle = newAngle.floatValue();
            circle.setLayoutParams(layoutParams);
            if (firstLocUpdate){
                legendCircle.setVisibility(View.VISIBLE);
                legendText.setVisibility(View.VISIBLE);
                circle.setVisibility(View.VISIBLE);
            }
        } else {
            legendCircle.setVisibility(View.INVISIBLE);
            legendText.setVisibility(View.INVISIBLE);
            circle.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        compassUpdate();
    }

    public void onNewLocationBtnClicked(View view) {
        Intent intent = new Intent(this, InputLocation.class);

        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        String locationThreeName = preferences.getString("locationThreeName", "N/A");

        Log.i("Location 3", locationThreeName);
        if(locationThreeName != "N/A"){
            Utilities.showAlert(this, "You cannot save any more locations");
        }else {
            startActivity(intent);
        }

    }
}