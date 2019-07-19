package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapapp.BuildConfig;
import com.example.mapapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import businessLogic.Controllers.AdminLoginController;
import presentation.AsyncTasks.AdminLogInTask;

public class AdminLoginActivity extends AppCompatActivity {

    private final String resultKey = "adminResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        final TextView admin_id = (TextView) findViewById(R.id.editTadminuser);
        final TextView admin_password = (TextView) findViewById(R.id.editTdminpass);
        final TextView admin = (TextView) findViewById(R.id.test);
        Button btnLogin=(Button) findViewById(R.id.btnadminlogg);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdminLogInTask logIn = new AdminLogInTask(AdminLoginActivity.this, getApplicationContext(),admin_id.getText().toString(),
                        admin_password.getText().toString(),resultKey);

                logIn.execute();

                //AdminLoginController.login(getApplicationContext(),admin_id.getText().toString(),admin_password.getText().toString());
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
