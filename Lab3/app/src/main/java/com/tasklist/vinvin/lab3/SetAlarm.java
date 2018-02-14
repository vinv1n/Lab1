package com.tasklist.vinvin.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by vinvin on 14.2.2018.
 */

public class SetAlarm extends BroadcastReceiver {

    SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, WeatherReader.class);
        context.startService(intent1);

        String response = preferences.getString("response", "");

        Intent notificationIntent = new Intent(context, NotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context);

        Notification notification = notificationCompat.setContentTitle("Weather info")
                .setLights(Color.GREEN, 3000, 3000)
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI)
                .setContentTitle("Weather info!")
                .setContentIntent(pendingIntent).build();

        NotificationManager Notificationmanager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);


        Notificationmanager.notify(0, notification);
    }
}
