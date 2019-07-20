package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mapapp.R;

import java.util.ArrayList;

import dataAccess.Models.Place;
import presentation.Adapters.AdminListAdapter;

public class SeeCountriesActivity extends AppCompatActivity {

    private ArrayList<String> other_countries;
    private ArrayList<String> admin_countries;
    private ListView listView;
    String[][] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_countries);
        other_countries = getIntent().getStringArrayListExtra("othercountries");
        admin_countries = getIntent().getStringArrayListExtra("admincountries");

        listView = findViewById(R.id.countrieslist);

        data = placestoString();


        listView.setAdapter(new AdminListAdapter(this,data));
    }

    private String[][] placestoString(){
        String[][] data;
        int x = 0;

        if(other_countries.size() >0 || admin_countries.size() >0) {

            data = new String[other_countries.size()+admin_countries.size()][2];
            for (int i = 0;i < other_countries.size(); i++) {
                data[i][0] = other_countries.get(i);
                data[i][1] = "OffSeason";
                x = i;
            }
            for (int i = 0;i < admin_countries.size(); i++) {
                data[i+x+1][0] = admin_countries.get(i);
                data[i+x+1][1] = "OnSeason";
            }

        }else{
            data = new String[1][2];
            data[0][0] = " ";
            data[0][1] = "nothing to show here";
        }
        return data;
    }
}
