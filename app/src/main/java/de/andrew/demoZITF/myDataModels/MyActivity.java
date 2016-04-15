package de.andrew.demoZITF.myDataModels;

/**
 * Created by Andrew on 4/11/16.
 */
public class MyActivity {
    String activityName;
    double activityPrice;

    public MyActivity(String name, double price){
        activityName =name;
        activityPrice =price;
    }
    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
