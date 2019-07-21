package presentation.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mapapp.R;

import presentation.AsyncTasks.SignUpTask;

public class SignUpActivity extends AppCompatActivity {

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

                SignUpTask signUp = new SignUpTask(SignUpActivity.this, context, user_id.getText().toString(),
                        user_name.getText().toString(), user_password.getText().toString());

                signUp.execute();

            }
        });

    }
}
