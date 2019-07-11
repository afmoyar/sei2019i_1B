package businessLogic.Controllers;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import dataAccess.Models.Place;
import dataAccess.Repositories.AdministratorRepository;
import dataAccess.Repositories.PlaceRepository;
import dataAccess.Repositories.UserRepository;
import presentation.Activities.SeePlacesActivity;
import presentation.Activities.WelcomeUserActivity;


public abstract class SeePlacesController {

    //ArrayList llamada en SeePlacesActivity
    public static ArrayList<String> arrayList;

    public static void getPlacesWithId(Context context, String id){
        PlaceRepository.findPlaceWithId(context,id);
    }

    public static void changeToSeePlacesActivity(Context context, ArrayList<String> places) {

        Intent i = new Intent(context, SeePlacesActivity.class);
        arrayList = places;
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    public static ControlResult insertUserPlace(Context context, String userId,String latitude, String longitude) throws InterruptedException {



        ControlResult result = null;

        switch (PlaceRepository.createUserPlace(context,userId,latitude,longitude)){

            case SUCCES:

                result = ControlResult.SUCCESS;
                break;

            case DB_ERROR:

                result = ControlResult.SERVER_ERROR;
                break;

            case CONNECT_ERROR:

                result = ControlResult.CONNECT_ERROR;
                break;
        }

        return result;
    }
}
