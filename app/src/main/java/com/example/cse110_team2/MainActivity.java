package com.example.cse110_team2;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.LongDef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;


import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private int counter = 0;
    private LocationManager locationManager;
    private FriendManager friendManager;
    private PointRelation locationRelater;
    private OrientationService orientationService;
    private boolean firstLocUpdate;
    private final int MAX_RADIUS_OFFSET = 70;

    private final int MAX_DIST = 430;
//    public int zoomManager.get_curr_zoom_max();
    private MyLocation myloc;
    private ConstraintLayout layout;
    private ZoomManager zoomManager;

    private boolean inMock;
    public HashMap<String, HashMap<String, View>> friendMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        inMock = false;
        String name = preferences.getString("user", "N/A");
        if (name == "N/A") {
            Log.d("debug", name);
            Intent intent = new Intent(this, InputNameActivity.class);
            startActivity(intent);
        }

        myloc = new MyLocation(-117, 34);
        locationRelater = new PointRelation(myloc);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        zoomManager = new ZoomManager();
        updateZoomButtons();

//
        friendManager = FriendManager.provide();
        friendManager.loadFriendsFromSharedPreferences(preferences);
        layout = (ConstraintLayout)findViewById(R.id.compasslayout);

        friendMap = new HashMap<String, HashMap<String, View>>();

        orientationService = OrientationService.singleton(MainActivity.this);
        orientationService.getOrientation().observe(this, azimuth -> {
            updateFunctions(azimuth);
        });


        var executor = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> poller = executor.scheduleAtFixedRate(() -> {
            friendManager.updateFriendLocations();
            updateFunctions(orientationService.getOrientation().getValue());
            updateLocationStatus();
        }, 0, 5, TimeUnit.SECONDS);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                firstLocUpdate = true;
                if (inMock) {
                    myloc.setLon(-117);
                    myloc.setLat(34);
                } else {
                    myloc.setLon(location.getLongitude());
                    myloc.setLat(location.getLatitude());
                    updateFunctions(orientationService.getOrientation().getValue());
                }

                //lon.setText("Longitude: " + String.valueOf(myloc.getLon()));
                //lat.setText("Latitude: " + String.valueOf(myloc.getLat()));
            }
        });

    }

    public void setMock(boolean inMock){
        this.inMock = inMock;
    }

    public boolean getMock(){
        return this.inMock;
    }


    public void updateFunctions(Float az){
        int width = ((ImageView) findViewById(R.id.compassImage)).getWidth();
        Log.d("COMPASS RAD", "" + width);
        zoomManager.setCompassWidth((double) width);


        //Might be necessary to calculate azimuth angle/zoom/etc
        compassUpdate(az);
        updateCompassImage();
    }

    public void updateCompassImage(){
        int zoomAmount = zoomManager.getZoomAmount();
        switch(zoomAmount){
            case 0: ((ImageView) findViewById(R.id.compassImage)).setImageResource(R.drawable.zoom);
                    break;
            case 1: ((ImageView) findViewById(R.id.compassImage)).setImageResource(R.drawable.zoom1);
                    break;
            case 2: ((ImageView) findViewById(R.id.compassImage)).setImageResource(R.drawable.zoom2);
                    break;
            case 3: ((ImageView) findViewById(R.id.compassImage)).setImageResource(R.drawable.zoom3);
                    break;
        }
    }

    public void compassUpdate(Float az) {
        String name;
        String uid;
        float longitude;
        float latitude;
        User curr_friend;
        ArrayList<User> friendList = friendManager.getFriends();
//        User us1 = new User("tomo", "thissirandom", (float)-110.3, (float)50.3, "hello");
////        37.3064, -121.9967
//        User us2 = new User("john", "thissirandom22", (float)-121.9964, (float)37.3064, "hello123");
//        User us3 = new User("buzzy", "thissirandom33", (float)-125, (float)38.3304, "hello12345");
//        friendList.add(us1);
//        friendList.add(us2);
//        friendList.add(us3);
        if (inMock) {
            friendList = friendManager.getMockFriends();
        }
//        ArrayList<User> friendList = new ArrayList<User>();
//        User tempUser = new User("Kevin", "abc", -117, (float)33.9870, "private");
//        friendList.add(tempUser);

        for (int i = 0; i < friendList.size(); i++) {
            curr_friend = friendList.get(i);
            name = curr_friend.name;

            uid = curr_friend.uid;
            upsertFriendMap(uid, name);
            longitude = curr_friend.longitude;
            latitude = curr_friend.latitude;
            double point_angle = locationRelater.angleCalculation(latitude, longitude);
            double friend_distance = locationRelater.distanceCalculation(latitude, longitude);

            if(name == "john") {
                Log.d("POINT_ANGLE", uid + " " + longitude + " " + latitude);
                Log.d("POINT_ANGLE", "" + point_angle + "\n\n");
                Log.d("POINT_ANGLE", "" + locationRelater.myCoords.getLon() + " " + locationRelater.myCoords.getLat() + "\n\n");
                Log.d("POINT_ANGLE", "" + friend_distance + "\n\n");
                Log.d("friends", " " + friend_distance);
                Log.d("friends", " " + zoomManager.get_curr_zoom_max());
            }
            if(friend_distance < zoomManager.get_curr_zoom_max()){
                if(name == "john"){
                    Log.d("POINT_ANGLE", "sending to display name");

                }
                displayFriendName(uid, point_angle, friend_distance);
            } else {
                displayDotOnEdge(uid,point_angle);
            }
            //TODO: Update friend name here with relative location (longitude and latitude)
            rotate(az, uid);
        }
        handleNameOverlap();
    }

