package com.tasklist.vinvin.lab3;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int MY_PERMISSIONS = 2;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ensurePermissions();
    }

    private void startService(){
        Intent intent = new Intent(this, WeatherReader.class);
        this.startService(intent);
    }

    private void ensurePermissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)){

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED){
                    startService();
                    
                    String response = preferences.getString("response", "");

                } else {
                    // these permissions are mandatory!
                    System.exit(1);

                }
        }
    }

    public void makeNotification() {

        // onclick action not yet done
        if (android.os.Build.VERSION.SDK_INT <= 26) {
            try {

                NotificationCompat.Builder notificationCompat = new
                        NotificationCompat.Builder(getApplicationContext());
                notificationCompat.setLights(Color.GREEN, 3000, 3000);
                notificationCompat.setVibrate(new long[]{1000, 1000, 1000, 1000});
                notificationCompat.setSound(Settings.System.DEFAULT_ALARM_ALERT_URI);
                notificationCompat.setContentTitle("Location found!");
                int notID = 001;

                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                notificationCompat.setStyle(inboxStyle);

                NotificationManager Notificationmanager = (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);

                Intent notifyIntent = new Intent(this, MainActivity.class);
                PendingIntent content = PendingIntent.getActivity(this, 0,
                        notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                notificationCompat.setContentIntent(content);

                Notificationmanager.notify(notID, notificationCompat.build());

            } catch (NullPointerException e) {

                // something else should be done here
                Toast.makeText(this, "Something went wrong!",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            try {

                NotificationManager manager = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

                String id = "01";
                CharSequence name = "weather";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(id, name,
                        importance);
                notificationChannel.setDescription("Weather info");
                notificationChannel.enableLights(true);
                notificationChannel.setShowBadge(true);
                AudioAttributes audioAttributes = notificationChannel.getAudioAttributes();
                notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI,
                        audioAttributes);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400,
                        500, 400, 300, 200, 400});
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

                manager.createNotificationChannel(notificationChannel);
                manager.notify();

            } catch (NullPointerException ee) {

                Toast.makeText(this, "Something went wrong!",
                        Toast.LENGTH_LONG).show();
                // returns to main activity if notification throws nullpointerexception
                setContentView(R.layout.activity_main);
            }
        }
    }
}
