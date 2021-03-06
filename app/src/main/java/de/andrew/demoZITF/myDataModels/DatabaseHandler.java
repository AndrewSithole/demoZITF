package de.andrew.demoZITF.myDataModels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String DATABASE_NAME = "NPlacesDb";

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
    // These are the names of the JSON objects that need to be extracted.
    final String LOC_ID = "ID";
    final String LOC_CONTENT = "post_content";
    final String LOC_ALTITUDE = "altitude";
    final String LOC_LATITUDE = "latitude";
    final String LOC_LONGITUDE = "longitude";
    final String LOC_CURRENCY = "currency";
    final String LOC_LANGUAGES = "languages";
    final String LOC_VISA_REQUIREMENTS = "visa_requirement";
    final String LOC_NIGHTLIFE = "location_nightlife";
    final String LOC_SPORTS = "location_sports_and_nature";
    final String LOC_TYPE = "place_type";
    final String LOC_TITLE = "post_title";
    final String LOC_IMG_URL = "featured_image_url";
    // FOR ACCOMMODATION
    final String ACCOMMODATION_TABLE = "Accommodation";
    final String ACC_ID = "ID";
    final String ACC_TITLE = "title";
    final String ACC_DESCRIPTION = "description";
    final String ACC_MIN_DAYS_STAY = "min_days";
    final String ACC_MAX_COUNT = "max_count";
    final String ACC_IS_PRICE_PER_PERSON = "per_person";
    final String ACC_STAR_COUNT = "star_count";
    final String ACC_LOCATION_ID = "location";
    final String ACC_ACTIVITIES = "activities";
    final String ACC_IMG = "imgUrl";

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
                + LOC_ID + " INTEGER PRIMARY KEY, " + LOC_TITLE + " TEXT,"
                + LOC_TYPE + " TEXT, " + LOC_ALTITUDE + " INTEGER, "
                + LOC_LATITUDE + " DOUBLE, " + LOC_LONGITUDE + " DOUBLE, "
                + LOC_LANGUAGES + " TEXT, " + LOC_NIGHTLIFE + " TEXT, "
                + LOC_VISA_REQUIREMENTS + " TEXT, " + LOC_CONTENT + " TEXT, "
                + LOC_SPORTS + " TEXT, " + LOC_IMG_URL + " TEXT, "
                + LOC_CURRENCY+ " TEXT" + ");";
        db.execSQL(CREATE_PLACES_TABLE);
        String CREATE_ACCOMMODATION_TABLE = "CREATE TABLE " + ACCOMMODATION_TABLE + "("
                + ACC_ID + " INTEGER PRIMARY KEY, " + ACC_TITLE + " TEXT,"
                + ACC_DESCRIPTION + " TEXT, " + ACC_MAX_COUNT + " TEXT, "
                + ACC_MIN_DAYS_STAY + " TEXT, " + ACC_STAR_COUNT + " TEXT, "
                + ACC_LOCATION_ID + " TEXT, " + ACC_IS_PRICE_PER_PERSON + " TEXT, "
                + ACC_IMG + " TEXT, " + ACC_ACTIVITIES + " TEXT " +  ");";
        db.execSQL(CREATE_ACCOMMODATION_TABLE);
