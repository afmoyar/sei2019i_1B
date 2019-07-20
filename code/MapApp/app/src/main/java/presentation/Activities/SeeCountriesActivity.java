package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mapapp.R;

import java.util.ArrayList;

import presentation.Adapters.AdminListAdapter;

public class SeeCountriesActivity extends AppCompatActivity {

    private ArrayList<String> other_countries;
    private ArrayList<String> admin_countries;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_countries);
        other_countries = getIntent().getStringArrayListExtra("othercountries");
        admin_countries = getIntent().getStringArrayListExtra("admincountries");

        listView = findViewById(R.id.countrieslist);


        listView.setAdapter(new AdminListAdapter(this,other_countries));


    }
}
