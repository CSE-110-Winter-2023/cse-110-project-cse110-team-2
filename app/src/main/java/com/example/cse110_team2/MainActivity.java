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
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LocationService locationService;
    private PointRelation locationRelater;
    private boolean firstLocUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstLocUpdate = false;
        //TextView lon = findViewById(R.id.Lon);
        //TextView lat = findViewById(R.id.Lat);

        MyLocation myloc = new MyLocation(0, 0);
        locationRelater = new PointRelation(myloc);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        ImageView redCircle = (ImageView) findViewById(R.id.redImage);
        ImageView blueCircle = (ImageView) findViewById(R.id.blueImage);
        ImageView yellowCircle = (ImageView) findViewById(R.id.yellowImage);
        redCircle.setVisibility(View.INVISIBLE);
        blueCircle.setVisibility(View.INVISIBLE);
        yellowCircle.setVisibility(View.INVISIBLE);

        locationService = LocationService.singleton(this);

        locationService.getLocation().observe(this, loc -> {
            firstLocUpdate=true;
            Log.d("type", "test");
            myloc.setLon(loc.second);
            myloc.setLat(loc.first);
            locationRelater.setCoords(myloc);
            compassUpdate();
        });
        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        String locName = preferences.getString("locationOneName", "N/A");
        if (locName == "N/A") {
            Intent intent = new Intent(this, InputLocation.class);
            startActivity(intent);
        }
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

        updateSpecificCircle(locName, locLat, locLon, circle, legendCircle, legendText);

        //        Second Point
        locName = preferences.getString("locationTwoName", "N/A");
        locLat = preferences.getString("locationTwoLat", "N/A");
        locLon = preferences.getString("locationTwoLon", "N/A");
        circle = (ImageView) findViewById(R.id.blueImage);
        legendCircle = (ImageView) findViewById(R.id.bluelegendcircle);
        legendText = (TextView) findViewById(R.id.bluelegendtext);

        updateSpecificCircle(locName, locLat, locLon, circle, legendCircle, legendText);

        //        Third Point
        locName = preferences.getString("locationThreeName", "N/A");
        locLat = preferences.getString("locationThreeLat", "N/A");
        locLon = preferences.getString("locationThreeLon", "N/A");
        circle = (ImageView) findViewById(R.id.yellowImage);
        legendCircle = (ImageView) findViewById(R.id.yellowlegendcircle);
        legendText = (TextView) findViewById(R.id.yellowlegendtext);

        updateSpecificCircle(locName, locLat, locLon, circle, legendCircle, legendText);

        solveOverlap();
    }

    private void updateSpecificCircle(String locName, String locLat, String locLon, ImageView circle, ImageView legendCircle, TextView legendText) {
        if (locName != "N/A") {
            Double newAngle = locationRelater.angleCalculation(Double.parseDouble(locLat), Double.parseDouble(locLon));
            legendText.setText(locName);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) circle.getLayoutParams();
            layoutParams.circleAngle = newAngle.floatValue();
            circle.setLayoutParams(layoutParams);
            if (firstLocUpdate) {
                legendCircle.setVisibility(View.VISIBLE);
                legendText.setVisibility(View.VISIBLE);
                circle.setVisibility(View.VISIBLE);
            }
        } else {
            Log.d("type","points");
            legendCircle.setVisibility(View.INVISIBLE);
            legendText.setVisibility(View.INVISIBLE);
            circle.setVisibility(View.INVISIBLE);
        }
    }

    private void solveOverlap() {
        final float scale = this.getResources().getDisplayMetrics().density;
        Log.i("overlap", "scale: " + scale);
        ImageView circleOne = findViewById(R.id.redImage);
        ImageView circleTwo = findViewById(R.id.blueImage);
        ImageView circleThree = findViewById(R.id.yellowImage);
        boolean overlay12 = checkAngleOverlap(circleOne, circleTwo);
        boolean overlay23 = checkAngleOverlap(circleTwo, circleThree);
        boolean overlay31 = checkAngleOverlap(circleThree, circleOne);
        Log.i("overlap", "overlay12: " + overlay12);
        Log.i("overlap", "overlay23: " + overlay23);
        Log.i("overlap", "overlay31: " + overlay31);
        if ((overlay12 && (overlay23 || overlay31)) || (overlay23 && overlay31)) {
            setCircleRadius(circleOne, (int) (158 * scale + 0.5f));
            setCircleRadius(circleTwo, (int) (168 * scale + 0.5f));
            setCircleRadius(circleThree, (int) (178 * scale + 0.5f));
            setCircleSize(circleOne, (int) (10 * scale + 0.5f));
            setCircleSize(circleTwo, (int) (10 * scale + 0.5f));
            setCircleSize(circleThree, (int) (10 * scale + 0.5f));
        } else if (overlay12) {
            setCircleRadius(circleOne, (int) (158 * scale + 0.5f));
            setCircleRadius(circleTwo, (int) (172 * scale + 0.5f));
            setCircleSize(circleOne, (int) (14 * scale + 0.5f));
            setCircleSize(circleTwo, (int) (14 * scale + 0.5f));
        } else if (overlay23) {
            setCircleRadius(circleTwo, (int) (158 * scale + 0.5f));
            setCircleRadius(circleThree, (int) (172 * scale + 0.5f));
            setCircleSize(circleTwo, (int) (14 * scale + 0.5f));
            setCircleSize(circleThree, (int) (14 * scale + 0.5f));
        } else if (overlay31) {
            setCircleRadius(circleOne, (int) (158 * scale + 0.5f));
            setCircleRadius(circleThree, (int) (172 * scale + 0.5f));
            setCircleSize(circleOne, (int) (14 * scale + 0.5f));
            setCircleSize(circleThree, (int) (14 * scale + 0.5f));
        }
    }

    private boolean checkAngleOverlap(ImageView circleOne, ImageView circleTwo) {
        ConstraintLayout.LayoutParams layoutParamsOne = (ConstraintLayout.LayoutParams) circleOne.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParamsTwo = (ConstraintLayout.LayoutParams) circleTwo.getLayoutParams();
//        Log.i("overlap", "Angle diff: " + Math.abs(layoutParamsOne.circleAngle-layoutParamsTwo.circleAngle));
//        return false;
        return Math.abs(layoutParamsOne.circleAngle - layoutParamsTwo.circleAngle) < 10;
    }

    private void setCircleRadius(ImageView circle, int radius) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) circle.getLayoutParams();
        layoutParams.circleRadius = radius;
        circle.setLayoutParams(layoutParams);
    }

    private void setCircleSize(ImageView circle, int size) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) circle.getLayoutParams();
        layoutParams.width = size;
        layoutParams.height = size;
        circle.setLayoutParams(layoutParams);
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
        if (locationThreeName != "N/A") {
            Utilities.showAlert(this, "You cannot save any more locations");
        } else {
            startActivity(intent);
        }

    }
}


