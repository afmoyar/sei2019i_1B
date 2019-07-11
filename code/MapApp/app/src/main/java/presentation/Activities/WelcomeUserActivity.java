package presentation.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;


import businessLogic.Controllers.MapController;
import businessLogic.Controllers.SeePlacesController;
import dataAccess.Models.User;

public class WelcomeUserActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeUserActivity";
    private static final int ERROR_DIALOG_REQUEST =9001;
    private static User user;
    private String id,name;
    private final String userKey = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");

        final TextView textViewUserId = findViewById(R.id.textViewUserId);
        TextView textViewUserName = findViewById(R.id.textViewUserName);
        TextView textViewCountry = findViewById(R.id.textViewCountry);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {

            //Toast.makeText(getApplicationContext(),"user found",Toast.LENGTH_SHORT).show();
            user = (User) extras.get(userKey);
            textViewUserId.setText(user.getId());
            textViewUserName.setText(user.getName());
        }

        Button mapButton = findViewById(R.id.mapButton);
        Button placesButton = findViewById(R.id.placesButton);
        if(MapController.isServicesOk(this))
        {
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MapActivity.class);
                    i.putExtra("user",user);
                    startActivity(i);
                }
            });

            placesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeePlacesController.getPlacesWithId(getApplicationContext(),textViewUserId.getText().toString());
                }
            });
        }

    }
}
