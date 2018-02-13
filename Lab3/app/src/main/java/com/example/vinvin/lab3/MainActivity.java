package com.example.vinvin.lab3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWeatherInfo();
    }

    private void getWeatherInfo() {
        Intent intent = new Intent(this, FetchWeatherInfo.class);
        this.startService(intent);
    }

    private void makeNotification() {

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
