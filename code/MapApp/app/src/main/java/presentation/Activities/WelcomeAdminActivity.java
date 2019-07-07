package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.widget.Button;
=======
>>>>>>> ShowAdminandUsernames
import android.widget.TextView;

import com.example.mapapp.R;

public class WelcomeAdminActivity extends AppCompatActivity {

    private String id,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin);
<<<<<<< HEAD
        TextView placeToSearch = (TextView) findViewById(R.id.placeToSearch);
        Button btnSignup=(Button) findViewById(R.id.pointToLocate);
=======
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        TextView admin_id = (TextView) findViewById(R.id.textVadminid);
        TextView admin_name = (TextView) findViewById(R.id.textVadminname);
        admin_id.setText(id);
        admin_name.setText(name);
>>>>>>> ShowAdminandUsernames
    }
}
