package presentation.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.mapapp.R;


import businessLogic.Controllers.MapController;
import businessLogic.Controllers.SeePlacesController;

public class WelcomeUserActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeUserActivity";
    private static final int ERROR_DIALOG_REQUEST =9001;
    private String id,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");

        TextView textViewUserId=(TextView) findViewById(R.id.textViewUserId);
        TextView textViewUserName=(TextView) findViewById(R.id.textViewUserName);
        TextView textViewCountry=(TextView) findViewById(R.id.textViewCountry);

        textViewUserId.setText(id);
        textViewUserName.setText(name);

        Button mapButton=(Button) findViewById(R.id.mapButton);
        Button placesButton=(Button) findViewById(R.id.placesButton);
        if(MapController.isServicesOk(this))
        {
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(i);
                }
            });

            placesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeePlacesController.getPlacesWithId(getApplicationContext(),id);
                }
            });
        }

    }
}