public void rotate(Float az, String uid) {
        if (az == null) { az = 0.0F; }
        Float finalAz = az;
        runOnUiThread(new Runnable() {
            public void run() {


                HashMap<String, View> friendViews = friendMap.get(uid);
                View nameView = friendViews.get("text");
                View dotView = friendViews.get("dot");
                ConstraintLayout.LayoutParams nameLayout = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();
                ConstraintLayout.LayoutParams dotLayout = (ConstraintLayout.LayoutParams) dotView.getLayoutParams();

                nameLayout.circleAngle -= Math.toDegrees(finalAz);
                dotLayout.circleAngle -= Math.toDegrees(finalAz);
                nameView.setLayoutParams(nameLayout);
                dotView.setLayoutParams(dotLayout);
            }
        });
}


    private void upsertFriendMap(String uid, String name){
        if (!friendMap.containsKey(uid)) {
            runOnUiThread(new  Runnable()
            {
                public void run()
                {
                    HashMap<String, View> viewMap = new HashMap<String, View>();
                    TextView nameView = new TextView(MainActivity.this);
                    ViewGroup.LayoutParams wrapParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    nameView.setLayoutParams(wrapParams);
                    nameView.setText(name);
                    nameView.setId(View.generateViewId());
                    nameView.setVisibility(View.INVISIBLE);
                    nameView.setTextSize(12);
                    layout.addView(nameView);

                    ImageView dotView = new ImageView(MainActivity.this);
                    dotView.setImageResource(R.drawable.redcircle);
                    dotView.setId(View.generateViewId());
                    dotView.setVisibility(View.INVISIBLE);
                    layout.addView(dotView);
                    ConstraintLayout.LayoutParams dotLayout = (ConstraintLayout.LayoutParams) dotView.getLayoutParams();
                    dotLayout.circleConstraint = R.id.compasslayout;
                    Log.d("DOT-ON-EDGE", "" + zoomManager.getCompassWidth()/2);
                    dotLayout.circleRadius = (int) zoomManager.getCompassWidth()/2;
                    dotLayout.circleAngle = 0;
                    dotLayout.width = 30;
                    dotLayout.height = 30;
                    dotView.setLayoutParams(dotLayout);

                    viewMap.put("text", nameView);
                    viewMap.put("dot", dotView);
                    friendMap.put(uid, viewMap);
                }
            });
        }
    }

    public void updateLocationStatus() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        long unixTime = System.currentTimeMillis() ;
        long locationTime = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getTime();

        boolean hasLocation = unixTime - locationTime > 60000 ? false:true;
        ImageView statusIndicator = findViewById(R.id.LocationIndicator);
        TextView statusText = findViewById(R.id.LocationText);

        runOnUiThread(new  Runnable() {
            @Override
            public void run() {

                if (hasLocation) {
                    statusIndicator.setColorFilter(Color.argb(255, 0, 255, 0));
                    statusText.setText("Live Location");
                } else {
                    statusIndicator.setColorFilter(Color.argb(255, 255, 0, 0));
                    statusText.setText(String.valueOf((unixTime-locationTime )/60000) + "m");
                }

            }
        });

    }

    private void displayFriendName(String uid, double angle, double distance){

        runOnUiThread(new  Runnable()
        {
            public void run()
            {
                double newAngle = 90 - 360 - angle;
                HashMap<String, View> friendViews = friendMap.get(uid);
                View nameView = friendViews.get("text");
                View dotView = friendViews.get("dot");
//                MAX_DIST * distance/zoomManager.get_curr_zoom_max()
                ConstraintLayout.LayoutParams nameLayout = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();
                int xShift = (int) (zoomManager.getRadius(distance) * Math.cos(Math.toRadians(newAngle)));
                int yShift = (int) (zoomManager.getRadius(distance) * Math.sin(Math.toRadians(newAngle)));
                Log.d("dist;", "dist: "  + distance);
                Log.d("dist;", "angle: "  + newAngle);
                Log.d("dist;", "dist conversion: "  + MAX_RADIUS_OFFSET*distance/zoomManager.get_curr_zoom_max());
                Log.d("dist;", "xShift: "  + xShift);
                Log.d("dist;", "yShift: "  + yShift);
                nameLayout.setMargins(((ImageView) findViewById(R.id.compassImage)).getWidth()/2 + xShift,((ImageView) findViewById(R.id.compassImage)).getHeight()/2 - yShift,0,0);
                nameView.setLayoutParams(nameLayout);
                dotView.setVisibility(View.INVISIBLE);
                nameView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void handleNameOverlap(){
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                ImageView compass = (ImageView) findViewById(R.id.compassImage);
                Log.d("timer", "Compass width: " + compass.getMeasuredWidth());
                ArrayList<View> friendViews = new ArrayList<View>();
                Log.d("timer", "calm");
                friendMap.forEach((uid,view) -> {
                    if (view.get("text").getVisibility() == View.VISIBLE) {
                        friendViews.add(view.get("text"));
                    }
                });
                friendViews.sort((v1,v2) -> (int)(v1.getX()-v2.getX()));
                for (int i = 0; i< friendViews.size(); i++) {
                    Log.d("timer", " NEW STUFF " + ((TextView)friendViews.get(i)).getText());
                }
                for(int i = 0; i < friendViews.size(); i++) {
                    TextView view1 = (TextView)friendViews.get(i);
                    view1.measure(0,0);
                    while (view1.getText().length() > 0 && view1.getMeasuredWidth() + view1.getX() > ((ImageView)findViewById(R.id.compassImage)).getWidth()/2+Math.sqrt(Math.pow(MAX_DIST,2) - (Math.pow((double)(view1.getY()-((ImageView)findViewById(R.id.compassImage)).getHeight()/2),2)))){
                        Log.d("math sin", "" + view1.getMeasuredWidth() + view1.getX());
                        Log.d("math sin", "" + Math.sqrt(Math.pow(MAX_DIST,2) - (Math.pow((double)(view1.getY()-((ImageView)findViewById(R.id.compassImage)).getHeight()/2),2))));
                        view1.setText(view1.getText().subSequence(0,view1.getText().length()-1));
                        view1.measure(0,0);
                    }
                }
                int i = 0;
                while (i < friendViews.size()-1) {
                    TextView view1 = (TextView)friendViews.get(i);
                    view1.measure(0,0);
                    TextView view2 = (TextView)friendViews.get(i+1);
                    view2.measure(0,0);
                    if (view1.getX() == 0.0 || view2.getX() == 0.0) {
                        break;
                    }
                    if (view1.getX() == view2.getX() && view1.getY() == view2.getY()){
                        view1.setText(view1.getText() + " & " + view2.getText());
                        view2.setText("");
                        friendViews.remove(i+1);
                    }
                    else if (view1.getY() <= view2.getY() && view1.getY()+view1.getMeasuredHeight() >= view2.getY()
                            || view1.getY() <= view2.getY()+view2.getMeasuredHeight() && view1.getY()+view1.getMeasuredHeight() >= view2.getY()+view2.getMeasuredHeight()){
                        while (view1.getMeasuredWidth() + view1.getX() > view2.getX() && view1.getText().length() > 0){
                            Log.d("timer", i + "  " + view1.getText() + "  ,   " + view2.getText());
                            view1.setText(view1.getText().subSequence(0,view1.getText().length()-1));
                            view1.measure(0,0);
                        }
                    }
                    Log.d("timer", i + "  " + view1.getX());
                    Log.d("timer", i + "  " + view2.getMeasuredWidth());
                    i++;
                }
            }
        });
    }



    private void displayDotOnEdge(String uid, double angle){
        Log.d("DOT-ON-EDGE", "displaying dot on edge");
        Log.d("DOT-ON-EDGE","" + angle + "   " + uid);
        runOnUiThread(new  Runnable()
        {
            public void run()
            {
                HashMap<String, View> friendViews = friendMap.get(uid);
                View nameView = friendViews.get("text");
                View dotView = friendViews.get("dot");
                ConstraintLayout.LayoutParams dotLayout = (ConstraintLayout.LayoutParams) dotView.getLayoutParams();
                dotLayout.circleAngle = (float) angle;
                dotLayout.circleRadius = (int) zoomManager.getCompassWidth()/2;
                dotView.setLayoutParams(dotLayout);
                nameView.setVisibility(View.INVISIBLE);
                dotView.setVisibility(View.VISIBLE);

//                counter += 1;
//                ImageView iv = new ImageView(MainActivity.this);
//                iv.setImageResource(R.drawable.redcircle);
//                iv.setId(View.generateViewId());
//                layout.addView(iv);
//
//                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) iv.getLayoutParams();
//                lp.circleConstraint = R.id.compasslayout;
//                lp.circleRadius = ((ImageView) findViewById(R.id.compassImage)).getWidth()/2 - MAX_RADIUS_OFFSET;
//                lp.circleAngle = (float) angle;
//                lp.width = 30;
//                lp.height = 30;
//                iv.setLayoutParams(lp);
            }
        });

    }

//    private void setCircleRadius(ImageView circle, int radius) {
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) circle.getLayoutParams();
//        layoutParams.circleRadius = radius;
//        circle.setLayoutParams(layoutParams);
//    }
//
//    private void setCircleSize(ImageView circle, int size) {
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) circle.getLayoutParams();
//        layoutParams.width = size;
//        layoutParams.height = size;
//        circle.setLayoutParams(layoutParams);
//    }

    public FriendManager getFriendManager() {
        return this.friendManager;
    }

    public void mockAddFriend(User user) {
        this.friendManager.addFriend(user);
    }

    public void setOrientationMock(MutableLiveData<Float> ld) {
        orientationService.setMockOrientationSource(ld);
    }

    public void mockCompassUpdate() {
        float az = this.orientationService.getOrientation().getValue();
    }

    @Override
    protected void onPause() {
        super.onPause();
        orientationService.unregisterSensorListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        orientationService = OrientationService.singleton(this);
        orientationService.registerSensorListeners();
    }
