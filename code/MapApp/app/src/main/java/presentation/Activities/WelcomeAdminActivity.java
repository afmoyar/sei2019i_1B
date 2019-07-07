package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.mapapp.R;

public class WelcomeAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin);
        TextView placeToSearch = (TextView) findViewById(R.id.placeToSearch);
        Button btnSignup=(Button) findViewById(R.id.pointToLocate);
    }
}
