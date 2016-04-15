package de.andrew.demoZITF.myDataModels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 4/14/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "placesManager";

    // Places table name
    private static final String TABLE_PLACES = "place";
    private static final String TABLE_PLACE_SERVICES = "services";
    private static final String TABLE_PLACE_ACTIVITIES = "place";

    // Places Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_ALTITUDE = "altitude";
    private static final String KEY_P_O_Is = "pointsOfInterest";
    private static final String KEY_CULTURAL_RULES = "culturalRules";
    private static final String KEY_RULES = "rules";

    // for Services
    private static final String KEY_SERVICE_ID = "services_id";
    private static final String KEY_SERVICE_NAME = "name";
    private static final String KEY_SERVICE_PRICE = "price";
    private static final String KEY_PLACE_ID = "place_id";
    // for Activities
    private static final String KEY_ACTIVITY_ID = "activity_id";
    private static final String KEY_ACTIVITY_NAME = "name";
    private static final String KEY_ACTIVITY_PRICE = "price";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_PLACES + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT,"
                + KEY_TYPE + " TEXT, " + KEY_ALTITUDE + " INTEGER, "
                + KEY_CULTURAL_RULES + " TEXT, " + KEY_RULES + " TEXT, "
                + KEY_P_O_Is+ " TEXT"+ ");";
        db.execSQL(CREATE_PLACES_TABLE);
        String CREATE_PLACE_SERVICES_TABLE= "CREATE TABLE " + TABLE_PLACE_SERVICES + "("
                + KEY_SERVICE_ID + " INTEGER PRIMARY KEY," + KEY_SERVICE_NAME + " TEXT,"
                + KEY_SERVICE_PRICE + " DOUBLE, " + KEY_PLACE_ID + " INTEGER, "
                + " FOREIGN KEY ("+KEY_PLACE_ID+") REFERENCES "+TABLE_PLACES+"("+KEY_PLACE_ID+" ON DELETE CASCADE));";
        db.execSQL(CREATE_PLACE_SERVICES_TABLE);
        String CREATE_ACTIVITIES_TABLE= "CREATE TABLE " + TABLE_PLACE_ACTIVITIES + "("
                + KEY_ACTIVITY_ID + " INTEGER PRIMARY KEY," + KEY_ACTIVITY_NAME + " TEXT,"
                + KEY_ACTIVITY_PRICE + " DOUBLE, " + KEY_PLACE_ID + " INTEGER, "
                + " FOREIGN KEY ("+KEY_PLACE_ID+") REFERENCES "+TABLE_PLACES+"("+KEY_PLACE_ID+" ON DELETE CASCADE));";

        db.execSQL(CREATE_ACTIVITIES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);

        // Create tables again
        onCreate(db);
    }
    // Adding new place
    public void addPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        ContentValues serviceValues = new ContentValues();
        values.put(KEY_NAME, place.getPlaceName()); // Place Name
        values.put(KEY_TYPE, place.getPlaceType()); // Place Phone Number
        values.put(KEY_P_O_Is, place.getInterestingPoints().toString());
        values.put(KEY_CULTURAL_RULES, place.getCulturalRules().toString());
        values.put(KEY_RULES, place.getRules().toString());
        // ToDo Remove the toString method and replace
        // Inserting Row
        db.insert(TABLE_PLACES, null, values);
        db.close(); // Closing database connection
    }

    public void addService(PlaceServices service) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, service.getServiceName()); // Place Name
        values.put(KEY_SERVICE_PRICE, service.getServicePrice());
        // ToDo Remove the toString method and replace
        // Inserting Row
        db.insert(TABLE_PLACE_SERVICES, null, values);
        db.close(); // Closing database connection
    }
    public void addActivity(PlaceActivities activity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, activity.getActivityName()); // Place Name
        values.put(KEY_ACTIVITY_PRICE, activity.getActivityPrice());
        // ToDo Remove the toString method and replace
        // Inserting Row
        db.insert(TABLE_PLACE_ACTIVITIES, null, values);
        db.close(); // Closing database connection
    }
    // Getting single place
    public Place getPlace(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String[] { KEY_ID,
                        KEY_NAME, KEY_TYPE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Place place = new Place(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                cursor.getString(4),cursor.getString(5),cursor.getString(6));
        // return place
        return place;
    }
    // Getting single services
    public PlaceServices getService(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACE_SERVICES, new String[] { KEY_SERVICE_ID,
                        KEY_SERVICE_NAME, KEY_SERVICE_PRICE }, KEY_PLACE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PlaceServices service = new PlaceServices();
        service.setServiceName(cursor.getString(1));
        service.setServicePrice(Integer.parseInt(cursor.getString(2)));
        // return place
        return service;
    }

    // Getting single services
    public PlaceActivities getActivity(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACE_SERVICES, new String[] { KEY_ACTIVITY_ID,
                        KEY_SERVICE_NAME, KEY_ACTIVITY_PRICE }, KEY_PLACE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PlaceActivities activity = new PlaceActivities();
        activity.setActivityName(cursor.getString(1));
        activity.setActivityPrice(Double.parseDouble(cursor.getString(2)));
        // return place
        return activity;
    }

    // Getting All Places
    public List<Place> getAllPlaces() {
        List<Place> placeList = new ArrayList<Place>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLACES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setId(Integer.parseInt(cursor.getString(0)));
                place.setPlaceName(cursor.getString(1));
                place.setPlaceType(cursor.getString(2));
                // Adding place to list
                placeList.add(place);
            } while (cursor.moveToNext());
        }

        // return place list
        return placeList;
    }
    // Getting All Places
    public List<PlaceServices> getAllPlaceServices() {
        List<PlaceServices> placeServiceList = new ArrayList<PlaceServices>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLACE_SERVICES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PlaceServices service = new PlaceServices();
                service.setServiceName(cursor.getString(1));
                service.setServicePrice(Double.parseDouble(cursor.getString(2)));
                // Adding place to list
                placeServiceList.add(service);
            } while (cursor.moveToNext());
        }

        // return place list
        return placeServiceList;
    }

    // Getting All Places
    public List<PlaceActivities> getAllPlaceActivities(int id) {
        List<PlaceActivities> placeActivitiesList = new ArrayList<PlaceActivities>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLACE_ACTIVITIES + "WHERE "+ KEY_ID +"="+id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PlaceActivities activity = new PlaceActivities();
                activity.setActivityName(cursor.getString(1));
                activity.setActivityPrice(Double.parseDouble(cursor.getString(2)));
                // Adding place to list
                placeActivitiesList.add(activity);
            } while (cursor.moveToNext());
        }

        // return place list
        return placeActivitiesList;
    }

    // Getting places Count
    public int getPlacesCount() {
        // Getting places Count
            String countQuery = "SELECT  * FROM " + TABLE_PLACES;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
    }
    // Updating single place
    public int updatePlace(Place place) {
        // Updating single place
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, place.getPlaceName());
            values.put(KEY_TYPE, place.getPlaceType());

            // updating row
            return db.update(TABLE_PLACES, values, KEY_ID + " = ?",
                    new String[] { String.valueOf(place.getId()) });
}

    // Deleting single place
    public void deletePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLACES, KEY_ID + " = ?",
                new String[] { String.valueOf(place.getId()) });
        db.close();
    }

}
