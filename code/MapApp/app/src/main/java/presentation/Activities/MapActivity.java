package presentation.Activities;

import android.Manifest;
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
import android.util.Pair;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.MapController;
import dataAccess.Models.User;
import dataAccess.Models.Place;


public class MapActivity extends AppCompatActivity implements  OnMapReadyCallback {
    private static final String TAG = "MapActivity";
    private static final float  DEFAULT_ZOOM=4.3f;
    //provitional LatLong for country, later this data will come from data base
    private static double lat=4.0000000;
    private static double longitud=-72.0000000;
    //provisional user,
    private User myUser=new User("afmoyar","andres","1234");
    ArrayList<Place> seasonPlacesList=new ArrayList<>();
    private ArrayList<Marker> markerArrayList=new ArrayList<>();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //provisional  places for the season
        Place bgta=new Place(4.71098599,-74.072092,"Bogota","I live here lol","Colombia");
        Place mdll=new Place(6.244203,-75.5812119,"Medellin","I  dont live here lmao","Colombia");
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

                            LatLng pos = m.getPosition();

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
