package com.example.cse110_team2;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FriendManager {

    public volatile static FriendManager instance = null;

    private ArrayList<User> friends;

    public FriendManager(){
        friends = new ArrayList<User>();
    }

    public static FriendManager provide(){
        if(instance == null){
            instance = new FriendManager();
        }
        return instance;
    }

    public ArrayList<User> getFriends(){
        return friends;
    }

    public void loadFriendsFromSharedPreferences(SharedPreferences preferences){
        Gson gson = new Gson();
        Type type = new TypeToken<List<User>>() {}.getType();

        String json = preferences.getString("friends", "");
        friends = gson.fromJson(json, type);

    }

    public void saveFriendsToSharedPreferences(SharedPreferences preferences){
        Gson gson = new Gson();
        SharedPreferences.Editor editor = preferences.edit();
        String json = gson.toJson(friends);

        editor.putString("friends", json);
        editor.commit();

    }
}
