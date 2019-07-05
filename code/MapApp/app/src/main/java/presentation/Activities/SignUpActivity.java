package presentation.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mapapp.R;

import businessLogic.Controllers.Controller;
import dataAccess.DataBase.Database;

public class SignUpActivity extends AppCompatActivity {

    private final Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextView user_id = (TextView) findViewById(R.id.editTSignid);
        final TextView user_name = (TextView) findViewById(R.id.editTSignname);
        final TextView user_password = (TextView) findViewById(R.id.editTSignpass);

        Button btnSignup=(Button) findViewById(R.id.btnsighupF);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.insertUser(getApplicationContext(),user_id.getText().toString(),user_name.getText().toString(),user_password.getText().toString());
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }
}
