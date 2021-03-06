package de.andrew.demoZITF.sessions;

/**
 * Created by Andrew on 4/6/16.
 */
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.facebook.login.LoginManager;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String HAS_DOWNLOADED = "hasDownloaded";

    private static final String SELECTED_PLACE = "selectedPlace";
    private static final String KEY_CURRENT_PLACE = "current_place";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String KEY_FACEBOOK_ID = "fb_id";

    public static final String KEY_FACEBOOK_PIC = "pic_link";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Selected Place
     * */
    public void setSelectedPlace(int name){
        // Storing login value as TRUE
        editor.putBoolean(SELECTED_PLACE, true);

        // Storing name in pref
        editor.putInt(KEY_CURRENT_PLACE, name);

        // commit changes
        editor.commit();
    }

    public int getSelectedPlace() {

        return pref.getInt(KEY_CURRENT_PLACE, 0);
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }
    /**
     * Create login session
     * */
    public void createFacebookSession(String name, String email, String id){

        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing email in pref
        editor.putString(KEY_FACEBOOK_ID, id);

        // Storing email in pref
//        editor.putString(KEY_FACEBOOK_PIC, picLink);

        // commit changes
        editor.commit();
    }
    public void createDownloadSession(){
        // Storing login value as TRUE
        editor.putBoolean(HAS_DOWNLOADED, true);
        // commit changes
        editor.commit();
    }

    public void deleteDownloadSession(){
        // Storing login value as TRUE
        editor.putBoolean(HAS_DOWNLOADED, false);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    public boolean checkDownloadedData(){
        boolean status;
        // Check login status
        if(!this.hasDownloaded()){
            // user has not downloaded data
            status = false;
        }else {
            status=true;
        }
        return status;

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_FACEBOOK_ID, pref.getString(KEY_FACEBOOK_ID,null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        LoginManager.getInstance().logOut();
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public boolean hasDownloaded(){
        return pref.getBoolean(HAS_DOWNLOADED, false);
    }

    public boolean selectedPlace(){
        return pref.getBoolean(SELECTED_PLACE, false);
    }


}