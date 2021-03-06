package barzeCombo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BarSelectActivity extends FragmentActivity implements RetainedFragment.OnFragmentInteractionListener, OnMapReadyCallback, OnInfoWindowClickListener {
    private static final double CAMERA_LNG = -76.9395892;
    private static final double CAMERA_LAT = 38.984923;

    // The Map Object
    private GoogleMap mMap;

    // URL for getting the earthquake
    // replace with your own user name

    @SuppressWarnings("unused")
    public static final String TAG = "BarSelectActivity";
    private RetainedFragment mRetainedFragment;
    private boolean mMapReady;
    private boolean mDataReady;
    List<DataModel> dataModels;
    private static CustomArrayAdapter adapter;
    // Set up UI and get earthquake data
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mapview);

        if (null != savedInstanceState) {
            mRetainedFragment = (RetainedFragment) getFragmentManager()
                    .findFragmentByTag(RetainedFragment.TAG);
            onDownloadfinished();
        } else {
            mRetainedFragment = new RetainedFragment();
            getFragmentManager().beginTransaction()
                    .add(mRetainedFragment, RetainedFragment.TAG)
                    .commit();
            mRetainedFragment.onButtonPressed();

        }

        final List<DataModel> entries = new ArrayList<DataModel>();
        //entries.add(new DataModel("Corner Stone Grill & Loft", 2.0, "$10", 38.980684, -76.937615, null));
        //entries.add(new DataModel("MilkBoy ArtHouse", 6.0, "$0", 38.981448, -76.938099, null));
        System.out.println("starting Data PULL");

        /*int numCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(numCores * 2, numCores *2,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());*/

        Database database = new Database();
        final Task<List<Bar>> taskBars = database.getAllBars();
        taskBars.addOnCompleteListener( new OnCompleteListener<List<Bar>>() {
            @Override
            public void onComplete(@NonNull Task<List<Bar>> taskBars) {
                if (taskBars.getResult() != null) {
                    List<Bar> bars = taskBars.getResult();
                    for (Bar bar : bars) {
                        String bar_name = bar.getName();
                        List<Float> location = bar.getLocation();
                        Float lat = location.get(0);
                        Float lng = location.get(1);
                        Log.i(TAG, "lat: " + bar.getWaitTime());
                        entries.add(new DataModel(bar_name, bar.getWaitTime(), bar.getLowCover(), lat, lng, null));
                        System.out.println("Loaded Data to entries");
                    }
                    System.out.println("taskBar Success");
                } else {
                    Exception exception = taskBars.getException();
                }
                dataModels = entries;
                if(dataModels != null) {
                    for (DataModel m : dataModels) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(m.getLat(), m.getLng()))
                                .title(String.valueOf(m.getName()))
                                .snippet("Wait: " + m.getWait() + " min.  Cover: " + m.getCover())
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker()));
                    }
                }


            }


        });

        System.out.println("Map does it's thing");
        // The GoogleMap instance underlying the GoogleMapFragment defined in main.xml
        MapFragment map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        map.getMapAsync(this);










    }

    public List<DataModel> getDataModels() {
        return dataModels;
    }

    // Called when data is downloaded
    public void onDownloadfinished() {
        mDataReady = true;
        if (mMapReady) {
            //placeMarkers();
            mDataReady = false;
        }

    }

    private void placeMarkers() {

        if(dataModels != null) {
            for (DataModel m : dataModels) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(m.getLat(), m.getLng()))
                        .title(String.valueOf(m.getName()))
                        .snippet("Wait: " + m.getWait() + " min.  Cover: " + m.getCover())
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(getMarkerColor(m.getWait()))));
            }
        }

        // Add a marker for every earthquake
        /*for (EarthQuakeRec rec : mRetainedFragment.getData()) {

            // Add a new marker for this earthquake
            mMap.addMarker(new MarkerOptions()

                    // Set the Marker's position
                    .position(new LatLng(rec.getLat(), rec.getLng()))

                    // Set the title of the Marker's information window
                    .title(String.valueOf(rec.getMagnitude()))

                    // Set the color for the Marker
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(getMarkerColor(rec
                                    .getMagnitude()))));

        }*/
    }


    // Called when Map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapReady = true;
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(CAMERA_LAT, CAMERA_LNG), 15));
        if (mDataReady) {
            //placeMarkers();
            mMapReady = false;
        }

        mMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        //TODO: Grab Marker or whatever to begin detail view of bar
        Intent startBarInfo = new Intent(BarSelectActivity.this,BarInfo.class);
        startBarInfo.putExtra("barClicked",marker.getTitle());
        startActivity(startBarInfo);
    }

    // Assign marker color
    private float getMarkerColor(double magnitude) {

        if (magnitude < 20.0) {
            magnitude = magnitude/20.0;
            magnitude = magnitude * 360;
        } else if (magnitude >= 20.0) {
            magnitude = 360;
        }
        return (float) ((magnitude));
    }
}
