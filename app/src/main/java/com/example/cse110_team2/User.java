package com.example.cse110_team2;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class User {

    @PrimaryKey
    @SerializedName("public_code")
    public String uid;

    @SerializedName("label")
    public String name;

    @SerializedName("longitude")
    public float longitude;

    @SerializedName("latitude")
    public float latitude;

    @SerializedName("private_code")
    public String private_code;

//    @SerializedName("updated_at")
//    public long updatedAt = 0;

    public User(String name, String uid, float longitude, float latitude, String private_code){
       this.name = name;
       this.uid = uid;
       this.longitude = longitude;
       this.latitude = latitude;
       this.private_code = private_code;
    }

    public static User createUser(@NonNull String name,String publicID,String privateID){
        return new User(name,publicID, -1, -1, privateID);
    }

    public static User fromJSON(String json) {
        return new Gson().fromJson(json, User.class);
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }
    public void setLongitude(float longitude){
        this.longitude = longitude;
    }

    public void setLatitude(float latitude){
        this.latitude = latitude;
    }

}
