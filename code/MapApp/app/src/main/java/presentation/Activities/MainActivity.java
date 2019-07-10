package presentation.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import businessLogic.Controllers.MapController;
import businessLogic.Controllers.UserLoginController;
import dataAccess.DataBase.Database;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeUserActivity";

    private final Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView myimageview = (ImageView) findViewById(R.id.imageView);
        myimageview.setImageResource(R.drawable.siteslogo);

        final TextView user_id = (TextView) findViewById(R.id.editTuserID);
        final TextView user_password = (TextView) findViewById(R.id.editTuserPass);

        Button btnLogin=(Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login test
                UserLoginController.login(getApplicationContext(),user_id.getText().toString(),user_password.getText().toString());
                cleanEntries( user_id, user_password);
            }
        });

        Button btnSignup=(Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
                cleanEntries( user_id, user_password);
            }
        });

        Button btnAdminSignup=(Button) findViewById(R.id.adminlogbtn);
        btnAdminSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(i);
                cleanEntries( user_id, user_password);

            }
        });
    }

    public void cleanEntries(TextView user_id,TextView user_password)
    {
        user_password.setText("");
        user_id.findFocus();
    }

}
