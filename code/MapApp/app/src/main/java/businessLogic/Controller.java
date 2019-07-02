package businessLogic;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import dataAccess.Repositories.UserRepository;
import presentation.Activities.MapActivity;

public abstract class Controller {


    public static void login(Context context, String id, String password){
        UserRepository.searchByIdAndPassword(context,id,password);
    }

    public static void insertUser(Context context, String id,String name, String password){
        UserRepository.createUser(context,id,name,password);
    }

    public static void changeToMapActivity(Context context){
        Intent i = new Intent(context, MapActivity.class);
        context.startActivity(i);
    }
}
