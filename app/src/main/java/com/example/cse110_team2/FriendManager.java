package com.example.cse110_team2;

import android.content.SharedPreferences;
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
        String userName = preferences.getString("user", "N/A");
        String publicID = preferences.getString("publicID", "N/A");
        String privateID = preferences.getString("privateID", "N/A");

        User user = api.getUserAsync(publicID);
        if(user != null) {
            user.private_code = privateID;
            user.name = userName;
            if(mainUser ==null)
                mainUser = user;
        }


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

    public void setMainUser(User user){
        mainUser = user;
    }

    public User getMainUser(){
        return mainUser;
    }
}
