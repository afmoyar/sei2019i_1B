package presentation.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import businessLogic.ControlResult;
import businessLogic.Controllers.SeePlacesController;
import dataAccess.Models.Place;
import dataAccess.Models.User;

public class UpdatePlaceTask extends AsyncTask<Void, Void, ControlResult> {

    private Context context;
    private Place place;
    private String userId;
    private String comment;
    private String rating;
    private ProgressDialog progress;
    private User user;
    private String place_latitude;
    private String place_longitude;
    private int index;

    public UpdatePlaceTask (Context context,User user, Place place, String comment, int rating, int index, ProgressDialog progress) {

        this.context = context;
        this.progress = progress;
        this.place=place;
        this.user=user;
        this.userId = user.getId();
        this.comment = comment;
        this.rating = String.valueOf(rating);
        this.place_latitude = String.valueOf(place.getLatitude());
        this.place_longitude = String.valueOf(place.getLongitude());
        this.index = index;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        progress.setMessage("updating place");
        progress.setIndeterminate(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        progress.show();
    }

    @Override
    protected ControlResult doInBackground(Void... voids) {

        return SeePlacesController.updateUserPlace(this.context, userId, comment, rating, place_latitude, place_longitude);
    }

    @Override
    protected void onPostExecute(ControlResult result) {

        if ((progress != null) && progress.isShowing()) {
            progress.dismiss();
        }

        switch (result) {

            case CONNECT_ERROR:

                Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                break;


            case SERVER_ERROR:
                Toast.makeText(context, "Couldn't save comment, try again", Toast.LENGTH_LONG).show();
                break;

            case SUCCESS:

                Toast.makeText(context, "Comment and rating added", Toast.LENGTH_SHORT).show();
                place.setComment(comment);
                place.setRating(Integer.parseInt(rating));
                user.places.set(index, place);
                break;
        }
    }
}
