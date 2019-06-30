package presentation.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mapapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import dataAccess.DataBase.Database;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST =9001;
    private final Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isServicesOk())
        {
            init();
        }

    }
    private void init()
    {
        Button btnMap=(Button) findViewById(R.id.btnMapp);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Inserts user data into the external database
                database.insertUser(getApplicationContext(),"id3_here","señor","bigotes");


                //MapActivity
                Intent i = new Intent(getApplicationContext(), MapActivity.class);
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
