package com.tasklist.vinvin.lab4;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tasklist.vinvin.lab4.DataStorage.DataBaseHandler;
import com.tasklist.vinvin.lab4.DataStorage.Location;

import java.util.List;

import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends AppCompatActivity
        implements LocationListener {


    DataBaseHandler db;
    static final int MY_PERMISSIONS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // checks selfPermissions
        checkPermissions();

        // open the connection to Database
        initDB();
    }

    private void initDB(){
        db = new DataBaseHandler(this);
    }

    private void initLocationData(){
        try {
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(GPS_PROVIDER, 5000,
                    10, this);
        } catch (SecurityException e){
            checkPermissions();
        }
    }

    // floating action button might be good for this
    private void saveLocation(){

    }

    private void checkPermissions(){
        int check = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (check != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initLocationData();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Permissions denied", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        List<Location> locationList = db.getAllLocations();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
