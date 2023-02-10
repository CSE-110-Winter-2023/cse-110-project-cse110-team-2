package com.example.cse110_team2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onNewLocationBtnClicked(View view) {
        Intent intent = new Intent(this, InputLocation.class);

        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        String locationThreeName = preferences.getString("locationThreeName", "N/A");

        Log.i("Location 3", locationThreeName);
        if(locationThreeName != "N/A"){
            Utilities.showAlert(this, "You cannot save any more locations");
        }else{
            startActivity(intent);
        }

    }
}