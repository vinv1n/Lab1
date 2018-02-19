package com.tasklist.vinvin.lab4.DataStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Database for location data.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;

    private String TABLE_NAME = "location";
    private String LOCATION_NAME = "name";
    private String ID_ = "id";
    private String LAT = "lat";
    private String LON = "lon";

    public DataBaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID_ + " INTEGER PRIMARY KEY" + LOCATION_NAME +
                " TEXT " + LAT + " TEXT " + LON + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*
        Methods to access data in database
     */
    public void addLocation(Location location){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOCATION_NAME, location.getLocation_name());
        values.put(LAT, location.getLatitude());
        values.put(LON, location.getLongitude());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public Location getLocation(int id){
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME, new String[]{ID_, LOCATION_NAME, LAT, LON}, ID_ + "=?",
                    new String[]{String.valueOf(id)},
                    null, null, null, null);

            if (cursor != null){
                cursor.moveToFirst();
            }

            Location location = new Location(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3));

            cursor.close();
            return location;
        } catch (NullPointerException e) {
            close();
        }
        return null;
    }

    public List<Location> getAllLocations(){
        List<Location> locations = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                Location location = new Location();
                location.setId_(Integer.parseInt(cursor.getString(0)));
                location.setLocation_name(cursor.getString(1));
                location.setLatitude(cursor.getString(2));
                location.setLongitude(cursor.getString(3));

                // adds a new location to list
                locations.add(location);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return locations;
    }

    public void deleteLocation(Location location){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, ID_ + " = ?",
                new String[]{String.valueOf(location.getId_())});

        db.close();

    }

    public int updateLocation(Location location){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LOCATION_NAME, location.getLocation_name());
        values.put(LAT, location.getLatitude());
        values.put(LON, location.getLongitude());

        return db.update(TABLE_NAME, values, ID_ + " = ?",
                new String[]{String.valueOf(location.getId_())});
    }

}
