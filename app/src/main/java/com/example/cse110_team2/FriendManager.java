package com.example.cse110_team2;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;


public class FriendManager {

    public volatile static FriendManager instance = null;

    private ArrayList<User> friends;

    private ArrayList<User> mockFriends;

    private User mainUser;

    public FriendManager(){
        friends = new ArrayList<User>(0);
        mockFriends = new ArrayList<User>(0);
        mainUser = null;
    }

    public static FriendManager provide(){
        if(instance == null){
            instance = new FriendManager();
        }
        instance.removeNull();

        return instance;
    }

    public void removeNull() {
        for (int i = 0; i < friends.size(); i++) {
            var f = friends.get(i);
            if ((f == null) || (f.uid.equals("")) || (f.name == null)) {
                friends.remove(i);
            }
        }
    }

    public ArrayList<User> getFriends(){
        Log.d("CLICK 3", "friend length " + friends.size());
        return friends;
    }
    public ArrayList<User> getMockFriends(){
        return mockFriends;
    }

    public void addFriend(User user){
        // check if friend already exists
        Log.d("CLICK FRIEND MANAGER", "user added" + user.name);
        boolean existsFlag = false;
        for (User us : this.getFriends()) {
            if (us.uid.equals(user.uid)) { existsFlag = true; }
        }

        if (existsFlag == false) { friends.add(user); }
    }

    public void loadFriendsFromSharedPreferences(SharedPreferences preferences){
        SharedCompassAPI api = SharedCompassAPI.provide();
        String text = preferences.getString("friends", "");
        String userName = preferences.getString("user", "N/A");
        String publicID = preferences.getString("publicID", "N/A");
        String privateID = preferences.getString("privateID", "N/A");

//        Log.d("CLICK", "checking publicID " + publicID);
        User user = api.getUserAsync(publicID);
//        Log.d("CLICK", "checking USER " + user.uid + "  " + user.name);
        if(user != null && user.name != null) {
            user.private_code = privateID;
            user.name = userName;
            if(mainUser ==null)
                mainUser = user;
        }


        friends = new ArrayList<User>(0);
        if(text.length() > 0) {
//            Log.d("CLICK 4", "in get data from shared prefs");
//            Log.d("CLICK 4", "length " + friends.size());
//            Log.d("CLICK 11", "text  _+ text " + text + "   " + text.length());
            String[] uids = text.split(",");
//            Log.d("CLICK 10", "length: " + uids.length);
            for (int i = 0; i < uids.length; i++) {
                friends.add(api.getUserAsync(uids[i]));
            }
//            Log.d("CLICK 5", "length " + friends.size());
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

    public void setMainUser(User user){
        mainUser = user;
    }

    public User getMainUser(){
        return mainUser;
    }
}
