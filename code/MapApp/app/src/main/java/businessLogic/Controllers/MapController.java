package businessLogic.Controllers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import businessLogic.ControlResult;
import dataAccess.Models.Place;
import dataAccess.Repositories.PlaceRepository;

public abstract class MapController
{
    private static final String TAG = "MapController";

    private static final int ERROR_DIALOG_REQUEST =9001;

    public static boolean isServicesOk(Activity activity)
    {
        Log.d(TAG,"isServicesOk: checking google services version");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);
        if(available== ConnectionResult.SUCCESS)
        {
            //Everything is ok
            Log.d(TAG,"isServicesOk: Google Play Services is working");
            return true;

        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            //an error occurred but it can be resolved
            Log.d(TAG,"isServicesOk: an error occurred but we can fix it");
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(activity,available,ERROR_DIALOG_REQUEST);
            dialog.show();

        }
        else
        {
            Toast.makeText(activity,"You can´t make map request",Toast.LENGTH_LONG).show();
        }
        return false;
    }
    public static void moveCamera(GoogleMap mMap,LatLng latLng,float zoom)
    {

        Log.d(TAG,"moveCamera: moving the camera to lat: "+latLng.latitude+" , long: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

    public static Marker makeMarker(GoogleMap mMap, LatLng latLng, String title)
    {
        MarkerOptions options=new MarkerOptions().position(latLng).title(title);
        return mMap.addMarker(options);
    }

    public static Pair<ArrayList<Place>, ControlResult> getSeasonPlaces(Context context){

        ControlResult result = ControlResult.CONNECT_ERROR;
        ArrayList<Place> places = null;

        try {
            places = PlaceRepository.getPlace(context);
            result=ControlResult.SUCCESS;
        }
        catch (InterruptedException| ExecutionException e){

            e.printStackTrace();
        }
        catch(TimeoutException te){

            return  new Pair<>(places, ControlResult.CONNECT_ERROR);
        }

        return  new Pair<>(places, result);
    }
}
