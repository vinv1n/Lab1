package com.example.vinvin.lab3;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 *
 * Created by vinvin on 13.2.2018.
 */

public class FetchWeatherInfo extends IntentService {

    SharedPreferences preferences;

    public FetchWeatherInfo(){
        super("FetchWeatherInfo");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent){

        String uri_string = "http://api.openweathermap.org/data/2.5/weather?q=Oulu,fi";

        try {
            URL url = new URL(uri_string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(6000);
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader
                        (connection.getInputStream()));

                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                preferences.edit()

                        .putString("information", stringBuilder.toString())
                        .apply();
            }
        } catch (Exception e) {
            System.exit(1);
        }

    }
}
