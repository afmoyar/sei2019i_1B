package presentation.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

import businessLogic.ControlResult;
import businessLogic.Controllers.SeePlacesController;
import dataAccess.Models.Place;
import dataAccess.Models.User;

public class DeletePlaceTask extends AsyncTask<Void, Void, ControlResult>{

        private Context context;
        private Place place;
        private String userId;
        private String latitude;
        private String longitude;
        private Marker marker;
        private ProgressDialog progress;
        private HashMap<LatLng, Place> userPlacesByLocation;
        private User user;

        public DeletePlaceTask(Context context,User user, Place place,Marker marker,HashMap<LatLng, Place> userPlacesByLocation) {

            this.context = context;
            this.progress = new ProgressDialog(context);
            this.place=place;
            this.user=user;
            this.marker=marker;
            this.userId = user.getId();
            this.userPlacesByLocation=userPlacesByLocation;
            this.latitude = String.valueOf(place.getLatitude());
            this.longitude = String.valueOf(place.getLongitude());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Log.d(TAG,"onPreExecute: user id: "+userId+", lat: "+latitude+", long: "+longitude);
            progress.setMessage("deleting place");
            progress.setIndeterminate(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected ControlResult doInBackground(Void... voids) {
            ControlResult result = ControlResult.CONNECT_ERROR;

            try {
                result = SeePlacesController.deleteUserPlace(this.context, userId, latitude, longitude);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(ControlResult result) {
            progress.dismiss();

            switch (result) {

                case CONNECT_ERROR:

                    Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                    break;


                case SERVER_ERROR:
                    Toast.makeText(context, "Couldn't delete place,try again", Toast.LENGTH_LONG).show();
                    break;

                case SUCCESS:

                    Toast.makeText(context, "Place deleted ,tap again to undo", Toast.LENGTH_SHORT).show();
                    user.deletePlace(place);
                    userPlacesByLocation.remove(new LatLng(place.getLatitude(), place.getLongitude()));
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    break;
            }
        }
    }

