package dataAccess.Repositories;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import dataAccess.AdminUpdatePayload;
import dataAccess.DataBase.Database;


public abstract class AdministratorRepository {

    private static final Database database = new Database();

    public static AdminLogInResult getAdminAndCountries(final Context context,final String id, final String password) throws InterruptedException, ExecutionException, TimeoutException, JSONException {

        Gson gson = new Gson();
        JSONObject loginResult = database.queryAdmin(context, id, password);

        if(loginResult.length() == 1){

            System.out.println(loginResult.toString());
            throw new JSONException("error");
        }

        System.out.println(loginResult.toString());
        return gson.fromJson(loginResult.toString(), AdminLogInResult.class);
    }

    public static ResponseType updateAdminCountries(Context context, AdminUpdatePayload payload){

        ResponseType response = ResponseType.CONNECT_ERROR;

        try{

            String stringResponse = database.updateAdminCountries(context, payload);
            response = stringResponse.equals("") ? ResponseType.SUCCES : ResponseType.DB_ERROR;
        }
        catch (TimeoutException e){}
        catch (InterruptedException | ExecutionException e){

            e.printStackTrace();
        }

        return  response;
    }
}
