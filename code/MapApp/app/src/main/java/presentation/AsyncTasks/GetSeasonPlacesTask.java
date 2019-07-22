package presentation.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import java.util.ArrayList;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.MapController;
import dataAccess.Models.Place;
import dataAccess.Models.User;
import presentation.Activities.MapActivity;

public class GetSeasonPlacesTask extends AsyncTask<Void, Void, Pair<ArrayList<Place>, ControlResult>> {

    private Context context;
    private User user;
    private String userKey;
    private String placesKey;
    private Activity activity;
    private ProgressDialog progress;


    public GetSeasonPlacesTask(Activity activity,Context context, User user, String userKey, String placesKey) {

        this.context = context;
        this.user = user;
        this.userKey = userKey;
        this.placesKey = placesKey;
        this.activity = activity;
        this.progress = new ProgressDialog(activity);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Log.d(TAG,"onPreExecute: user id: "+userId+", lat: "+latitude+", long: "+longitude);
        progress.setMessage("getting season places");
        progress.setIndeterminate(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();
    }
    @Override
    protected Pair<ArrayList<Place>, ControlResult> doInBackground(Void... voids) {

            return MapController.getSeasonPlaces(context);
    }

    @Override
    protected void onPostExecute(Pair<ArrayList<Place>, ControlResult> resultPair){

        progress.dismiss();
        if(resultPair.second == ControlResult.SUCCESS){

            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra(userKey, user);
            intent.putExtra(placesKey, resultPair.first);
            activity.startActivityForResult(intent, 1);
        }
        else
        {
            Toast.makeText(context,"Couldn't connect to DB. Try again later.",Toast.LENGTH_LONG).show();
            return;
        }
    }
}
