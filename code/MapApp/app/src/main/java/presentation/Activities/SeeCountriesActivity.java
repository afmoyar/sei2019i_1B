package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateView(position);
            }
        });
    }

    private void updateView(int index){
        View v = listView.getChildAt(index - listView.getFirstVisiblePosition());

        if(v == null)
            return;

        TextView someText = (TextView) v.findViewById(R.id.season);
        TextView someText2 = (TextView) v.findViewById(R.id.contryname);
        if(someText.getText().toString() == "OffSeason"){
            someText.setText("OnSeason");
            WelcomeAdminActivity.other_countries.remove(WelcomeAdminActivity.other_countries.indexOf(someText2.getText().toString()));
            admin_countries.add(someText2.getText().toString());
            WelcomeAdminActivity.admin.setCountries(admin_countries);
        }else{
            someText.setText("OffSeason");
            admin_countries.remove(admin_countries.indexOf(someText2.getText().toString()));
            WelcomeAdminActivity.admin.setCountries(admin_countries);
            WelcomeAdminActivity.other_countries.add(someText2.getText().toString());
        }

    }

    private String[][] placestoString(){
        String[][] data;
        int x = 0;
        int y = 1;

        if(other_countries.size() > 0 || admin_countries.size() >0) {

            data = new String[other_countries.size()+admin_countries.size()][2];
            for (int i = 0;i < other_countries.size(); i++) {
                data[i][0] = other_countries.get(i);
                data[i][1] = "OffSeason";
                x = i;
            }
            if(other_countries.size() == 0){
                y = 0;
            }
            for (int i = 0;i < admin_countries.size(); i++) {
                data[i+x+y][0] = admin_countries.get(i);
                data[i+x+y][1] = "OnSeason";
            }

        }else{
            data = new String[1][2];
            data[0][0] = " ";
            data[0][1] = "nothing to show here";
        }
        return data;
    }
}
