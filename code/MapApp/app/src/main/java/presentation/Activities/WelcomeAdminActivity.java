package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mapapp.R;

import businessLogic.Controllers.AdminLoginController;

public class WelcomeAdminActivity extends AppCompatActivity {

    private String id,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin);

        TextView placeToSearch = (TextView) findViewById(R.id.placeToSearch);
        Button btnSignup=(Button) findViewById(R.id.pointToLocate);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        final TextView admin_id = (TextView) findViewById(R.id.adminid);
        final TextView admin_name = (TextView) findViewById(R.id.adminName);
        admin_id.setText(id);
        admin_name.setText(name);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminLoginController.login(getApplicationContext(),admin_id.getText().toString(),admin_name.getText().toString());
            }
        });
    }
}
