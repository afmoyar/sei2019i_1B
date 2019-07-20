package presentation.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;

import java.util.ArrayList;

import businessLogic.Controllers.AdminLoginController;
import dataAccess.Models.Administrator;
import dataAccess.Repositories.AdminLogInResult;

public class WelcomeAdminActivity extends AppCompatActivity {

    private String id,name;
    private Administrator admin;
    private ArrayList<String> other_countries;
    private final String resultKey = "adminResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin);

        TextView placeToSearch = (TextView) findViewById(R.id.placeToSearch);
        Button btnSignup=(Button) findViewById(R.id.pointToLocate);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");

        final TextView admin_id = (TextView) findViewById(R.id.adminid);
        final TextView admin_name = (TextView) findViewById(R.id.adminName);

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            AdminLogInResult result = (AdminLogInResult) extras.get(resultKey);
            admin = result.admin;
            other_countries = result.countries;

            admin_id.setText(admin.getId());
            admin_name.setText(admin.getName());
        }
    }
    /*
    @Override
    public void onBackPressed(){


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


