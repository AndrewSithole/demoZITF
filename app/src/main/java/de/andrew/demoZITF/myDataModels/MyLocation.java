package de.andrew.demoZITF.myDataModels;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew on 4/18/16.
 */
public class MyLocation {
    private String ID;
    private String postContent;
    private String postTitle;
    private String altitude;
    private String latitude;
    private String longitude;
    private Object currency;
    private String languages;
    private Object visaRequirement;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public MyLocation(){}

    /**
     *
     * @return
     * The ID
     */
    public String getID() {
        return ID;
    }

    /**
     *
     * @param ID
     * The ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     *
     * @return
     * The postContent
     */
    public String getPostContent() {
        return postContent;
    }

    /**
     *
     * @param postContent
     * The post_content
     */
    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    /**
     *
     * @return
     * The postTitle
     */
    public String getPostTitle() {
        return postTitle;
    }

    /**
     *
     * @param postTitle
     * The post_title
     */
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    /**
     *
     * @return
     * The altitude
     */
    public String getAltitude() {
        return altitude;
    }

    /**
     *
     * @param altitude
     * The altitude
     */
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @return
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The currency
     */
    public Object getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     * The currency
     */
    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     * The languages
     */
    public String getLanguages() {
        return languages;
    }

    /**
     *
     * @param languages
     * The languages
     */
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    /**
     *
     * @return
     * The visaRequirement
     */
    public Object getVisaRequirement() {
        return visaRequirement;
    }

    /**
     *
     * @param visaRequirement
     * The visa_requirement
     */
    public void setVisaRequirement(Object visaRequirement) {
        this.visaRequirement = visaRequirement;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

