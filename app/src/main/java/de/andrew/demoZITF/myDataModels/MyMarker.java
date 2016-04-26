package de.andrew.demoZITF.myDataModels;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Andrew on 3/2/16.
 */
public class MyMarker {
    private String mLabel;
    private String mIcon;
    private Double mLatitude;
    private Double mLongitude;
    private LatLng latLng;

    public MyMarker(String label, String icon, Double latitude, Double longitude)
    {
        latLng = new LatLng(latitude,longitude);
        this.mLabel = label;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIcon = icon;
    }

    public String getmLabel()
    {
        return mLabel;
    }

    public void setmLabel(String mLabel)
    {
        this.mLabel = mLabel;
    }

    public String getmIcon()
    {
        return mIcon;
    }

    public void setmIcon(String icon)
    {
        this.mIcon = icon;
    }

    public Double getmLatitude()
    {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude()
    {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude)
    {
        this.mLongitude = mLongitude;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}