package com.example.cse110_team2;

import android.app.Activity;
import android.app.AlertDialog;

import java.util.Optional;

public class Utilities {

    public static Optional<Double> convertStringToDouble(String text){

        try{
            Double coordinate = Double.parseDouble(text);
            return Optional.of(coordinate);
        }catch(NumberFormatException e){
            return Optional.empty();
        }

    }

    public static void showAlert(Activity activity, String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert!")
                .setMessage(message)
                .setPositiveButton("Ok", (dialog, id) ->{
                    dialog.cancel();
                })
                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

}
