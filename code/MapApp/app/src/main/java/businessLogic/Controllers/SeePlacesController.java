package businessLogic.Controllers;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import dataAccess.Models.Place;
import dataAccess.Repositories.AdministratorRepository;
import dataAccess.Repositories.PlaceRepository;
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
}
