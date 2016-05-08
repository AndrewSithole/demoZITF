package de.andrew.demoZITF.myDataModels;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Andrew on 5/8/16.
 */
public class DownloadAccommodations {

    private ArrayList<Accommodation> getDataFromJson(String accommodationJsonStr)
            throws JSONException {
        Log.e(LOG_TAG, "SUccessfully called Get data from JSON");
        // These are the names of the JSON objects that need to be extracted.
        final String ACC_ID = "ID";
        final String ACC_TITLE = "post_title";
        final String ACC_DESCRIPTION = "post_content";
        final String ACC_MIN_DAYS_STAY = "accommodation_min_days_stay";
        final String ACC_MAX_CHILD_COUNT = "accommodation_max_child_count";
        final String ACC_MAX_COUNT = "accommodation_max_count";
        final String ACC_IS_PRICE_PER_PERSON = "accommodation_is_price_per_person";
        final String ACC_STAR_COUNT = "accommodation_star_count";
        final String ACC_LOCATION_ID = "accommodation_location_post_id";
        final String ACC_ACTIVITIES = "accommodation_activities";

        JSONObject jsonResult = new JSONObject(accommodationJsonStr);
        JSONArray accommodationArray = jsonResult.getJSONArray("data");
        if (accommodationArray!=null){
            Log.e(LOG_TAG, accommodationArray.getString(1).toString());
        }else {
            Log.e(LOG_TAG, "Location Array is empty");
        }

        ArrayList<Accommodation> mResults = new ArrayList<Accommodation>();
        Accommodation[] resultStrs = new Accommodation[accommodationArray.length()];
        int m = accommodationArray.length();
//            Log.e(LOG_TAG, "The length of Result Array is: "+accommodationArray.length());
        for(int i = 0; i < m; i++) {
            // Get the JSON object representing the Location
            JSONObject singleAccommodation = accommodationArray.getJSONObject(i);
            Accommodation accommodation = new Accommodation();
            accommodation.setID(singleAccommodation.getString(ACC_ID));
            accommodation.setPostTitle(singleAccommodation.getString(ACC_TITLE));
            accommodation.setPostContent(singleAccommodation.getString(ACC_DESCRIPTION));
            accommodation.setAccommodationStarCount(singleAccommodation.getString(ACC_STAR_COUNT));
            accommodation.setAccommodationLocationPostId(singleAccommodation.getString(ACC_LOCATION_ID));
            accommodation.setAccommodationMaxChildCount(singleAccommodation.getString(ACC_MAX_CHILD_COUNT));
            accommodation.setAccommodationMaxCount(singleAccommodation.getString(ACC_MAX_COUNT));
            accommodation.setAccommodationMinDaysStay(ACC_MIN_DAYS_STAY);
            accommodation.setAccommodationActivities(singleAccommodation.getString(ACC_ACTIVITIES));
            accommodation.setAccommodationIsPricePerPerson(singleAccommodation.getString(ACC_IS_PRICE_PER_PERSON));
            mResults.add(accommodation);


            Log.e(LOG_TAG, "Title " + 1 + " = " + accommodation.getPostTitle());

        }
        Log.e(LOG_TAG, "Place entry 1 : " + mResults.get(1).getPostTitle().toString());
        return mResults;
    }
    String LOG_TAG = "ASYNCTASK";

    private class DownloadContentTask extends AsyncTask<Void, Void, ArrayList<Accommodation>> {

        protected ArrayList<Accommodation> doInBackground(Void... voidsv) {

            //android.os.Debug.waitForDebugger();
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String mLocationsJsonStr = null;
            Log.e(LOG_TAG, "Starting background thread");
            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at LOC's forecast API page, at
                // http://openweathermap.org/API#forecast
                String baseUrl = "http://10.0.2.2/mydemo/accommodation_api.php";
                URL url = new URL(baseUrl);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                mLocationsJsonStr = buffer.toString();
                Log.e(LOG_TAG, mLocationsJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                Log.e(LOG_TAG, "Calling the getData fromJSON method");
                return getDataFromJson(mLocationsJsonStr);
            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(ArrayList<Accommodation> result) {
            for (Accommodation a:result){
                Log.e(LOG_TAG,a.getPostTitle());
            }
//            myAdapter = new SimpleItemRecyclerViewAdapter(result);
//            mRecyclerView.setAdapter(myAdapter);
//            mSwipeRefreshLayout.post(new Runnable() {
//                @Override
//                public void run() {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            });

            Log.e(LOG_TAG, " Finished Executing ");



        }
    }

}
