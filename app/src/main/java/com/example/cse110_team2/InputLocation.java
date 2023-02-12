package com.example.cse110_team2;

import androidx.appcompat.app.AppCompatActivity;

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
        saveLocation();
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


        if(locationOneName == "N/A"){
            editor.putString("locationOneName", newLocationName.getText().toString());
            editor.putString("locationOneLat", newLocationLat.getText().toString());
            editor.putString("locationOneLon", newLocationLon.getText().toString());

        }else if(locationTwoName == "N/A"){
            editor.putString("locationTwoName", newLocationName.getText().toString());
            editor.putString("locationTwoLat", newLocationLat.getText().toString());
            editor.putString("locationTwoLon", newLocationLon.getText().toString());

        }else if(locationThreeName == "N/A"){
            editor.putString("locationThreeName", newLocationName.getText().toString());
            editor.putString("locationThreeLat", newLocationLat.getText().toString());
            editor.putString("locationThreeLon", newLocationLon.getText().toString());

        }else{

        }
        editor.apply();
    }
}