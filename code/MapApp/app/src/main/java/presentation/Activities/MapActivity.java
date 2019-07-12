package presentation.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mapapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

import businessLogic.Controllers.MapController;
import businessLogic.Controllers.SeePlacesController;
import dataAccess.Models.Place;
import dataAccess.Models.User;
import presentation.AsyncTasks.DeletePlaceTask;
import presentation.AsyncTasks.SavePlaceTask;


public class MapActivity extends AppCompatActivity implements  OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final float  DEFAULT_ZOOM=4.3f;
    //provitional LatLong for country, later this data will come from data base
    private static double lat=4.0000000;
    private static double longitud=-72.0000000;
    private User myUser;
    ArrayList<Place> seasonPlacesList=new ArrayList<>();
    private ArrayList<Marker> markerArrayList=new ArrayList<>();
    private GoogleMap mMap;
    private HashMap<LatLng, Place> userPlacesByLocation;
    private HashMap<LatLng, Place> mapPlacesByLocation;
    private final String userKey = "user";
    private final String placesKey = "places";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if(getIntent().getExtras()!=null)
        {
            myUser=(User) getIntent().getExtras().get(userKey);
            seasonPlacesList = (ArrayList<Place>) getIntent().getExtras().get(placesKey);
        }

        Log.d(TAG,"initMap: initializing map");
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
        userPlacesByLocation = SeePlacesController.indexPlaces(myUser.places);
        mapPlacesByLocation  = SeePlacesController.indexPlaces(seasonPlacesList);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG,"onMapReady: map is ready");
        mMap=googleMap;
        MapController.moveCamera(mMap,new LatLng(lat,longitud),DEFAULT_ZOOM);

        //putting markers on default demonstration places
        Marker marker;
        for(Place place : seasonPlacesList) {

            marker=MapController.makeMarker(mMap,new LatLng(place.getLatitude(),place.getLongitude()),
                    place.getName()+", "+place.getCountryName());

            if(myUser.getPlaces().contains(place)) {

                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }

            markerArrayList.add(marker);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();
                LatLng position = marker.getPosition();

                if(userPlacesByLocation.containsKey(position)){

                      DeletePlaceTask deletePlace = new DeletePlaceTask(MapActivity.this,myUser,
                            mapPlacesByLocation.get(position), marker, userPlacesByLocation);
                    deletePlace.execute();

                    //Toast.makeText(getApplicationContext(),"already saved",Toast.LENGTH_LONG).show();
                    //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                }
                else{

                    Log.d(TAG,"onMapReady:about to execute asynk task");
                    SavePlaceTask savePlace = new SavePlaceTask(MapActivity. this,myUser,
                            mapPlacesByLocation.get(position), marker,userPlacesByLocation);
                    savePlace.execute();
                }

                return true;
            }
        });

    }

    @Override
    public void onBackPressed(){

        Intent i = new Intent();
        i.putExtra(userKey ,myUser);
        setResult(RESULT_OK, i);
        super.onBackPressed();
    }


}
