package presentation.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.MapController;
import businessLogic.Controllers.UserLoginController;
import dataAccess.Models.User;

public class MainActivity extends AppCompatActivity {

    private final String resultKey = "user";

    class LogInTask extends AsyncTask<Void, Void, Pair<User, ControlResult>>{

        public LogInTask(Context context, String id, String password){

            this.context = context;
            this.id = id;
            this.password = password;
            this.progress = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected void onPreExecute(){

            super.onPreExecute();
            progress.setMessage("Querying DB");
            progress.setIndeterminate(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Pair<User, ControlResult> doInBackground(Void... voids) {

            return UserLoginController.logIn(context, id, password);
        }

        @Override
        protected void onPostExecute(Pair<User, ControlResult> result){

            progress.dismiss();

            if(result.second == ControlResult.SUCCESS) {

                Intent intent = new Intent(context, WelcomeUserActivity.class);
                intent.putExtra(resultKey, result.first);
                startActivity(intent);
            }

            switch (result.second){

                case INPUT_ERROR:

                    Toast.makeText(context, "Wrong formating", Toast.LENGTH_LONG).show();
                    break;

                case CONNECT_ERROR:

                    Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                    break;

                case SERVER_ERROR:

                    Toast.makeText(context, "User or password incorrect. Please try again", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        String id;
        String password;
        Context context;
        private ProgressDialog progress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView myimageview = findViewById(R.id.imageView);
        myimageview.setImageResource(R.drawable.siteslogo);

        final TextView user_id = findViewById(R.id.editTuserID);
        final TextView user_password = findViewById(R.id.editTuserPass);

        Button forceOpenMapBtn = findViewById(R.id.forceOpenMapBtn);
        if(MapController.isServicesOk(this)){
            forceOpenMapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserLoginController.changeToMapActivity(getApplicationContext());
                }
            });
        }

        Button btnLogin=(Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogInTask logIn = new LogInTask(getApplicationContext(),user_id.getText().toString(),
                                                user_password.getText().toString());

                logIn.execute();
                cleanEntries( user_id, user_password);
            }
        });

        Button btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
                cleanEntries( user_id, user_password);
            }
        });

        Button btnAdminSignup = findViewById(R.id.adminlogbtn);
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
        user_id.setText("");
        user_password.setText("");
        user_id.findFocus();
    }

}
