package dataAccess.Repositories;

import android.content.Context;

import dataAccess.DataBase.Database;

public abstract class PlaceRepository {
    private static final Database database = new Database();

    public static void findPlaceWithId(final Context context, final String id){
        database.findPlaceWithId(context, id);
    }
}
