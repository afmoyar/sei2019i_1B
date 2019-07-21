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

public class UpdatePlaceTask extends AsyncTask<Void, Void, ControlResult> {
    private Context context;
    private Place place;
    private String userId;
    private String comment;
    private String rating;
    private Marker marker;
    private ProgressDialog progress;
    private User user;
    private String place_latitude;
    private String place_longitude;

    public UpdatePlaceTask (Context context,User user, Place place, String comment, int rating) {

        this.context = context;
        this.progress = new ProgressDialog(context);
        this.place=place;
        this.user=user;
        this.userId = user.getId();
        this.comment = comment;
        this.rating = String.valueOf(rating);
        this.place_latitude = String.valueOf(place.getLatitude());
        this.place_longitude = String.valueOf(place.getLongitude());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Log.d(TAG,"onPreExecute: user id: "+userId+", lat: "+latitude+", long: "+longitude);
        progress.setMessage("updating place");
        progress.setIndeterminate(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        // AQUÍ NO FUNCIONA
        //progress.show();
    }

    @Override
    protected ControlResult doInBackground(Void... voids) {
        ControlResult result = ControlResult.CONNECT_ERROR;

        try {
            result = SeePlacesController.updateUserPlace(this.context, userId, comment, rating, place_latitude, place_longitude);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        return result;

    }

    @Override
    protected void onPostExecute(ControlResult result) {

        //AQUÍ TAMPOCO FUNCIONA
        //progress.dismiss();

        switch (result) {

            case CONNECT_ERROR:

                Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                break;


            case SERVER_ERROR:
                Toast.makeText(context, "Couldn't save comment, try again", Toast.LENGTH_LONG).show();
                break;

            case SUCCESS:

                Toast.makeText(context, "Comment and rating added", Toast.LENGTH_SHORT).show();
                user.deletePlace(place);
                place.setComment(comment);
                place.setRating(Integer.parseInt(rating));
                user.addPlace(place);
                break;
        }
    }
}
