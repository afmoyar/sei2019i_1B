package presentation.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mapapp.R;


import java.util.ArrayList;

import businessLogic.Controllers.MapController;
import dataAccess.Models.User;
import dataAccess.SignalWrappers.UserLogInResult;
import presentation.AsyncTasks.GetSeasonPlacesTask;

public class WelcomeUserActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeUserActivity";
    private static final int ERROR_DIALOG_REQUEST =9001;
    private User user;
    private ArrayList<String> seasonCountries;
    private String limitDate;
    private final String resultKey = "result";
    private final String userKey = "user";
    private final String placesKey = "places";
    private final String limitDateKey = "limitDate";
    private final String seasonCountriesKey = "seasonCountries";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);


        final TextView textViewUserId = findViewById(R.id.textViewUserId);
        TextView textViewUserName = findViewById(R.id.textViewUserName);
        Button goToSeasonInfoBtn = findViewById(R.id.goToSeasonInfoBtn);
        Bundle extras = getIntent().getExtras();

        if(extras != null) {

            UserLogInResult result = (UserLogInResult) extras.get(resultKey);
            user = result.user;
            seasonCountries = result.seasonCountries == null ? new ArrayList<String>() : result.seasonCountries;
            limitDate = result.limitDate;
            textViewUserId.setText(user.getId());
            textViewUserName.setText(user.getName());
        }

        Button mapButton = findViewById(R.id.mapButton);
        Button placesButton = findViewById(R.id.placesButton);
        if(MapController.isServicesOk(this)) {
            mapButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    GetSeasonPlacesTask seasonPlacesTask = new GetSeasonPlacesTask(WelcomeUserActivity.this, getApplicationContext(), user, userKey, placesKey);
                    seasonPlacesTask.execute();
                }
            });
        }
        placesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SeePlacesActivity.class);
                i.putExtra(userKey, user);
                startActivityForResult(i, 2);
            }
        });

        goToSeasonInfoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                 Intent i=new Intent(getApplicationContext(), SeasonInfoForUserActivity.class);
                 i.putExtra(limitDateKey, limitDate);
                 i.putExtra(seasonCountriesKey, seasonCountries);
                 startActivity(i);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 || requestCode == 2){

            user = (User) data.getExtras().get(userKey);
        }
    }
    /*
    @Override
    public void onBackPressed(){
        //super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("are you sure you want to close your account?")
                .setTitle("Log out");

        builder.setPositiveButton("Log out", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }
    */
}
