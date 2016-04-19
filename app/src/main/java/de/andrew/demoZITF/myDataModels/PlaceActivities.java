package de.andrew.demoZITF.myDataModels;

/**
 * Created by Andrew on 4/11/16.
 */
public class PlaceActivities extends Object {
    private String activityName;
    private double activityPrice;

    public PlaceActivities(){

    }
    public PlaceActivities(String name, double price){
        activityName =name;
        activityPrice =price;
    }
    public void setActivityName(String name) {
        this.activityName = name;
    }

    public void setActivityPrice(double price) {
        this.activityPrice = price;
    }

    public double getActivityPrice() {
        return activityPrice;
    }

    public String getActivityName() {
        return activityName;
    }
}
