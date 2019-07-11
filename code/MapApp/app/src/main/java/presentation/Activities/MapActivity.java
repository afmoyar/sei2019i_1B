package presentation.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mapapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.MapController;
import businessLogic.Controllers.SeePlacesController;
import dataAccess.Models.Place;
import dataAccess.Models.User;


public class MapActivity extends AppCompatActivity implements  OnMapReadyCallback {

    class savePlaceTask extends AsyncTask<Void, Void, ControlResult> {

        private Context context;
        private Place place;
        private String userId;
        private String latitude;
        private String longitude;
        private Marker marker;
        private ProgressDialog progress;

        savePlaceTask(Context context, Place place,Marker marker) {

            this.context = context;
            this.progress = new ProgressDialog(MapActivity.this);
            this.place=place;
            this.marker=marker;
            this.userId = myUser.getId();
            this.latitude = String.valueOf(place.getLatitude());
            this.longitude = String.valueOf(place.getLongitude());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG,"onPreExecute: user id: "+userId+", lat: "+latitude+", long: "+longitude);
            progress.setMessage("saving place");
            progress.setIndeterminate(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected ControlResult doInBackground(Void... voids) {
            ControlResult result = ControlResult.CONNECT_ERROR;

            try {

                result = SeePlacesController.insertUserPlace(this.context, userId, latitude, longitude);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(ControlResult result) {
            progress.dismiss();

            switch (result) {

                case CONNECT_ERROR:

                    Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                    break;


                case SERVER_ERROR:

                    Toast.makeText(context, "Couldn't save place. is it already saved?", Toast.LENGTH_LONG).show();
                    break;

                case SUCCESS:

                    Toast.makeText(context, "Place saved successfully", Toast.LENGTH_SHORT).show();
                    myUser.addPlace(place);
                    userPlacesByLocation.put(new LatLng(place.getLatitude(), place.getLongitude()), place);
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    break;
            }
        }
    }
    
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
        userPlacesByLocation = indexPlaces(myUser.places);
        mapPlacesByLocation  = indexPlaces(seasonPlacesList);
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

                    Toast.makeText(getApplicationContext(),"already saved",Toast.LENGTH_LONG).show();
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                }
                else{

                    Log.d(TAG,"onMapReady:about to execute asynk task");
                    MapActivity.savePlaceTask savePlace = new MapActivity.savePlaceTask(getApplicationContext(),
                                                                                        mapPlacesByLocation.get(position), marker);
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

    private HashMap<LatLng, Place> indexPlaces(ArrayList<Place> places){

        HashMap<LatLng, Place> result = new HashMap<>();

        for(Place place : places){

            result.put(new LatLng(place.getLatitude(), place.getLongitude()), place);
        }

        return result;
    }
}
