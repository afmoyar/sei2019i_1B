package presentation.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Pair;
import android.widget.Toast;

import java.util.ArrayList;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.MapController;
import dataAccess.Models.Place;
import dataAccess.Models.User;
import presentation.Activities.MapActivity;
import presentation.Activities.WelcomeUserActivity;

public class GetSeasonPlacesTask extends AsyncTask<Void, Void, Pair<ArrayList<Place>, ControlResult>> {

    Context context;
    User user;
    String userKey;
    String placesKey;
    Activity activity;

    public GetSeasonPlacesTask(Activity activity,Context context, User user, String userKey, String placesKey) {

        this.context = context;
        this.user = user;
        this.userKey = userKey;
        this.placesKey = placesKey;
        this.activity = activity;
    }

    @Override
    protected Pair<ArrayList<Place>, ControlResult> doInBackground(Void... voids) {

        return MapController.getSeasonPlaces(context);
    }

    @Override
    protected void onPostExecute(Pair<ArrayList<Place>, ControlResult> resultPair){

        if(resultPair.second == ControlResult.CONNECT_ERROR){

            Toast.makeText(context,"Couldn't connesct to DB. Try again later.",Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(userKey, user);
        intent.putExtra(placesKey, resultPair.first);
        activity.startActivityForResult(intent, 1);
    }


}
