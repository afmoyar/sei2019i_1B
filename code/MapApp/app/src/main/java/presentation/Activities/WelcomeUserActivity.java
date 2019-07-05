package presentation.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class WelcomeUserActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeUserActivity";
    private static final int ERROR_DIALOG_REQUEST =9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);

        TextView textViewUserId=(TextView) findViewById(R.id.textViewUserId);
        TextView textViewUserName=(TextView) findViewById(R.id.textViewUserName);
        TextView textViewCountry=(TextView) findViewById(R.id.textViewCountry);
        Button mapButton=(Button) findViewById(R.id.mapButton);
        if(isServicesOk())
        {
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i= new Intent(getApplicationContext(),MapActivity.class);
                    startActivity(i);
                }
            });
        }

    }
    public boolean isServicesOk()
    {
        Log.d(TAG,"isServicesOk: checking google services version");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(WelcomeUserActivity.this);
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
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(WelcomeUserActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();

        }
        else
        {
            Toast.makeText(this,"You can´t make map request",Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
