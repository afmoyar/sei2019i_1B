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

import businessLogic.Controllers.Controller;
import dataAccess.DataBase.Database;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST =9001;
    private final Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView myimageview = (ImageView) findViewById(R.id.imageView);
        myimageview.setImageResource(R.drawable.siteslogo);
        //botón para forzar apertura del mapa
        Button forceOpenMapBtn=(Button) findViewById(R.id.forceOpenMapBtn);
        forceOpenMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.changeToMapActivity(getApplicationContext());
            }
        });


        final TextView user_id = (TextView) findViewById(R.id.editTuserID);
        final TextView user_password = (TextView) findViewById(R.id.editTuserPass);

        Button btnLogin=(Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login test
                Controller.login(getApplicationContext(),user_id.getText().toString(),user_password.getText().toString());
            }
        });

        Button btnSignup=(Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });

        Button btnAdminSignup=(Button) findViewById(R.id.adminlogbtn);
        btnAdminSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(i);
            }
        });
    }

    public boolean isServicesOk()
    {
        Log.d(TAG,"isServicesOk: checking google services version");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if(available== ConnectionResult.SUCCESS)
        {
            //Everything is ok
            Log.d(TAG,"isServicesOk: Google Play Services is working");
            return true;

        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            //an error occurred but it can be resolved
            Log.d(TAG,"isServicesOk: an error occurred but we can fix it");
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();

        }
        else
        {
            Toast.makeText(this,"You can´t make map request",Toast.LENGTH_LONG).show();
        }
        return false;
    }

}
