
package de.andrew.demoZITF.myDataModels;

import java.util.HashMap;
import java.util.Map;

public class Accommodation {

    private String ID;
    private String postContent;
    private String postTitle;
    private String accommodationMinDaysStay;
    private String accommodationMaxCount;
    private String accommodationMaxChildCount;
    private String accommodationIsPricePerPerson;
    private String accommodationStarCount;
    private String accommodationLocationPostId;
    private String accommodationActivities;
    private String imgUrl;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The ID
     */
    public String getID() {
        return ID;
    }

    /**
     * 
     * @param ID
     *     The ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * 
     * @return
     *     The postContent
     */
    public String getPostContent() {
        return postContent;
    }

    /**
     * 
     * @param postContent
     *     The post_content
     */
    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    /**
     * 
     * @return
     *     The postTitle
     */
    public String getPostTitle() {
        return postTitle;
    }

    /**
     * 
     * @param postTitle
     *     The post_title
     */
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    /**
     * 
     * @return
     *     The accommodationMinDaysStay
     */
    public String getAccommodationMinDaysStay() {
        return accommodationMinDaysStay;
    }

    /**
     * 
     * @param accommodationMinDaysStay
     *     The accommodation_min_days_stay
     */
    public void setAccommodationMinDaysStay(String accommodationMinDaysStay) {
        this.accommodationMinDaysStay = accommodationMinDaysStay;
    }

    /**
     * 
     * @return
     *     The accommodationMaxCount
     */
    public String getAccommodationMaxCount() {
        return accommodationMaxCount;
    }

    /**
     * 
     * @param accommodationMaxCount
     *     The accommodation_max_count
     */
    public void setAccommodationMaxCount(String accommodationMaxCount) {
        this.accommodationMaxCount = accommodationMaxCount;
    }

    /**
     * 
     * @return
     *     The accommodationMaxChildCount
     */
    public String getAccommodationMaxChildCount() {
        return accommodationMaxChildCount;
    }

    /**
     * 
     * @param accommodationMaxChildCount
     *     The accommodation_max_child_count
     */
    public void setAccommodationMaxChildCount(String accommodationMaxChildCount) {
        this.accommodationMaxChildCount = accommodationMaxChildCount;
    }

    /**
     * 
     * @return
     *     The accommodationIsPricePerPerson
     */
    public String getAccommodationIsPricePerPerson() {
        return accommodationIsPricePerPerson;
    }

    /**
     * 
     * @param accommodationIsPricePerPerson
     *     The accommodation_is_price_per_person
     */
    public void setAccommodationIsPricePerPerson(String accommodationIsPricePerPerson) {
        this.accommodationIsPricePerPerson = accommodationIsPricePerPerson;
    }

    /**
     * 
     * @return
     *     The accommodationStarCount
     */
    public String getAccommodationStarCount() {
        return accommodationStarCount;
    }

    /**
     * 
     * @param accommodationStarCount
     *     The accommodation_star_count
     */
    public void setAccommodationStarCount(String accommodationStarCount) {
        this.accommodationStarCount = accommodationStarCount;
    }

    /**
     * 
     * @return
     *     The accommodationLocationPostId
     */
    public String getAccommodationLocationPostId() {
        return accommodationLocationPostId;
    }

    /**
     * 
     * @param accommodationLocationPostId
     *     The accommodation_location_post_id
     */
    public void setAccommodationLocationPostId(String accommodationLocationPostId) {
        this.accommodationLocationPostId = accommodationLocationPostId;
    }

    /**
     * 
     * @return
     *     The accommodationActivities
     */
    public String getAccommodationActivities() {
        return accommodationActivities;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 
     * @param accommodationActivities
     *     The accommodation_latitude
     */
    public void setAccommodationActivities(String accommodationActivities) {
        this.accommodationActivities = accommodationActivities;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
