package de.andrew.demoZITF;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
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
import java.util.HashMap;

import de.andrew.demoZITF.dummy.DummyContent;
import de.andrew.demoZITF.ui.base.BaseActivity;

public class GetMap extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
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

    public double calculateSatisfaction(int ranking, double distance){
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

    private void setUpMap()
    {
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
        mMyMarkersArray.add(new MyMarker("Hostel 1", "icon1", Double.parseDouble("-17.837185"), Double.parseDouble("31.006663")));
        mMyMarkersArray.add(new MyMarker("Hostel 2", "icon2", Double.parseDouble("-17.8377"), Double.parseDouble("31.00673")));
        mMyMarkersArray.add(new MyMarker("Hostel 3", "icon3", Double.parseDouble("-17.838"), Double.parseDouble("31.00679")));
        mMyMarkersArray.add(new MyMarker("Hostel 4", "icon4", Double.parseDouble("-17.83833"), Double.parseDouble("31.00685")));
        mMyMarkersArray.add(new MyMarker("N-Block", "icon5", Double.parseDouble("-17.83869"), Double.parseDouble("31.00694")));
        mMyMarkersArray.add(new MyMarker("S-Block", "icon6", Double.parseDouble("-17.83905"), Double.parseDouble(" 31.00703")));
        mMyMarkersArray.add(new MyMarker("Library", "icon7", Double.parseDouble("-17.83943"), Double.parseDouble("31.00748")));
        mMyMarkersArray.add(new MyMarker("Car Park", "icondefault", Double.parseDouble("-17.83907"), Double.parseDouble("31.00916")));

        setUpMap();
        float zoomLevel = 18; //This goes up to 21
        LatLng latLng = new LatLng(-17.837185,31.006663);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        plotMarkers(mMyMarkersArray);
    }

    private void plotMarkers(ArrayList<MyMarker> markers)
    {
        if(markers.size() > 0)
        {
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


}
