package dataAccess.Repositories;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import dataAccess.DataBase.Database;

public abstract class PlaceRepository {
    private static final Database database = new Database();
    private static final String TAG = "PlaceRepository";

    public static void findPlaceWithId(final Context context, final String id){
        database.findPlaceWithId(context, id);
    }

    public static ResponseType createUserPlace(Context context, String userId,String latitude, String longitude){
        Log.d(TAG,"createUserPlace");

        String  stringResponse;

        try {

            stringResponse = database.insertUserPlace(context, userId, latitude, longitude);
        }
        catch (TimeoutException |InterruptedException| ExecutionException e){

            System.out.println(e.getStackTrace());
            return ResponseType.CONNECT_ERROR;
        }

        return stringResponse.equals("") ? ResponseType.SUCCES : ResponseType.DB_ERROR;
    }
}
