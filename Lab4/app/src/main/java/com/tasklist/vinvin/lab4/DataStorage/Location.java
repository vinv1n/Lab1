package com.tasklist.vinvin.lab4.DataStorage;

/**
 *
 * Created by vinvin on 19.2.2018.
 */

public class Location {

    private int id;
    private String location_name;
    private String Longitude;
    private String Latitude;

    // constructor for location
    protected Location(int id, String location, String lon, String lat){
        this.id = id;
        this.location_name = location;
        this.Latitude = lat;
        this.Longitude = lon;
    }

    protected Location(){

    }

    public String getLatitude() {
        return Latitude;
    }

    public long getId_() {
        return id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public void setId_(int id) {
        this.id = id;
    }
}
