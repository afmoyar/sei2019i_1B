package businessLogic.Controllers;

import android.content.Context;
import android.content.Intent;

import dataAccess.Repositories.AdministratorRepository;
import presentation.Activities.WelcomeAdminActivity;


public abstract class AdminLoginController {

    public static void login(Context context, String id, String password){
        AdministratorRepository.searchByIdAndPassword(context,id,password);
    }

    public static void changeToWelcomeAdminActivity(Context context){
        Intent i = new Intent(context, WelcomeAdminActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
