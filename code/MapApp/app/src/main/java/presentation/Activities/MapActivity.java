package presentation.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mapapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.MapController;
import businessLogic.Controllers.SeePlacesController;
import businessLogic.Controllers.UserSignUpController;
import dataAccess.Models.Place;
import dataAccess.Models.User;

public class MapActivity extends AppCompatActivity implements  OnMapReadyCallback {

    class SignUpTask extends AsyncTask<Void, Void, ControlResult> {

        private Context context;
        private String userId;
        private String latitude;
        private String longitude;
        private ProgressDialog progress;

        SignUpTask(Context context, String userId, String latitude, String longitude) {

            this.context = context;
            this.progress = new ProgressDialog(MapActivity.this);
            this.userId = userId;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage("Querying DB");
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
                    break;
            }
        }
    }
    private static final String TAG = "MapActivity";
    private static final float  DEFAULT_ZOOM=4.3f;
    //provitional LatLong for country, later this data will come from data base
    private static double lat=4.0000000;
    private static double longitud=-72.0000000;
    //provisional user,
    //private User myUser=new User("afmoyar","andres","1234");
    private User myUser;
    ArrayList<Place> seasonPlacesList=new ArrayList<>();
    private ArrayList<Marker> markerArrayList=new ArrayList<>();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if(getIntent().getExtras()!=null)
        {
            myUser=(User) getIntent().getExtras().get("user");
        }

        //provisional  places for the season
        Place bgta=new Place(4.71,-74.07,"Bogota","I live here lol","Colombia");
        Place mdll=new Place(6.24,-75.58,"Medellin","I  dont live here lmao","Colombia");
        seasonPlacesList.add(bgta);
        seasonPlacesList.add(mdll);
        Log.d(TAG,"initMap: initializing map");
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"onMapReady: map is ready");
        mMap=googleMap;
        MapController.moveCamera(mMap,new LatLng(lat,longitud),DEFAULT_ZOOM);
        //putting markers on default demostration places
        Marker marker;
        for(Place place:seasonPlacesList)
        {
            marker=MapController.makeMarker(mMap,new LatLng(place.getLatitude(),place.getLongitude()),
                    place.getName()+", "+place.getCountryName());
            if(myUser.getPlaces().contains(place))
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            markerArrayList.add(marker);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int i=0;
                for(Marker m:markerArrayList)
                {
                    if(marker.equals(m))
                    {
                        m.showInfoWindow();
                        if(myUser.getPlaces().contains(seasonPlacesList.get(i)))
                            Toast.makeText(getApplicationContext(),"already saved",Toast.LENGTH_LONG).show();
                        else
                        {
                            myUser.addPlace(seasonPlacesList.get(i));
                            Toast.makeText(getApplicationContext(),m.getTitle()+" saved",Toast.LENGTH_LONG).show();
                            m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        }

                        break;
                    }
                    i++;
                }
                return true;
            }
        });

        //address can be null if google doesn´t find any place or if the geolocate service is no available
    }
}
