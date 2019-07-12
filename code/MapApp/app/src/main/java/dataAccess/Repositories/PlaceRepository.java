package dataAccess.Repositories;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import dataAccess.DataBase.Database;
import dataAccess.Models.Place;

public abstract class PlaceRepository {
    private static final Database database = new Database();
    private static final String TAG = "PlaceRepository";

    public static ResponseType createUserPlace(Context context, String userId,String latitude, String longitude) {
        Log.d(TAG, "createUserPlace");

        String stringResponse;

        try {

            stringResponse = database.insertUserPlace(context, userId, latitude, longitude);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {

            System.out.println(e.getStackTrace());
            return ResponseType.CONNECT_ERROR;
        }

        return stringResponse.equals("") ? ResponseType.SUCCES : ResponseType.DB_ERROR;
    }

    public static ArrayList<Place> getPlace(Context context) throws InterruptedException, ExecutionException, TimeoutException {

        Gson gson = new Gson();
        JSONArray jsonArray = database.queryCurrentSeasonPlaces(context);
        return gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Place>>(){}.getType());
    }
}
