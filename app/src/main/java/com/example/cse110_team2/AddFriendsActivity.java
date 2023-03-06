package com.example.cse110_team2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class AddFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        TextView id = (TextView)findViewById(R.id.ID);
        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        String publicID = preferences.getString("publicID","NA");
        id.setText("ID: "+ publicID);
    }
    // TODO: function to add friends
    public void AddBtnClicked(){
        finish();
    }
}