package presentation.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;

import org.w3c.dom.Text;

import dataAccess.Models.Place;
import dataAccess.Models.User;
import presentation.AsyncTasks.UpdatePlaceTask;

public class CommentActivity extends AppCompatActivity {
    private User user;
    private Place currentPlace;
    private String placeName;
    private final String userKey = "user";
    private final String currentPlaceKey = "place";
    private final String placeNameKey = "placeName";
    private final String placeIndexKey = "placeIndex";
    private int index;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        final TextView userComment = findViewById(R.id.userComment);
        user = (User) getIntent().getExtras().get(userKey);
        currentPlace = (Place) getIntent().getExtras().get(currentPlaceKey);
        placeName = getIntent().getExtras().get(placeNameKey).toString();
        index = getIntent().getIntExtra(placeIndexKey, 0);

        if(progress == null){

            progress = new ProgressDialog(CommentActivity.this);
        }

        final RatingBar ratingBar = findViewById(R.id.ratingBar2);

        Button saveButton = findViewById(R.id.saveComment);
        saveButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                int rating = Math.round(ratingBar.getRating()*2);
                currentPlace.setRating(rating);
                currentPlace.setComment(userComment.getText().toString());
                UpdatePlaceTask updatePlaceTask = new UpdatePlaceTask(CommentActivity.this,user,currentPlace,userComment.getText().toString(), rating, index, progress);
                updatePlaceTask.execute();

                onBackPressed();
            }

        });



    }

    @Override
    public void onPause() {
        super.onPause();

        if ((progress != null) && progress.isShowing())
            progress.dismiss();
        progress = null;
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent();
        i.putExtra(userKey ,user);
        i.putExtra(currentPlaceKey, currentPlace);
        i.putExtra(placeNameKey, placeName);
        setResult(RESULT_OK, i);
        super.onBackPressed();
    }
}
/*
        final TextView place_name = findViewById(R.id.placeName);
        final TextView place_description = findViewById(R.id.placeDescription);
        place_name.setText(place);
        place_description.setText(placeDescription);

        Button commentButton = findViewById(R.id.CommentButton);
        commentButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                UpdatePlaceTask updatePlaceTask = new UpdatePlaceTask(getApplicationContext(),user,currentPlace,"comentario de prueba", 10);
                updatePlaceTask.execute();

                Intent i = new Intent(getApplicationContext(), CommentActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
            }

        });
 */
