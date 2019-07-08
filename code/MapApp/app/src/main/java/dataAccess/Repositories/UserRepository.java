package dataAccess.Repositories;

import android.content.Context;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import dataAccess.DataBase.Database;

public abstract class UserRepository {
    private static final Database database = new Database();

    public static void searchByIdAndPassword(final Context context,final String id, final String password){
        database.UserloginFunction(context, id, password);
    }

    public static ResponseType createUser(Context context, String id,String name, String password){

        String  stringResponse;

        try {

            stringResponse = database.insertUser(context, id, name, password);
        }
        catch (TimeoutException|InterruptedException|ExecutionException e){

            System.out.println(e.getStackTrace());
            return ResponseType.CONNECT_ERROR;
        }

        return stringResponse.equals("") ? ResponseType.SUCCES : ResponseType.DB_ERROR;
    }
}
