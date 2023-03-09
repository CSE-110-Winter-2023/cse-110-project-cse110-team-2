package com.example.cse110_team2;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FriendManager {

    public volatile static FriendManager instance = null;

    private ArrayList<User> friends;

    private ArrayList<User> mockFriends;

    public FriendManager(){
        friends = new ArrayList<User>(0);
        mockFriends = new ArrayList<User>(0);
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
    public ArrayList<User> getMockFriends(){
        return mockFriends;
    }

    public void addFriend(User user){
        // check if friend already exists
        boolean existsFlag = false;
        for (User us : this.getFriends()) {
            if (us.uid.equals(user.uid)) { existsFlag = true; }
        }

        if (existsFlag == false) { friends.add(user); }
    }

    public void loadFriendsFromSharedPreferences(SharedPreferences preferences){
        SharedCompassAPI api = SharedCompassAPI.provide();
        String text = preferences.getString("friends", "");
        friends = new ArrayList<User>(0);

        String [] uids = text.split(",");

        for(int i =0; i < uids.length; i++){
            friends.add(api.getUserAsync(uids[i]));
        }
    }

    public void saveFriendsToSharedPreferences(SharedPreferences preferences){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < friends.size(); i++) {
            sb.append(friends.get(i).uid + ",");
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("friends", sb.toString());
        editor.commit();
    }

    public void updateFriendLocations(){
        SharedCompassAPI api = SharedCompassAPI.provide();
        for(int i = 0; i < friends.size(); i++){
            friends.set(i, api.getUserAsync(friends.get(i).uid));
        }
    }
}
