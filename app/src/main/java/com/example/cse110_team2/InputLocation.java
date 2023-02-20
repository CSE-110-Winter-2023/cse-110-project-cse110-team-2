package com.example.cse110_team2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InputLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_location);
    }


    public void onLocationSaveBtnClicked(View view) {

        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        String locationThreeName = preferences.getString("locationThreeName", "N/A");

        Log.i("Location 3", locationThreeName);
        if (locationThreeName != "N/A") {
            Utilities.showAlert(this, "You cannot save any more locations");
        } else {
            saveLocation();
            finish();
        }

    }

    public void onHeadingSaveBtnClicked(View view){
        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        SharedPreferences.Editor editor = preferences.edit();
        TextView newHeading = (TextView) findViewById(R.id.heading_text);
        String heading = newHeading.getText().toString();
        editor.putString("heading", heading);
        finish();

    }

    public void saveLocation(){

        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        SharedPreferences.Editor editor = preferences.edit();

        String locationOneName = preferences.getString("locationOneName", "N/A");
        String locationTwoName = preferences.getString("locationTwoName", "N/A");
        String locationThreeName = preferences.getString("locationThreeName", "N/A");

        TextView newLocationName = (TextView) findViewById(R.id.location_name);
        TextView newLocationLat = (TextView) findViewById(R.id.location_lat);
        TextView newLocationLon = (TextView) findViewById(R.id.location_lon);

        String newLocName = newLocationName.getText().toString();
        String newLocLat = newLocationLat.getText().toString();
        String newLocLon = newLocationLon.getText().toString();

        if(newLocName.length() == 0){
            return;
        }

        if(locationOneName == "N/A"){
            editor.putString("locationOneName", newLocName);
            editor.putString("locationOneLat", newLocLat);
            editor.putString("locationOneLon", newLocLon);

        }else if(locationTwoName == "N/A"){
            editor.putString("locationTwoName", newLocName);
            editor.putString("locationTwoLat", newLocLat);
            editor.putString("locationTwoLon", newLocLon);

        }else if(locationThreeName == "N/A"){
            editor.putString("locationThreeName",newLocName);
            editor.putString("locationThreeLat", newLocLat);
            editor.putString("locationThreeLon", newLocLon);

        }else{

        }
        editor.apply();
    }
}