/*
//
//
//        orientationService = OrientationService.singleton(MainActivity.this);
//        orientationService.getOrientation().observe(this, azimuth -> {
//            compassUpdate(azimuth);
//        });
//
//        myloc = new MyLocation(0, 0);
//        locationRelater = new PointRelation(myloc);
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
//        ImageView redCircle = (ImageView) findViewById(R.id.redImage);
//        ImageView blueCircle = (ImageView) findViewById(R.id.blueImage);
//        ImageView yellowCircle = (ImageView) findViewById(R.id.yellowImage);
//        redCircle.setVisibility(View.INVISIBLE);
//        blueCircle.setVisibility(View.INVISIBLE);
//        yellowCircle.setVisibility(View.INVISIBLE);
//
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
//            @Override
//            public void onLocationChanged(@NonNull Location location) {
//                firstLocUpdate = true;
//                myloc.setLon(location.getLongitude());
//                myloc.setLat(location.getLatitude());
//                compassUpdate(orientationService.getOrientation().getValue());
//                //lon.setText("Longitude: " + String.valueOf(myloc.getLon()));
//                //lat.setText("Latitude: " + String.valueOf(myloc.getLat()));
//            }
//        });
//
//        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
//        String locName = preferences.getString("locationOneName", "N/A");
//        if (locName == "N/A") {
//            Intent intent = new Intent(this, InputLocation.class);
//            startActivity(intent);
//        }
//    }
//




//
//    private void updateSpecificCircle(String locName, String locLat, String locLon, ImageView circle, ImageView legendCircle, TextView legendText) {
//        if (locName != "N/A") {
//            Double newAngle = locationRelater.angleCalculation(Double.parseDouble(locLat), Double.parseDouble(locLon));
//            legendText.setText(locName);
//            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) circle.getLayoutParams();
//            layoutParams.circleAngle = newAngle.floatValue();
//            circle.setLayoutParams(layoutParams);
//            if (firstLocUpdate) {
//                legendCircle.setVisibility(View.VISIBLE);
//                legendText.setVisibility(View.VISIBLE);
//                circle.setVisibility(View.VISIBLE);
//                circle.bringToFront();
//            }
//        } else {
//            legendCircle.setVisibility(View.INVISIBLE);
//            legendText.setVisibility(View.INVISIBLE);
//            circle.setVisibility(View.INVISIBLE);
//        }
//    }


        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        String locName = preferences.getString("locationOneName", "N/A");
        if (locName == "N/A") {
            Intent intent = new Intent(this, InputLocation.class);
            startActivity(intent);
        }
    }

    private void compassUpdate(Float az) {
        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        String locName;
        String locLat;
        String locLon;
        ImageView circle;
        ImageView legendCircle;
        TextView legendText;

        ImageView compassCircle;

//        First Point
        locName = preferences.getString("locationOneName", "N/A");
        locLat = preferences.getString("locationOneLat", "N/A");
        locLon = preferences.getString("locationOneLon", "N/A");
        circle = (ImageView) findViewById(R.id.redImage);
        legendCircle = (ImageView) findViewById(R.id.redlegendcircle);
        legendText = (TextView) findViewById(R.id.redlegendtext);

        updateSpecificCircle(locName, locLat, locLon, circle, legendCircle, legendText);
        rotateLoc(circle, az);

        //        Second Point
        locName = preferences.getString("locationTwoName", "N/A");
        locLat = preferences.getString("locationTwoLat", "N/A");
        locLon = preferences.getString("locationTwoLon", "N/A");
        circle = (ImageView) findViewById(R.id.blueImage);
        legendCircle = (ImageView) findViewById(R.id.bluelegendcircle);
        legendText = (TextView) findViewById(R.id.bluelegendtext);

        updateSpecificCircle(locName, locLat, locLon, circle, legendCircle, legendText);
        rotateLoc(circle, az);

        //        Third Point
        locName = preferences.getString("locationThreeName", "N/A");
        locLat = preferences.getString("locationThreeLat", "N/A");
        locLon = preferences.getString("locationThreeLon", "N/A");
        circle = (ImageView) findViewById(R.id.yellowImage);
        legendCircle = (ImageView) findViewById(R.id.yellowlegendcircle);
        legendText = (TextView) findViewById(R.id.yellowlegendtext);

        updateSpecificCircle(locName, locLat, locLon, circle, legendCircle, legendText);
        rotateLoc(circle, az);

        // Update compass overlay
        compassCircle = (ImageView) findViewById(R.id.compassImage);
        rotateImg(compassCircle, az);

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
                circle.bringToFront();
            }
        } else {
            legendCircle.setVisibility(View.INVISIBLE);
            legendText.setVisibility(View.INVISIBLE);
            circle.setVisibility(View.INVISIBLE);
        }
    }

*/

