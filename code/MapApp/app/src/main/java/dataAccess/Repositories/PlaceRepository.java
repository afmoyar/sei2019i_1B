package dataAccess.Repositories;

import android.content.Context;

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

    public static void findPlaceWithId(final Context context, final String id){
        database.findPlaceWithId(context, id);
    }

    public static ArrayList<Place> getPlace(Context context) throws InterruptedException, ExecutionException, TimeoutException {

        Gson gson = new Gson();
        JSONArray jsonArray = database.queryCurrentSeasonPlaces(context);
        return gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Place>>(){}.getType());
    }
}
