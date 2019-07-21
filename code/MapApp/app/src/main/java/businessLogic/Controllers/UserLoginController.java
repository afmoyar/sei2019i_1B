package businessLogic.Controllers;

import android.content.Context;
import android.util.Pair;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import dataAccess.Models.User;
import dataAccess.Repositories.UserRepository;

public abstract class UserLoginController {

    static public Pair<User, ControlResult> logIn(Context context, String id, String password){


        if(id.equals("") || password.equals("")){

            return new Pair<>(null, ControlResult.INPUT_ERROR);
        }

        String[] invalidSymbols = {"'", "\"", ",", "{", "}", "[", "]"};

        for(String symbol : invalidSymbols){

            if(id.contains(symbol) || password.contains(symbol))
                return new Pair<>(null, ControlResult.INPUT_ERROR);
        }

        User user = null;

        try{

            user = UserRepository.getUser(context, id, password);
        }
        catch (InterruptedException| ExecutionException | TimeoutException e){

            e.printStackTrace();
            return new Pair<>(null, ControlResult.CONNECT_ERROR);
        }
        catch (JSONException e){

            e.printStackTrace();
            return new Pair<>(null, ControlResult.SERVER_ERROR);
        }

        return new Pair<>(user, ControlResult.SUCCESS);
    }
}
