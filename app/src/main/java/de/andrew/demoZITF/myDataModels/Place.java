package de.andrew.demoZITF.myDataModels;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andrew on 4/10/16.
 */
public class Place {
    private List<PlaceServices> PlaceServices;
    private List<PlaceActivities> activities;
    private String[] culturalRules;
    private String[] rules;
    private int id;
    private String placeType, placeName;
    private int altitude;
    private HashMap<String, LatLng> interestingPoints;
    String dummy1, dummy2;
    public Place(){

    }

    public Place(int id, String name, String placeType, int altitude, String culturalRules, String rules, String pois){
        this.setId(id);
        this.setPlaceName(name);
        this.setPlaceType(placeType);
        this.altitude = altitude;
        dummy2 =culturalRules + rules;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<PlaceServices> getPlaceServices()
    {
        return this.PlaceServices;
    }

    public void setPlaceServices(List<PlaceServices> placeServices) {
        this.PlaceServices = placeServices;
    }

    public void setActivities(List<PlaceActivities> activities) {
        this.activities = activities;
    }
    public List<PlaceActivities> getActivities()
    {
        return this.activities;
    }
    public String[] getCulturalRules()
    {
        return this.culturalRules;
    }
    public void setCulturalRules(String[] culturalRules) {
        this.culturalRules = culturalRules;
    }

    public String[] getRules()
    {
        return this.rules;
    }

    public void setRules(String[] rules) {
        this.rules = rules;
    }

    public String getPlaceType()
    {
        return this.placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public int getAltitude()
    {
        return this.altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public HashMap<String,LatLng> getInterestingPoints()
    {
        return this.interestingPoints;
    }

    public void setInterestingPoints(HashMap<String, LatLng> interestingPoints) {
        this.interestingPoints = interestingPoints;
    }
}
