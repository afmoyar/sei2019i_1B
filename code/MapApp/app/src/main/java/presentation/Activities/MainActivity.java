package presentation.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.HashMap;
import java.util.Map;

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

        final TextView user_id = (TextView) findViewById(R.id.editTuserID);
        final TextView user_password = (TextView) findViewById(R.id.editTuserPass);

        Button btnLogin=(Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login test
                loginFunction(getApplicationContext(),user_id.getText().toString(),user_password.getText().toString());
                //MapActivity
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

        Button btnAdminSignup=(Button) findViewById(R.id.adminlog);
        btnAdminSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(i);
            }
        });
    }

    public void  loginFunction (final Context context, final String id, final String password){

        //StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.URL_login),
                new Response.Listener<String>() {
                    //message when the connection works
                    @Override
                    public void onResponse(String response) {
                        //login for the user when data is correct
                        if(response.contains("1")){
                            Toast.makeText(context, "login was successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MapActivity.class);
                            startActivity(i);
                        }
                        //message when login was not successful
                        else{
                            Toast.makeText(context, "name or password incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            //message when the connection doesn't work
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error: failed to connect with the db", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            //HashMap with the data to insert into the database
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",id);
                params.put("password",password);
                return params;
            }
        };

        //request using the parameters previously written
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
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
