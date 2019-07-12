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

import businessLogic.Controllers.AdminLoginController;

public class WelcomeAdminActivity extends AppCompatActivity {

    private String id,name;

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
        admin_id.setText(id);
        admin_name.setText(name);
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


