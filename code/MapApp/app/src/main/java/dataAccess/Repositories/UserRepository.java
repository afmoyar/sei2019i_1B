package dataAccess.Repositories;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import dataAccess.DataBase.Database;
import dataAccess.ResponseType;
import dataAccess.SignalWrappers.UserLogInResult;

public abstract class UserRepository {

    private static final Database database = new Database();

    public static ResponseType createUser(Context context, String id, String name, String password){

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

    public static UserLogInResult getUser(final Context context, final String id, final String password) throws InterruptedException, ExecutionException, TimeoutException, JSONException {

        Gson gson = new Gson();
        JSONObject loginResult = database.queryUser(context, id, password);

        if(loginResult.length() == 1){

            throw new JSONException("error");
        }

        return gson.fromJson(loginResult.toString(), UserLogInResult.class);
    }
}
