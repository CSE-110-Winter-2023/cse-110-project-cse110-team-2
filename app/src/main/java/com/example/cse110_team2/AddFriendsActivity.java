package com.example.cse110_team2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddFriendsActivity extends AppCompatActivity {
    private FriendManager friendManager;
    private SharedCompassAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        TextView id = (TextView)findViewById(R.id.ID);
        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        String publicID = preferences.getString("publicID","NA");
        id.setText("ID: "+ publicID);

        friendManager = FriendManager.provide();
        api = new SharedCompassAPI();
    }


    public void onAddBtnClicked(View view){
        TextView id = (TextView)findViewById(R.id.friendID);
        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        User friend = api.getUserAsync(id.getText().toString());

        friendManager.addFriend(friend);
        friendManager.saveFriendsToSharedPreferences(preferences);


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}