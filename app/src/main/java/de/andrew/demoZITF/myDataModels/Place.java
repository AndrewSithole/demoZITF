package de.andrew.demoZITF.myDataModels;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andrew on 4/10/16.
 */
public class Place {
    private String currencyUsed;
    private String visaequirements;
    private String Description;
    private double latitude;
    private double longitude;
    private String languages;
    private String nightlife;

    private int id;
    private String placeType, placeName;
    private int altitude;
    private String sportsAndNature;

    public Place(){

    }

    public Place(int id, String name, String placeType, int altitude, String mCurrency, String mVisaInfo,
                 String mDescription, double mLatitude, double mLongitude, String mLanguages, String mNightLife, String mSports){
        this.setId(id);
        this.setPlaceName(name);
        this.setPlaceType(placeType);
        this.setAltitude(altitude);
        this.setCurrencyUsed(mCurrency);
        this.setVisaequirements(mVisaInfo);
        this.setDescription(mDescription);
        this.setLatitude(mLatitude);
        this.setLongitude(mLongitude);
        this.setLanguages(mLanguages);
        this.setNightlife(mNightLife);
        this.setSportsAndNature(mSports);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public void setCurrencyUsed(String currencyUsed) {
        this.currencyUsed = currencyUsed;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setNightlife(String nightlife) {
        this.nightlife = nightlife;
    }

    public void setVisaequirements(String visaequirements) {
        this.visaequirements = visaequirements;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCurrencyUsed() {
        return currencyUsed;
    }

    public String getDescription() {
        return Description;
    }

    public String getLanguages() {
        return languages;
    }

    public String getNightlife() {
        return nightlife;
    }

    public String getVisaequirements() {
        return visaequirements;
    }

    public int getId() {
        return id;
    }

    public int getAltitude() {
        return altitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setSportsAndNature(String sportsAndNature) {
        this.sportsAndNature = sportsAndNature;
    }

    public String getSportsAndNature() {
        return sportsAndNature;
    }
}
