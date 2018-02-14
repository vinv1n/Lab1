package com.tasklist.vinvin.lab3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 *
 * Created by vinvin on 14.2.2018.
 */

public class NotificationActivity extends Activity {

    SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TextView view = (TextView) findViewById(R.id.main_text);
        view.setText(preferences.getString("response", ""));
        setContentView(R.layout.activity_main);
    }
}
