package businessLogic.Controllers;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

import businessLogic.ControlResult;
import dataAccess.Models.Place;
import dataAccess.Repositories.PlaceRepository;


public abstract class SeePlacesController {

    private static final String TAG = "SeePlacesController";


    public static HashMap<LatLng, Place> indexPlaces(ArrayList<Place> places){

        HashMap<LatLng, Place> result = new HashMap<>();

        for(Place place : places){

            result.put(new LatLng(place.getLatitude(), place.getLongitude()), place);
        }

        return result;
    }

    public static ControlResult insertUserPlace(Context context, String userId, String latitude, String longitude) {
        Log.d(TAG,"insertUserPlace");



        ControlResult result = null;

        switch (PlaceRepository.createUserPlace(context,userId,latitude,longitude)){

            case SUCCES:

                result = ControlResult.SUCCESS;
                break;

            case DB_ERROR:

                result = ControlResult.SERVER_ERROR;
                break;

            case CONNECT_ERROR:

                result = ControlResult.CONNECT_ERROR;
                break;
        }

        return result;
    }

    public static ControlResult updateUserPlace(Context context, String userId,String comment, String rating, String place_latitude, String place_longitude) throws InterruptedException {
        Log.d(TAG,"updateUserPlace");



        ControlResult result = null;

        switch (PlaceRepository.updateUserPlace(context,userId,comment,rating, place_latitude, place_longitude)){

            case SUCCES:

                result = ControlResult.SUCCESS;
                break;

            case DB_ERROR:

                result = ControlResult.SERVER_ERROR;
                break;

            case CONNECT_ERROR:

                result = ControlResult.CONNECT_ERROR;
                break;
        }

        return result;
    }

    public static ControlResult deleteUserPlace(Context context, String userId,String latitude, String longitude) throws InterruptedException {
        Log.d(TAG,"deleteUserPlace");



        ControlResult result = null;

        switch (PlaceRepository.deleteUserPlace(context,userId,latitude,longitude)){

            case SUCCES:

                result = ControlResult.SUCCESS;
                break;

            case DB_ERROR:

                result = ControlResult.SERVER_ERROR;
                break;

            case CONNECT_ERROR:

                result = ControlResult.CONNECT_ERROR;
                break;
        }

        return result;
    }
}