//        String CREATE_PLACE_SERVICES_TABLE= "CREATE TABLE " + TABLE_PLACE_SERVICES + "("
//                + KEY_SERVICE_ID + " INTEGER PRIMARY KEY," + KEY_SERVICE_NAME + " TEXT,"
//                + KEY_SERVICE_PRICE + " DOUBLE, " + KEY_PLACE_ID + " INTEGER, "
//                + " FOREIGN KEY ("+KEY_PLACE_ID+") REFERENCES "+TABLE_PLACES+"("+KEY_PLACE_ID+" ON DELETE CASCADE));";
//        db.execSQL(CREATE_PLACE_SERVICES_TABLE);
//        String CREATE_ACTIVITIES_TABLE= "CREATE TABLE " + TABLE_PLACE_ACTIVITIES + "("
//                + KEY_ACTIVITY_ID + " INTEGER PRIMARY KEY," + KEY_ACTIVITY_NAME + " TEXT,"
//                + KEY_ACTIVITY_PRICE + " DOUBLE, " + KEY_PLACE_ID + " INTEGER, "
//                + " FOREIGN KEY ("+KEY_PLACE_ID+") REFERENCES "+TABLE_PLACES+"("+KEY_PLACE_ID+" ON DELETE CASCADE));";
//
//        db.execSQL(CREATE_ACTIVITIES_TABLE);
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
    public Boolean addAccommodation(Accommodation accommodation) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.query(ACCOMMODATION_TABLE, null, ACC_ID + " = ?", new String[]{String.valueOf(accommodation.getID())}, null, null, null, null);
        if (cur != null && cur.getCount()>0) {
            // duplicate found
            return false;
        }else {
            ContentValues values = new ContentValues();
            values.put(ACC_ID, accommodation.getID()); // Place Name
            values.put(ACC_TITLE, accommodation.getPostTitle()); // Place Phone Number
            values.put(ACC_DESCRIPTION, accommodation.getPostContent());
            values.put(ACC_MAX_COUNT, accommodation.getAccommodationMaxCount());
            values.put(ACC_MIN_DAYS_STAY, accommodation.getAccommodationMinDaysStay());
            values.put(ACC_LOCATION_ID, accommodation.getAccommodationLocationPostId());
            values.put(ACC_STAR_COUNT, accommodation.getAccommodationStarCount());
            values.put(ACC_ACTIVITIES, accommodation.getAccommodationActivities());
            values.put(ACC_IMG, accommodation.getImgUrl());
            // ToDo Remove the toString method and replace
            Log.e("DATABASE HANDLER ", "Now inserting items");
            // Inserting Row
            db.insert(ACCOMMODATION_TABLE, null, values);
            db.close(); // Closing database connection
            return true;
        }
    }
    // Adding new place
    public Boolean addPlace(Place place) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.query(TABLE_PLACES, null, LOC_ID + " = ?", new String[]{String.valueOf(place.getId())}, null, null, null, null);
        if (cur != null && cur.getCount()>0) {
            // duplicate found
            return false;
        }else {
            ContentValues values = new ContentValues();
            values.put(LOC_ID, place.getId()); // Place Name
            values.put(LOC_TITLE, place.getPlaceName()); // Place Phone Number
            values.put(LOC_CONTENT, place.getDescription());
            values.put(LOC_ALTITUDE, place.getAltitude());
            values.put(LOC_CURRENCY, place.getCurrencyUsed());
            values.put(LOC_LANGUAGES, place.getLanguages());
            values.put(LOC_LATITUDE, place.getLatitude());
            values.put(LOC_LONGITUDE, place.getLongitude());
            values.put(LOC_NIGHTLIFE, place.getNightlife());
            values.put(LOC_SPORTS, place.getSportsAndNature());
            values.put(LOC_TYPE, place.getPlaceType());
            values.put(LOC_VISA_REQUIREMENTS, place.getVisaequirements());
            values.put(LOC_IMG_URL, place.getImgURL());
            // ToDo Remove the toString method and replace
            Log.e("DATABASE HANDLER ", "Now inserting items");
            // Inserting Row
            db.insert(TABLE_PLACES, null, values);
            db.close(); // Closing database connection
            return true;
        }
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

        Cursor cursor = db.query(TABLE_PLACES, new String[]{LOC_ID,
                        LOC_TITLE, LOC_TYPE, LOC_ALTITUDE, LOC_LATITUDE, LOC_LONGITUDE, LOC_LANGUAGES,
                LOC_NIGHTLIFE, LOC_VISA_REQUIREMENTS, LOC_CONTENT, LOC_SPORTS, LOC_CURRENCY, LOC_IMG_URL}, LOC_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Place place = new Place();
        place.setId(Integer.parseInt(cursor.getString(0)));
        place.setPlaceName(cursor.getString(1));
        place.setPlaceType(cursor.getString(2));
        place.setAltitude(cursor.getInt(3));
        place.setLatitude(cursor.getInt(4));
        place.setLongitude(cursor.getInt(5));
        place.setLanguages(cursor.getString(6));
        place.setNightlife(cursor.getString(7));
        place.setVisaequirements(cursor.getString(8));
        place.setDescription(cursor.getString(9));
        place.setSportsAndNature(cursor.getString(10));
        place.setImgURL(cursor.getString(11));
        place.setCurrencyUsed(cursor.getString(12));
        // return place
        return place;
    }
    // Getting single place
    public Accommodation getAccommodatino(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String[]{ACC_ID,
                        ACC_TITLE, ACC_DESCRIPTION, ACC_LOCATION_ID, ACC_IS_PRICE_PER_PERSON, ACC_STAR_COUNT, ACC_MIN_DAYS_STAY,
                        ACC_MAX_COUNT, ACC_MIN_DAYS_STAY, ACC_LOCATION_ID}, ACC_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Accommodation accommodation = new Accommodation();
        accommodation.setID((cursor.getString(0)));
        accommodation.setPostTitle(cursor.getString(1));
        accommodation.setPostContent(cursor.getString(2));
        accommodation.setAccommodationMaxCount(cursor.getString(3));
        accommodation.setAccommodationMinDaysStay(cursor.getString(4));
        accommodation.setAccommodationLocationPostId(cursor.getString(5));
        accommodation.setAccommodationStarCount(cursor.getString(6));
        accommodation.setAccommodationActivities(cursor.getString(9));
        accommodation.setImgUrl(cursor.getString(8));
        // return accommodation
        return accommodation;
    }
