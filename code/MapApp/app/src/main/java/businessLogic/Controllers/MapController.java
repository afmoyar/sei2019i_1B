package businessLogic.Controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import presentation.Activities.MapActivity;

public abstract class MapController
{
    private static final String TAG = "MapController";

    private static final String  FINE_LOCATION= Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted=false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    private static final float  DEFAULT_ZOOM=4.3f;



    public static void moveCamera(GoogleMap mMap,LatLng latLng,float zoom)
    {

        Log.d(TAG,"moveCamera: moving the camera to lat: "+latLng.latitude+" , long: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }
    public static void geolocate(Context context,String searchString,GoogleMap mMap)
    {
        Geocoder geocoder= new Geocoder(context);
        List<Address> list=new ArrayList<>();
        try {

            list=geocoder.getFromLocationName(searchString,1);
        }catch(IOException e)
        {
            //Toast.makeText(this,"geolocator is not working",Toast.LENGTH_LONG).show();
            Log.e(TAG,"geolocate: IOExeption "+e.getMessage());
        }
        if(list.size()>0)
        {
            Address address= list.get(0);
            Log.d(TAG,"geolocate: found a location"+address.toString());
            LatLng foundLatLng=new LatLng(address.getLatitude(),address.getLongitude());
            makeMarker(mMap,foundLatLng,address.getAddressLine(0));
            //Toast.makeText(this,address.toString(),Toast.LENGTH_LONG).show();
        }
    }
    public static void makeMarker(GoogleMap mMap,LatLng latLng,String title)
    {
        MarkerOptions options=new MarkerOptions().position(latLng).title(title);
        mMap.addMarker(options);
    }
    private boolean getLocationPermission(Context context, Activity activity)
    {
        Log.d(TAG,"getLocationPermission: getting location permissions");
        String[] permissions={Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(context,FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(context,COURSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionGranted=true;
            }
            else
            {
                ActivityCompat.requestPermissions(activity,permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(activity,permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
        return mLocationPermissionGranted;
    }
    
}
