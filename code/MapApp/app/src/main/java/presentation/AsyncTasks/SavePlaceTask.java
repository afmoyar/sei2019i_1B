package presentation.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.SeePlacesController;
import dataAccess.Models.Place;
import dataAccess.Models.User;

public class SavePlaceTask extends AsyncTask<Void, Void, ControlResult> {

    private Context context;
    private Place place;
    private User user;
    private String userId;
    private String latitude;
    private String longitude;
    private Marker marker;
    private HashMap<LatLng, Place> userPlacesByLocation;
    private ProgressDialog progress;

    public SavePlaceTask(Context context, User user, Place place, Marker marker, HashMap<LatLng, Place> userPlacesByLocation) {

        this.context = context;
        this.progress = new ProgressDialog(context);
        this.user=user;
        this.place=place;
        this.marker=marker;
        this.userPlacesByLocation=userPlacesByLocation;
        this.userId = user.getId();
        this.latitude = String.valueOf(place.getLatitude());
        this.longitude = String.valueOf(place.getLongitude());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Log.d(TAG,"onPreExecute: user id: "+userId+", lat: "+latitude+", long: "+longitude);
        progress.setMessage("saving place");
        progress.setIndeterminate(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    protected ControlResult doInBackground(Void... voids) {

        return SeePlacesController.insertUserPlace(this.context, userId, latitude, longitude);
    }

    @Override
    protected void onPostExecute(ControlResult result) {
        progress.dismiss();

        switch (result) {

            case CONNECT_ERROR:

                Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                break;


            case SERVER_ERROR:

                Toast.makeText(context, "Couldn't save place. is it already saved?", Toast.LENGTH_LONG).show();
                break;

            case SUCCESS:

                Toast.makeText(context, "Place saved successfully", Toast.LENGTH_SHORT).show();
                user.addPlace(place);
                userPlacesByLocation.put(new LatLng(place.getLatitude(), place.getLongitude()), place);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                break;
        }
    }
}

