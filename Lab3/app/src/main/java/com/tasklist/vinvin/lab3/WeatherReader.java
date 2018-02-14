package com.tasklist.vinvin.lab3;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 *
 * Created by vinvin on 7.2.2018.
 */

public class WeatherReader extends IntentService {

    private String basic_uri = "http://api.openweathermap.org/data/2.5/weather";
    private SharedPreferences sharedPreferences;

    public WeatherReader(){
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        try {

            URL ulr = new URL("http://api.openweathermap.org/data/2.5/forecast?id=524901&A" +
                    "PPID=7c4f7f159556bc489598c5b219a5fa02");

            HttpURLConnection connection = (HttpURLConnection) ulr.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(6000);
            connection.setReadTimeout(6000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.
                        getInputStream()));

                StringBuilder Stringbuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    Stringbuilder.append(line);
                }

                bufferedReader.close();

                sharedPreferences.edit()
                        .putString("response", Stringbuilder.toString())
                        .apply();

            }

        } catch (Exception e){
            Log.e("error", e.getMessage());
        }
    }
    private String buildUri(){

        Uri.Builder builder = Uri.parse(basic_uri).buildUpon();
        String city = "Oulu,fi";
        builder.appendQueryParameter("city", city);

        return builder.toString();
    }
}
