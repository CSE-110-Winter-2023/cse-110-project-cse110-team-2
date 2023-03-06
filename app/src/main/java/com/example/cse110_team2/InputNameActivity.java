package com.example.cse110_team2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

public class InputNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);
    }
    public void onNameSaveBtnClicked(View view) {

        SharedPreferences preferences = getSharedPreferences("IDvalue", 0);
        SharedPreferences.Editor editor = preferences.edit();
        String publicID = UUID.randomUUID().toString();
        String  privateID = UUID.randomUUID().toString();

        TextView name = (TextView) findViewById(R.id.name);
        String newName = name.getText().toString();

        if (newName.length() == 0) {
            return;
        } else {
            editor.putString("user", newName);
            editor.putString("publicID", publicID);
            editor.putString("privateID", privateID);
            editor.apply();
            Log.d("debug",publicID);
            User user = User.createUser(newName,publicID,privateID);
            Intent intent = new Intent(this, AddFriendsActivity.class);
            startActivity(intent);

            SharedCompassAPI compassAPI = new SharedCompassAPI();
            compassAPI.putUserAsync(user);
            finish();
        }
    }

}