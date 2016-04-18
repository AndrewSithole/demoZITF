package de.andrew.demoZITF.ui.quote;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import de.andrew.demoZITF.R;
import de.andrew.demoZITF.dummy.DummyContent;
import de.andrew.demoZITF.myDataModels.DatabaseHandler;
import de.andrew.demoZITF.myDataModels.Place;
import de.andrew.demoZITF.sessions.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Shows a list of all available quotes.
 * <p/>
 * Created by Andreas Schrade on 14.12.2015.
 */
public class ArticleListFragment extends ListFragment {

    private Callback callback = dummyCallback;

    /**
     * A callback interface. Called whenever a item has been selected.
     */
    public interface Callback {
        void onItemSelected(int id);
    }

    /**
     * A dummy no-op implementation of the Callback interface. Only used when no active Activity is present.
     */
    private static final Callback dummyCallback = new Callback() {
        @Override
        public void onItemSelected(int id) {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new MyListAdapter());
        setHasOptionsMenu(true);
        DownloadContentTask downloadContentTask =new DownloadContentTask();
        downloadContentTask.execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // notify callback about the selected list item
        callback.onItemSelected(DummyContent.ITEMS.get(position).id);
    }

    /**
     * onAttach(Context) is not called on pre API 23 versions of Android.
     * onAttach(Activity) is deprecated but still necessary on older devices.
     */
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    /**
     * Deprecated on API 23 but still necessary for pre API 23 devices.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    /**
     * Called when the fragment attaches to the context
     */
    protected void onAttachToContext(Context context) {
        if (!(context instanceof Callback)) {
            throw new IllegalStateException("Activity must implement callback interface.");
        }

        callback = (Callback) context;
    }

    private class MyListAdapter extends BaseAdapter {
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<Place> places = db.getAllPlaces();
        @Override
        public int getCount() {

            return places.size();
        }

        @Override
        public Object getItem(int position) {
            return places.get(position);
        }

        @Override
        public long getItemId(int position) {
            return places.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_article, container, false);
            }

            final Place item = (Place) getItem(position);
            ((TextView) convertView.findViewById(R.id.article_title)).setText(item.getPlaceName());
            ((TextView) convertView.findViewById(R.id.article_subtitle)).setText(item.getDescription());
            final ImageView img = (ImageView) convertView.findViewById(R.id.thumbnail);
            Glide.with(getActivity()).load(R.drawable.p1).asBitmap().fitCenter().into(new BitmapImageViewTarget(img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    img.setImageDrawable(circularBitmapDrawable);
                }
            });

            return convertView;
        }
    }

    public ArticleListFragment() {
    }

    private ArrayList<Place>getDataFromJson(String locationsJsonStr)
            throws JSONException {
        Log.e(LOG_TAG, "SUccessfully called Get data from JSON");
        // These are the names of the JSON objects that need to be extracted.
        final String LOC_LIST = "Data";
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
        JSONObject jsonResult = new JSONObject(locationsJsonStr);
        JSONArray locationArray = jsonResult.getJSONArray("data");
        if (locationArray!=null){
            Log.e(LOG_TAG, locationArray.getString(1).toString());
        }else {
            Log.e(LOG_TAG, "Location Array is empty");
        }

        ArrayList<Place> mResults = new ArrayList<Place>();
        Place[] resultStrs = new Place[locationArray.length()];

//            Log.e(LOG_TAG, "The length of Result Array is: "+locationArray.length());
        for(int i = 0; i < locationArray.length(); i++) {
            // Get the JSON object representing the Location
            JSONObject singleLocation = locationArray.getJSONObject(i);
            Place place = new Place();
            place.setLanguages(singleLocation.getString(LOC_LANGUAGES));
            place.setVisaequirements(singleLocation.getString(LOC_VISA_REQUIREMENTS));
            place.setPlaceType(singleLocation.getString(LOC_TYPE));
            place.setNightlife(singleLocation.getString(LOC_NIGHTLIFE));
            place.setSportsAndNature(singleLocation.getString(LOC_SPORTS));
            place.setLatitude(singleLocation.getInt(LOC_LATITUDE));
            place.setLongitude(singleLocation.getInt(LOC_LONGITUDE));
            place.setCurrencyUsed(LOC_CURRENCY);
            place.setId(singleLocation.getInt(LOC_ID));
            place.setPlaceName(singleLocation.getString(LOC_TITLE));
            place.setDescription(singleLocation.getString(LOC_CONTENT));
            place.setAltitude(singleLocation.getInt(LOC_ALTITUDE));
            mResults.add(place);
            resultStrs[i] = place;


            Log.e(LOG_TAG, "Title " + 1 + " = " + place.getPlaceName());

        }

        for (Place s : resultStrs) {
            Log.e(LOG_TAG, "Place entry: " + s.getPlaceName().toString());
        }
        Log.e(LOG_TAG, "Place entry 1 : " + mResults.get(1).getPlaceName().toString());
        return mResults;
    }
    String LOG_TAG = "ASYNCTASK";

    private class DownloadContentTask extends AsyncTask<Void, Void, ArrayList<Place>> {

        protected ArrayList<Place> doInBackground(Void... voidsv) {
            android.os.Debug.waitForDebugger();
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
                String baseUrl = "http://uploads.zimstay.com/mydemo/api.php";
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

        protected void onPostExecute(ArrayList<Place> result) {
//            DatabaseHandler db = new DatabaseHandler(getActivity());
//            for(Place place: result) {
//                db.addPlace(place);
//            }

            Log.e(LOG_TAG, " Finished Executing ");



        }
    }
}
