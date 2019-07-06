package businessLogic.Controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import dataAccess.Repositories.UserRepository;
import presentation.Activities.MapActivity;
import presentation.Activities.WelcomeUserActivity;

public abstract class UserLoginController {


    public static void login(Context context, String id, String password){
        UserRepository.searchByIdAndPassword(context,id,password);
    }

    public static void insertUser(Context context, String id,String name, String password){
        UserRepository.createUser(context,id,name,password);
    }

    public static void changeToWelcomeUserActivity(Context context){
        Intent i = new Intent(context, WelcomeUserActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
