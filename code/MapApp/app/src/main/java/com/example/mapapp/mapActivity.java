package com.example.mapapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class mapActivity extends AppCompatActivity implements  OnMapReadyCallback {
    private static final String TAG = "mapActivity";

    private static final String FINE_LOCATION=Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted=false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getLocationPermission();

    }

    private void initMap()
    {
        Log.d(TAG,"initMap: initializing map");
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapActivity.this);
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
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this,"map is ready", Toast.LENGTH_LONG).show();
        Log.d(TAG,"onMapReady: map is ready");
        mMap=googleMap;

    }
}
