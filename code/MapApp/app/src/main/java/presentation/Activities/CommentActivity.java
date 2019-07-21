package presentation.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        final TextView userComment = findViewById(R.id.userComment);
        user = (User) getIntent().getExtras().get("User");
        currentPlace = (Place) getIntent().getExtras().get("Place");

        Button saveButton = findViewById(R.id.saveComment);
        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                UpdatePlaceTask updatePlaceTask = new UpdatePlaceTask(CommentActivity.this,user,currentPlace,userComment.getText().toString(), 10);
                updatePlaceTask.execute();
            }

        });



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
