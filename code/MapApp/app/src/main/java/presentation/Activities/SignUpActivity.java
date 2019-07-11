package presentation.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.UserSignUpController;

public class SignUpActivity extends AppCompatActivity {

    class SignUpTask extends AsyncTask<Void, Void, ControlResult>{

        SignUpTask(Context context, String id, String name, String pass){

            this.context = context;
            this.progress = new ProgressDialog(SignUpActivity.this);
            this.id = id;
            this.name = name;
            this.pass = pass;
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
        protected ControlResult doInBackground(Void... voids) {

            ControlResult result = ControlResult.CONNECT_ERROR;

            try {

                result = UserSignUpController.insertUsr(this.context, id, name, pass);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(ControlResult result){

            progress.dismiss();

            switch (result) {

                case CONNECT_ERROR:

                    Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                    break;

                case INPUT_ERROR:

                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    break;

                case SERVER_ERROR:

                    Toast.makeText(context, "Couldn't create user. Change the id and try again", Toast.LENGTH_LONG).show();
                    break;

                case SUCCESS:

                    Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        private Context context;
        private String id;
        private String name;
        private String pass;
        private ProgressDialog progress;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextView user_id = findViewById(R.id.editTSignid);
        final TextView user_name = findViewById(R.id.editTSignname);
        final TextView user_password = findViewById(R.id.editTSignpass);

        final Context context = getApplicationContext();

        Button btnSignUp = findViewById(R.id.btnsighupF);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignUpTask signUp = new SignUpTask(context, user_id.getText().toString(),
                        user_name.getText().toString(), user_password.getText().toString());

                signUp.execute();

            }
        });

    }
}
