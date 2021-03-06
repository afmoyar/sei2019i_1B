package presentation.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mapapp.R;

import java.util.ArrayList;

import dataAccess.Models.Administrator;
import dataAccess.SignalWrappers.AdminLogInResult;

public class WelcomeAdminActivity extends AppCompatActivity {

    public static Administrator admin;
    public static ArrayList<String> other_countries;
    private final String resultKey = "adminResult";
    private static AdminLogInResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin);

        final TextView admin_id =  findViewById(R.id.adminid);
        final TextView admin_name = findViewById(R.id.adminName);

        final Button countries = findViewById(R.id.seecountries);
        final Button date = findViewById(R.id.changedate);

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            result = (AdminLogInResult) extras.get(resultKey);
            admin = result.admin;
            other_countries = result.countries;

            admin_id.setText(admin.getId());
            admin_name.setText(admin.getName());
        }

        countries.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SeeCountriesActivity.class);
                i.putExtra(resultKey, result);
                startActivityForResult(i, 1);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), AdminEventDateActivity.class);
                i.putExtra("date",admin.getLimitDate());
                startActivity(i);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            result = (AdminLogInResult) data.getExtras().get(resultKey);
            System.out.println();
        }
    }
}


