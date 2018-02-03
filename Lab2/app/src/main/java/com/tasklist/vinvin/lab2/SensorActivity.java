package com.tasklist.vinvin.lab2;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * Created by vinvin on 30.1.2018.
 */

public class SensorActivity extends Activity
        implements SensorEventListener {

    private Sensor magnet;
    private Sensor acc;
    private SensorManager manager;
    TextView sensorValues;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorValues = (TextView) findViewById(R.id.values);

        try {

            magnet = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        } catch (NullPointerException e) {

            acc = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            updateView(event);
        }

    }
    private void updateView(SensorEvent event) {

        StringBuilder builder = new StringBuilder();

        float[] values = event.values;

        for(float value : values){
            builder.append(value);
            builder.append("\n");
        }

        sensorValues.setText(builder);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
}
