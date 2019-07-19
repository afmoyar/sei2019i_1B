package businessLogic.Controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import dataAccess.Models.User;
import dataAccess.Repositories.AdminLogInResult;
import dataAccess.Repositories.AdministratorRepository;
import dataAccess.Repositories.UserRepository;
import presentation.Activities.WelcomeAdminActivity;


public abstract class AdminLoginController {

    public static void login(Context context, String id, String password){
        AdministratorRepository.searchByIdAndPassword(context,id,password);
    }

    public static void changeToWelcomeAdminActivity(Context context, String id, String name){
        Intent i = new Intent(context, WelcomeAdminActivity.class);
        i.putExtra("id",id);
        i.putExtra("name",name);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    static public Pair<AdminLogInResult, ControlResult> logIn(Context context, String id, String password){


        if(id.equals("") || password.equals("")){

            return new Pair<>(null, ControlResult.INPUT_ERROR);
        }

        String[] invalidSymbols = {"'", "\"", ",", "{", "}", "[", "]"};

        for(String symbol : invalidSymbols){

            if(id.contains(symbol) || password.contains(symbol))
                return new Pair<>(null, ControlResult.INPUT_ERROR);
        }

        AdminLogInResult result = null;

        try{

            result = AdministratorRepository.getAdminAndCountries(context, id, password);
        }
        catch (InterruptedException| ExecutionException | TimeoutException e){

            e.printStackTrace();
            return new Pair<>(null, ControlResult.CONNECT_ERROR);
        }
        catch (JSONException e){

            e.printStackTrace();
            return new Pair<>(null, ControlResult.SERVER_ERROR);
        }

        return new Pair<>(result, ControlResult.SUCCESS);
    }
}
