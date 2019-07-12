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

import businessLogic.Controllers.AsyncTaskController;
import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.MapController;
import businessLogic.Controllers.SeePlacesController;
import dataAccess.Models.Place;
import dataAccess.Models.User;


public class MapActivity extends AppCompatActivity implements  OnMapReadyCallback {

    class SavePlaceTask extends AsyncTask<Void, Void, ControlResult> {

        private Context context;
        private Place place;
        private User user;
        private String userId;
        private String latitude;
        private String longitude;
        private Marker marker;
        private HashMap<LatLng, Place> userPlacesByLocation;
        private ProgressDialog progress;

        SavePlaceTask(Context context, User user, Place place, Marker marker, HashMap<LatLng, Place> userPlacesByLocation) {

            this.context = context;
            this.progress = new ProgressDialog(context);
            this.user=user;
            this.place=place;
            this.marker=marker;
            this.userPlacesByLocation=userPlacesByLocation;
            this.userId = user.getId();
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
                    user.addPlace(place);
                    userPlacesByLocation.put(new LatLng(place.getLatitude(), place.getLongitude()), place);
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    break;
            }
        }
    }

    class DeletePlaceTask extends AsyncTask<Void, Void, ControlResult> {

        private Context context;
        private Place place;
        private String userId;
        private String latitude;
        private String longitude;
        private Marker marker;
        private ProgressDialog progress;
        private HashMap<LatLng, Place> userPlacesByLocation;
        private User user;

        DeletePlaceTask(Context context,User user, Place place,Marker marker,HashMap<LatLng, Place> userPlacesByLocation) {

            this.context = context;
            this.progress = new ProgressDialog(context);
            this.place=place;
            this.user=user;
            this.marker=marker;
            this.userId = user.getId();
            this.userPlacesByLocation=userPlacesByLocation;
            this.latitude = String.valueOf(place.getLatitude());
            this.longitude = String.valueOf(place.getLongitude());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG,"onPreExecute: user id: "+userId+", lat: "+latitude+", long: "+longitude);
            progress.setMessage("deleting place");
            progress.setIndeterminate(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected ControlResult doInBackground(Void... voids) {
            ControlResult result = ControlResult.CONNECT_ERROR;

            try {
                result = SeePlacesController.deleteUserPlace(this.context, userId, latitude, longitude);

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
                    Toast.makeText(context, "Couldn't delete place,try again", Toast.LENGTH_LONG).show();
                    break;

                case SUCCESS:

                    Toast.makeText(context, "Place deleted ,tap again to undo", Toast.LENGTH_SHORT).show();
                    user.deletePlace(place);
                    userPlacesByLocation.remove(new LatLng(place.getLatitude(), place.getLongitude()));
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
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

                      //MapActivity.DeletePlaceTask deletePlace = new MapActivity.DeletePlaceTask(MapActivity.this,myUser,
                        //    mapPlacesByLocation.get(position), marker, userPlacesByLocation);
                    AsyncTaskController.DeletePlaceTask deletePlace=new AsyncTaskController().new DeletePlaceTask(MapActivity.this,myUser,
                                mapPlacesByLocation.get(position), marker, userPlacesByLocation);
                    deletePlace.execute();

                    //Toast.makeText(getApplicationContext(),"already saved",Toast.LENGTH_LONG).show();
                    //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                }
                else{

                    Log.d(TAG,"onMapReady:about to execute asynk task");
                    //MapActivity.SavePlaceTask savePlace = new SavePlaceTask(MapActivity. this,myUser,
                      //      mapPlacesByLocation.get(position), marker,userPlacesByLocation);
                    AsyncTaskController.SavePlaceTask savePlace=new AsyncTaskController().new SavePlaceTask(MapActivity. this,myUser,
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

    private HashMap<LatLng, Place> indexPlaces(ArrayList<Place> places){

        HashMap<LatLng, Place> result = new HashMap<>();

        for(Place place : places){

            result.put(new LatLng(place.getLatitude(), place.getLongitude()), place);
        }

        return result;
    }
}
