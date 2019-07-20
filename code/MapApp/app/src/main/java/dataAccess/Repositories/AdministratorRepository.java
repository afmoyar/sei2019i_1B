package dataAccess.Repositories;

import android.content.Context;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import dataAccess.DataBase.Database;
import dataAccess.Models.Administrator;
import dataAccess.Models.Place;
import dataAccess.Models.User;


public abstract class AdministratorRepository {

    private static final Database database = new Database();

    public static void searchByIdAndPassword(final Context context,final String id, final String password){
        database.AdminloginFunction(context, id, password);
    }

    public static AdminLogInResult getAdminAndCountries(final Context context,final String id, final String password) throws InterruptedException, ExecutionException, TimeoutException, JSONException {

        Gson gson = new Gson();
        JSONObject loginResult = database.queryAdmin(context, id, password);

        if(loginResult.length() == 1){

            System.out.println("real error heyyy ****************************");
            System.out.println(loginResult.toString());
            throw new JSONException("error");
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(loginResult.toString());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return gson.fromJson(loginResult.toString(), AdminLogInResult.class);
    }
}
