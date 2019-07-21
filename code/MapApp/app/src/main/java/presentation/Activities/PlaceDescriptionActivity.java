package presentation.Activities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;


public class PlaceDescriptionActivity extends AppCompatActivity {


    private String place;
    private String placeDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_description);

        place = getIntent().getExtras().get("placeName").toString();
        placeDescription = getIntent().getExtras().get("placeDescription").toString();

        final TextView place_name = findViewById(R.id.placeName);
        final TextView place_description = findViewById(R.id.placeDescription);
        place_name.setText(place);
        place_description.setText(placeDescription);


        Button commentButton = findViewById(R.id.CommentButton);
        commentButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CommentActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
            }

        });
    }
}