/*
    /**
     * Rotates the selected image view about a certain angle based on heading.
     * @param img The image view compass to rotate
     * @param az Azimuth from current heading

    public void rotateImg(ImageView img, Float az) {
        if (az == null) { az = 0.0F; }
        double azDeg = Utilities.radToDeg(az);
        img.setRotation((float) -(azDeg));
    }


    /**
     * Rotates location circles
     * @param img Location circle to rotate
     * @param az Azimuth from current heading

    public void rotateLoc(ImageView img, Float az) {
        if (az == null) { return; }
        double azDeg = Utilities.radToDeg(az);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) img.getLayoutParams();
        lp.circleAngle -= azDeg;
        img.setLayoutParams(lp);
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
            setCircleRadius(circleOne, (int) (360 + 0.5f));
            setCircleRadius(circleTwo, (int) (410 + 0.5f));
            setCircleRadius(circleThree, (int) (460 + 0.5f));
            setCircleSize(circleOne, (int) (50 + 0.5f));
            setCircleSize(circleTwo, (int) (50 + 0.5f));
            setCircleSize(circleThree, (int) (50 + 0.5f));
        } else if (overlay12) {
            setCircleRadius(circleOne, (int) (360 + 0.5f));
            setCircleRadius(circleTwo, (int) (410 + 0.5f));
            setCircleSize(circleOne, (int) (50 + 0.5f));
            setCircleSize(circleTwo, (int) (50 + 0.5f));
        } else if (overlay23) {
            setCircleRadius(circleTwo, (int) (360 + 0.5f));
            setCircleRadius(circleThree, (int) (410 + 0.5f));
            setCircleSize(circleTwo, (int) (50 + 0.5f));
            setCircleSize(circleThree, (int) (50 + 0.5f));
        } else if (overlay31) {
            setCircleRadius(circleOne, (int) (360 + 0.5f));
            setCircleRadius(circleThree, (int) (410 + 0.5f));
            setCircleSize(circleOne, (int) (50 + 0.5f));
            setCircleSize(circleThree, (int) (50 + 0.5f));
        } else {
            setCircleRadius(circleOne, (int) (410 + 0.5f));
            setCircleRadius(circleTwo, (int) (410 + 0.5f));
            setCircleRadius(circleThree, (int) (410 + 0.5f));
            setCircleSize(circleOne, (int) (50 + 0.5f));
            setCircleSize(circleTwo, (int) (50 + 0.5f));
            setCircleSize(circleThree, (int) (50 + 0.5f));

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

    public MyLocation getLocation() {
        return myloc;
    }

    public void setOrientationMock(MutableLiveData<Float> ld) {
        orientationService.setMockOrientationSource(ld);
    }

    public void mockCompassUpdate() {
        float az = this.orientationService.getOrientation().getValue();
        this.compassUpdate(az);
    }

    @Override
    protected void onPause() {
        super.onPause();
        orientationService.unregisterSensorListeners();
    }
    @Override
    protected void onResume() {
        super.onResume();

        orientationService.registerSensorListeners();

        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        String heading_string = preferences.getString("heading", "N/A");
        if (heading_string != "N/A") {

            float heading_float = Float.parseFloat(heading_string);
            preferences.edit().remove("heading").commit();

            MutableLiveData<Float> heading_data = new MutableLiveData<Float>();
            heading_data.setValue(heading_float);

            orientationService.setMockOrientationSource(heading_data);
        }


        compassUpdate(0.0F);
    }

    public void onNewLocationBtnClicked(View view) {
        Intent intent = new Intent(this, InputLocation.class);

//        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
//        String locationThreeName = preferences.getString("locationThreeName", "N/A");
//
//        Log.i("Location 3", locationThreeName);
//        if (locationThreeName != "N/A") {
//            Utilities.showAlert(this, "You cannot save any more locations");
//        } else {
        startActivity(intent);
//        }
     */






    public void onAddFriendClicked(View view) {
        Intent intent = new Intent(this, AddFriendsActivity.class);
        startActivity(intent);

    }

    public void zoomInClicked(View view){
        zoomManager.zoomIn();
        updateZoomButtons();
        updateCompassImage();
//        Log.d("PRINTING TEST:", "Zoom in");
    }
    public void zoomOutClicked(View view){
        zoomManager.zoomOut();
        updateZoomButtons();
        updateCompassImage();
//        Log.d("PRINTING TEST:", "Zoom out");

    }

    private void updateZoomButtons(){
        Button zoomInBtn = (Button) findViewById(R.id.zoomIn);
        Button zoomOutBtn = (Button) findViewById(R.id.zoomOut);

        boolean canZoomIn = zoomManager.canZoomIn();
        boolean canZoomOut = zoomManager.canZoomOut();

        zoomInBtn.setEnabled(canZoomIn);
        zoomOutBtn.setEnabled(canZoomOut);


    }
}


