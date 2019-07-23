package presentation.Activities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mapapp.R;

import dataAccess.Models.Place;
import dataAccess.Models.User;


public class PlaceDescriptionActivity extends AppCompatActivity {


    private String place;
    private User user;
    private Place currentPlace;
    private final String userKey = "user";
    private final String currentPlaceKey = "place";
    private final String placeNameKey = "placeName";
    private final String placeIndexKey = "placeIndex";
    private RatingBar ratingBar;
    private TextView user_comment;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_description);

        place = getIntent().getExtras().get(placeNameKey).toString();
        user=(User) getIntent().getExtras().get(userKey);
        currentPlace=(Place) getIntent().getExtras().get(currentPlaceKey);
        index = getIntent().getIntExtra(placeIndexKey, -1);

        Button commentButton = findViewById(R.id.CommentButton);
        final TextView place_name = findViewById(R.id.placeName);
        final TextView place_description = findViewById(R.id.placeDescription);
        user_comment = findViewById(R.id.commentId);


        place_name.setText(place);
        place_description.setText(currentPlace.getDescription());

        //rating Bar
        ratingBar= findViewById(R.id.userRatingBar);
        ratingBar.setProgress(currentPlace.getRating());

        if(currentPlace.getComment() != null) {
            user_comment.setText(currentPlace.getComment());
            commentButton.setText("EDIT COMMENT");
        }

        commentButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), CommentActivity.class);
                i.putExtra(currentPlaceKey, currentPlace);
                i.putExtra(userKey, user);
                i.putExtra(placeNameKey, place);
                i.putExtra(placeIndexKey, index);
                startActivityForResult(i, 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){

            user = (User) data.getExtras().get(userKey);
            currentPlace = (Place) data.getExtras().get(currentPlaceKey);
            place = data.getStringExtra(placeNameKey);
            //ratingBar= findViewById(R.id.userRatingBar);
            ratingBar.setProgress(currentPlace.getRating());

            if(currentPlace.getComment() != null) {
                user_comment.setText(currentPlace.getComment());
            }
        }
    }

    @Override
    public void onBackPressed(){

        Intent i = new Intent();
        user.places.set(index, currentPlace);
        i.putExtra(userKey ,user);
        setResult(RESULT_OK, i);
        super.onBackPressed();
    }
}

