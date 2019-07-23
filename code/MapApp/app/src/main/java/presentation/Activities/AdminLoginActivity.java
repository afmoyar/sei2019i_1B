package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mapapp.R;

import presentation.AsyncTasks.AdminLogInTask;

public class AdminLoginActivity extends AppCompatActivity {

    private final String resultKey = "adminResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        final TextView admin_id = findViewById(R.id.editTadminuser);
        final TextView admin_password = findViewById(R.id.editTdminpass);

        Button btnLogin = findViewById(R.id.btnadminlogg);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AdminLogInTask logIn = new AdminLogInTask(AdminLoginActivity.this, getApplicationContext(),admin_id.getText().toString(),
                        admin_password.getText().toString(),resultKey);

                logIn.execute();

                cleanEntries(admin_id, admin_password);
            }
        });
    }

    public void cleanEntries(TextView user_id,TextView user_password)
    {
        user_password.setText("");
        user_id.findFocus();
    }
}
