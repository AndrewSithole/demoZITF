package de.andrew.demoZITF;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import de.andrew.demoZITF.myDataModels.DatabaseHandler;
import de.andrew.demoZITF.myDataModels.MyLocation;
import de.andrew.demoZITF.myDataModels.MyMarker;
import de.andrew.demoZITF.myDataModels.Place;
import de.andrew.demoZITF.ui.SettingsActivity;

public class GetMap extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;
    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    private ArrayList<MyMarker> orderedMarkersArray = new ArrayList<MyMarker>();
    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LatLng currentLatLong;
    private HashMap<MyMarker, Double> shortestDistHash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
// Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // We are now connected!
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                currentLatLong = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            }
        }catch (SecurityException s){
            s.printStackTrace();
        }

    }

    @Override
    public void onConnectionSuspended(int cause) {
        // We are not connected anymore!
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // We tried to connect but failed!
    }
    public double calculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
    public void getClosestPoint(List<MyMarker> myMarkers) {
        // returns an ArrayList<Markers> from your data source
        int minIndex = -1;
        double minDist = 1E38; // initialize with a huge value that will be overwritten
        int size = myMarkers.size();
        MyMarker currentMarker = new MyMarker("Current Position",null,currentLatLong.latitude,currentLatLong.longitude);
        double curDistance =0;
            for (int i = 0; i < size; i++) {
                myMarkers.remove(i);
                int mSize = myMarkers.size();
                for (MyMarker marker:myMarkers) {
                    curDistance = calculationByDistance(currentMarker.getLatLng(), marker.getLatLng());
                    if (curDistance < minDist) {
                        minDist = curDistance;  // update neares
                        minIndex = i;           // store index of nearest marker in minIndex
                    }
                }
                orderedMarkersArray.add(currentMarker);
                currentMarker = myMarkers.get(minIndex);
            }
            if (minIndex >= 0) {
                    // now nearest maker found:
                    MyMarker nearestMarker = myMarkers.get(minIndex);
                    shortestDistHash.put(nearestMarker,curDistance);
                    currentLatLong = new LatLng(nearestMarker.getmLatitude(),nearestMarker.getmLongitude());
                    myMarkers.remove(minIndex);
                Log.e("My Map","Size is now"+size);
                    // TODO do something with nearesr marker
            } else {
                    // list of markers was empty
            }

    }
    public int calculateSatisfaction(int ranking, double distance){
        return 0;
    }

    public void addLines(ArrayList<MyMarker> markers){


        for (int i=1; i<markers.size(); i++) {
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(markers.get(i-1).getmLatitude(), markers.get(i-1).getmLongitude()), new LatLng(markers.get(i).getmLatitude(), markers.get(i).getmLongitude()))
                    .width(10)
                    .color(Color.BLUE)
                    .geodesic(true));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarkers();
        /* Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.addMarker(new MarkerOptions()

        );*/
    }

    private void setUpMap() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null)
            {
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                {
                    @Override
                    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker)
                    {
                        marker.showInfoWindow();
                        return true;
                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(), "Unable to create Maps", Toast.LENGTH_SHORT).show();
        }
    }

    public void addMarkers(){
        // Initialize the HashMap for Markers and MyMarker object
        mMarkersHashMap = new HashMap<Marker, MyMarker>();
        DatabaseHandler db = new DatabaseHandler(GetMap.this);
        List<Place> places = db.getAllPlaces();

        for(Place place:places) {
            MyMarker marker = new MyMarker(place.getPlaceName(),"icon1",place.getLatitude(),place.getLongitude());
            mMyMarkersArray.add(marker);
            rankMarker(marker, place.getId());
        }
        setUpMap();
        float zoomLevel = 6; //This goes up to 21
        LatLng latLng = new LatLng(-17.837185,31.006663);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        plotMarkers(mMyMarkersArray);
    }

    public void rankMarker(MyMarker marker, int id){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String game = sharedPref.getString("game", "");
        String mountains = sharedPref.getString("mountains", "");
        String schools = sharedPref.getString("Schools", "");
        String rocks = sharedPref.getString("rocks", "");
        String trade = sharedPref.getString("trade", "");
        String water = sharedPref.getString("water", "");

        DatabaseHandler db = new DatabaseHandler(GetMap.this);
        Place place = db.getPlace(id);
        String placeType = place.getPlaceType();
        switch (placeType){
            case "Game Park":
                marker.setRanking(Integer.parseInt(game));
                break;
            case "University":
                marker.setRanking(Integer.parseInt(schools));
                break;
            case "Water Fall":
                marker.setRanking(Integer.parseInt(water));
                break;
            case "Mountain":
                marker.setRanking(Integer.parseInt(mountains));
                break;
            case "Rocks":
                marker.setRanking(Integer.parseInt(rocks));
                break;
            case "Trade and Business":
                marker.setRanking(Integer.parseInt(trade));
                break;
        }

    }

    private void plotMarkers(ArrayList<MyMarker> markers)
    {
        if(markers.size() > 0)
        {
            Collections.sort(markers, new Comparator<MyMarker>() {
                @Override
                public int compare(MyMarker p1, MyMarker p2) {
                    return  p2.getRanking() - p1.getRanking();
                }

            });

            int i=0;
            for (MyMarker myMarker : markers)
            {
                i++;
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));

                if(i==1){
                    markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_1));
                }else if (i==2){
                    markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_2));
                }else if (i==3){
                    markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_3));
                }else if (i==4){
                    markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_4));
                }else if (i==5){
                    markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_5));
                }else if (i==6){
                    markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_6));
                }else if (i==7){
                    markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_7));
                }
                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);



                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
                addLines(markers);
            }
        }
    }
    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {
            View v  = getLayoutInflater().inflate(R.layout.infowindow_layout, null);

            MyMarker myMarker = mMarkersHashMap.get(marker);

            RoundedImageView markerIcon = (RoundedImageView) v.findViewById(R.id.thumbnail);

            TextView markerLabel = (TextView)v.findViewById(R.id.article_title);


            markerIcon.setImageResource(manageMarkerIcon(myMarker.getmIcon()));

            markerLabel.setText(myMarker.getmLabel());

            return v;
        }
        private int manageMarkerIcon(String markerIcon)
        {

            if (markerIcon.equals("icon1"))
                return R.drawable.p1;
            else if(markerIcon.equals("icon2"))
                return R.drawable.p2;
            else if(markerIcon.equals("icon3"))
                return R.drawable.p3;
            else if(markerIcon.equals("icon4"))
                return R.drawable.p4;
            else if(markerIcon.equals("icon5"))
                return R.drawable.p5;
            else if(markerIcon.equals("icon6"))
                return R.mipmap.icon_6;
            else if(markerIcon.equals("icon7"))
                return R.mipmap.icon_7;
            else
                return R.drawable.location_icon;
        }
    }
    class DistanceFromMeComparator implements Comparator<MyMarker> {
        MyMarker me;

        public DistanceFromMeComparator(MyMarker me) {
            this.me = me;
        }

        private Double distanceFromMe(MyMarker p) {
            double theta = p.getmLatitude() - me.getmLongitude();
            double dist = Math.sin(deg2rad(p.getmLatitude())) * Math.sin(deg2rad(me.getmLatitude()))
                    + Math.cos(deg2rad(p.getmLatitude())) * Math.cos(deg2rad(me.getmLatitude()))
                    * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            return dist;
        }

        private double deg2rad(double deg) { return (deg * Math.PI / 180.0); }
        private double rad2deg(double rad) { return (rad * 180.0 / Math.PI); }

        @Override
        public int compare(MyMarker p1, MyMarker p2) {
            return distanceFromMe(p1).compareTo(distanceFromMe(p2));
        }
    }

}
