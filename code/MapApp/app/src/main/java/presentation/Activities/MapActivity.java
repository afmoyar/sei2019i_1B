package presentation.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements  OnMapReadyCallback {
    private static final String TAG = "MapActivity";

    private static final String  FINE_LOCATION=Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted=false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    private static final float  DEFAULT_ZOOM=4.3f;
    //provitional LatLong, later this data will come from data base
    private static double lat=4.0000000;
    private static double longitud=-72.0000000;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getLocationPermission();

    }

    private void geolocate(String searchString)
    {
        Geocoder geocoder= new Geocoder(MapActivity.this);
        List<Address> list=new ArrayList<>();
        try {

            list=geocoder.getFromLocationName(searchString,1);
        }catch(IOException e)
        {
            Toast.makeText(this,"geolocator is not working",Toast.LENGTH_LONG).show();
            Log.e(TAG,"geolocate: IOExeption "+e.getMessage());
        }
        if(list.size()>0)
        {
            Address address= list.get(0);
            Log.d(TAG,"geolocate: found a location"+address.toString());
            LatLng foundLatLng=new LatLng(address.getLatitude(),address.getLongitude());
            makeMarker(foundLatLng,address.getAddressLine(0));
            //Toast.makeText(this,address.toString(),Toast.LENGTH_LONG).show();
        }
    }
    private void makeMarker(LatLng latLng,String title)
    {
        MarkerOptions options=new MarkerOptions().position(latLng).title(title);
        mMap.addMarker(options);
    }
    private void moveCamera(LatLng latLng,float zoom)
    {

        Log.d(TAG,"moveCamera: moving the camera to lat: "+latLng.latitude+" , long: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }
    private void initMap()
    {
        Log.d(TAG,"initMap: initializing map");
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }
    private void getLocationPermission()
    {
        Log.d(TAG,"getLocationPermission: getting location permissions");
        String[] permissions={Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionGranted=true;
                initMap();
            }
            else
            {
                ActivityCompat.requestPermissions(this,permissions, LOCATION_PERMISSION_REQUEST_CODE);

            }
        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions, LOCATION_PERMISSION_REQUEST_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Log.d(TAG,"onRequestPermissionsResult: called");
        mLocationPermissionGranted=false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:
                if(grantResults.length>0)
                {
                    for(int i=0;i<grantResults.length;i++)
                    {
                        if(grantResults[i]!= PackageManager.PERMISSION_GRANTED)
                        {
                            mLocationPermissionGranted=false;
                            Log.d(TAG,"onRequestPermissionsResult: permition failed");
                            return;
                        }
                    }
                    Log.d(TAG,"onRequestPermissionsResult: permition granted");
                    mLocationPermissionGranted=true;
                    //initialize map
                    initMap();
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this,"map is ready", Toast.LENGTH_LONG).show();
        Log.d(TAG,"onMapReady: map is ready");
        mMap=googleMap;
        if(mLocationPermissionGranted)
        {
            moveCamera(new LatLng(lat,longitud),DEFAULT_ZOOM);
            geolocate("Bogota");
            geolocate("Medellin");
        }

    }
}
