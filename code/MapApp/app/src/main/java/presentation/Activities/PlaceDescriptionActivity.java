package presentation.Activities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;

import dataAccess.Models.Place;
import dataAccess.Models.User;
import presentation.AsyncTasks.UpdatePlaceTask;


public class PlaceDescriptionActivity extends AppCompatActivity {


    private String place;
    private String placeDescription;
    private User user;
    private Place currentPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_description);

        place = getIntent().getExtras().get("placeName").toString();
        placeDescription = getIntent().getExtras().get("placeDescription").toString();
        user=(User) getIntent().getExtras().get("user");
        currentPlace=(Place) getIntent().getExtras().get("place");

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
    }
}
