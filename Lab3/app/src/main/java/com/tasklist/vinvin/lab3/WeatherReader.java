package com.tasklist.vinvin.lab3;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.prefs.Preferences;

/**
 *
 * Created by vinvin on 7.2.2018.
 */

public class WeatherReader extends IntentService {

    private String basic_uri = "http://api.openweathermap.org/data/2.5/weather";

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

            }

        } catch (Exception e){
            System.exit(1);
        }
    }
    private String buildUri(){

        Uri.Builder builder = Uri.parse(basic_uri).buildUpon();
        String city = "Oulu,fi";
        builder.appendQueryParameter("city", city);

        return builder.toString();
    }

    @Override
    public void onDestroy() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Intent intent = new Intent(getApplicationContext(), SetAlarm.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000*60*10, pendingIntent);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }
}