//    // Getting single services
//    public PlaceServices getService(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_PLACE_SERVICES, new String[] { KEY_SERVICE_ID,
//                        KEY_SERVICE_NAME, KEY_SERVICE_PRICE }, KEY_PLACE_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        PlaceServices service = new PlaceServices();
//        service.setServiceName(cursor.getString(1));
//        service.setServicePrice(Integer.parseInt(cursor.getString(2)));
//        // return place
//        return service;
//    }
//
//    // Getting single services
//    public PlaceActivities getActivity(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_PLACE_SERVICES, new String[] { KEY_ACTIVITY_ID,
//                        KEY_SERVICE_NAME, KEY_ACTIVITY_PRICE }, KEY_PLACE_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        PlaceActivities activity = new PlaceActivities();
//        activity.setActivityName(cursor.getString(1));
//        activity.setActivityPrice(Double.parseDouble(cursor.getString(2)));
//        // return place
//        return activity;
//    }

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
                place.setAltitude(cursor.getInt(3));
                place.setLatitude(cursor.getInt(4));
                place.setLongitude(cursor.getInt(5));
                place.setLanguages(cursor.getString(6));
                place.setNightlife(cursor.getString(7));
                place.setVisaequirements(cursor.getString(8));
                place.setDescription(cursor.getString(9));
                place.setSportsAndNature(cursor.getString(10));
                place.setCurrencyUsed(cursor.getString(12));
                place.setImgURL(cursor.getString(11));
                // Adding place to list
                placeList.add(place);
            } while (cursor.moveToNext());
        }

        // return place list
        return placeList;
    }

    // Getting single place
    public List<Accommodation> getAccommodations(int id) {
        List<Accommodation> accommodationList = new ArrayList<Accommodation>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + ACCOMMODATION_TABLE +" WHERE " +ACC_LOCATION_ID + "=" +id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();
        do {
            Accommodation accommodation = new Accommodation();
            accommodation.setID((cursor.getString(0)));
            accommodation.setPostTitle(cursor.getString(1));
            accommodation.setPostContent(cursor.getString(2));
            accommodation.setAccommodationMaxCount(cursor.getString(3));
            accommodation.setAccommodationMinDaysStay(cursor.getString(4));
            accommodation.setAccommodationLocationPostId(cursor.getString(5));
            accommodation.setAccommodationStarCount(cursor.getString(6));
            accommodation.setAccommodationActivities(cursor.getString(9));
            accommodation.setImgUrl(cursor.getString(8));
            accommodationList.add(accommodation);
        } while (cursor.moveToNext());
        // return accommodation
        return accommodationList;
    }
    // Getting All Places
//    public List<PlaceServices> getAllPlaceServices(int id) {
//        List<PlaceServices> placeServiceList = new ArrayList<PlaceServices>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_PLACE_SERVICES +" WHERE " + KEY_ID + "="+id;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                PlaceServices service = new PlaceServices();
//                service.setServiceName(cursor.getString(1));
//                service.setServicePrice(Double.parseDouble(cursor.getString(2)));
//                // Adding place to list
//                placeServiceList.add(service);
//            } while (cursor.moveToNext());
//        }
//
//        // return place list
//        return placeServiceList;
//    }

    // Getting All Places
//    public List<PlaceActivities> getAllPlaceActivities(int id) {
//        List<PlaceActivities> placeActivitiesList = new ArrayList<PlaceActivities>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_PLACE_ACTIVITIES + " WHERE "+ KEY_ID +"="+id;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                PlaceActivities activity = new PlaceActivities();
//                activity.setActivityName(cursor.getString(1));
//                activity.setActivityPrice(Double.parseDouble(cursor.getString(2)));
//                // Adding place to list
//                placeActivitiesList.add(activity);
//            } while (cursor.moveToNext());
//        }
//
//        // return place list
//        return placeActivitiesList;
//    }

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
                    new String[]{String.valueOf(place.getId())});
}

    // Deleting single place
    public void deletePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLACES, KEY_ID + " = ?",
                new String[]{String.valueOf(place.getId())});
        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(TABLE_PLACES,null,null);
    }
}
