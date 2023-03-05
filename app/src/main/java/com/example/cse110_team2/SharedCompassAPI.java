package com.example.cse110_team2;

import android.util.Log;

import androidx.annotation.AnyThread;
import androidx.annotation.WorkerThread;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SharedCompassAPI {

    public volatile static SharedCompassAPI instance = null;
    private OkHttpClient client;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public SharedCompassAPI(){
        this.client = new OkHttpClient();
    }

    public static SharedCompassAPI provide(){
        if(instance == null){
            instance = new SharedCompassAPI();
        }
        return instance;
    }

    @WorkerThread
    public User getUser(String uid){

        var request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + uid)
                .method("GET", null)
                .build();

        try (var response = client.newCall(request).execute()) {

            assert response.body() != null;
            var body = response.body().string();
            Log.i("get result",   body);
            return User.fromJSON(body);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @WorkerThread
    public void addUser(User user){
        RequestBody body = RequestBody.create(user.toJSON(), JSON);

        Request request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + user.uid)
                .method("PUT", body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            var result = response.body().toString();
            Log.i("push result",   result);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @WorkerThread
    public void updateUser(User user){
        RequestBody body = RequestBody.create(user.toJSON(), JSON);

        Request request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + user.uid)
                .method("PATCH", body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            var result = response.body().toString();
            Log.i("patch result",   result);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AnyThread
    public User getUserAsynch(String uid){
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> getUser(uid));

        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    @AnyThread
    public void addUserAsynch(User user){
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> addUser(user));

    }

    @AnyThread
    public void updateUserAsynch(User user){
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> updateUser(user));

    }

}
