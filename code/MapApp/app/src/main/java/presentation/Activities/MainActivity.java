package presentation.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mapapp.R;

import presentation.AsyncTasks.LogInTask;

public class MainActivity extends AppCompatActivity {

    private final String resultKey = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView myImageView = findViewById(R.id.imageView);
        myImageView.setImageResource(R.drawable.siteslogo);

        final TextView user_id = findViewById(R.id.editTuserID);
        final TextView user_password = findViewById(R.id.editTuserPass);


        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogInTask logIn = new LogInTask(MainActivity.this, getApplicationContext(),user_id.getText().toString(),
                                                user_password.getText().toString(),resultKey);

                logIn.execute();
                cleanEntries( user_id, user_password);
            }
        });

        Button btnSignUp = findViewById(R.id.btnSignup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
                cleanEntries( user_id, user_password);
            }
        });

        Button btnAdminSignUp = findViewById(R.id.adminlogbtn);
        btnAdminSignUp.setOnClickListener(new View.OnClickListener() {
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
