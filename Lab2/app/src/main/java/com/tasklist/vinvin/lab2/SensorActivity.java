package com.tasklist.vinvin.lab2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.DialerKeyListener;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends Activity
        implements SensorEventListener {

    private Sensor magnet;
    private Sensor acc;
    private SensorManager manager;

    private TextView sensorValues;
    private TextView sensorName;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorValues = (TextView) findViewById(R.id.values);
        sensorName = (TextView) findViewById(R.id.SensorType);


        try {

            magnet = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);

        } catch (NullPointerException e) {

            acc = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED){
            updateView(event);
        } else {
            // this is not really needed, but is here anyways
            updateView(event);
        }

    }
    private void updateView(SensorEvent event) {

        StringBuilder builder = new StringBuilder();

        float[] values = event.values;

        double result = Math.sqrt(Math.pow(values[0], 2) + Math.pow(values[1], 2)
                + Math.pow(values[2], 2));

        builder.append(Math.round(result));

        sensorName.setText("Sensor type: " + magnet.getName());
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

    @Override
    protected void onStop() {
        super.onStop();
        System.exit(1);
    }
}
