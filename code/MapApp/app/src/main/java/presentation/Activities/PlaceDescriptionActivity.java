package presentation.Activities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;


public class PlaceDescriptionActivity extends AppCompatActivity {


    private String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_description);

        //place = getIntent().getExtras().get("placeName").toString();
        //Toast.makeText(PlaceDescriptionActivity.this, place, Toast.LENGTH_SHORT).show();
        //final TextView place_name = findViewById(R.id.placeName);
        //place_name.setText(place);
        Toast.makeText(PlaceDescriptionActivity.this, "Hola", Toast.LENGTH_SHORT).show();
     }

}
