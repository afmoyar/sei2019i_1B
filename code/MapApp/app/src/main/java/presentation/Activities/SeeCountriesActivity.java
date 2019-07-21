package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mapapp.R;

import java.util.ArrayList;

import presentation.Adapters.AdminListAdapter;

public class SeeCountriesActivity extends AppCompatActivity {

    private ArrayList<String> other_countries;
    private ArrayList<String> admin_countries;
    private ArrayList<Boolean> country_status;
    private ListView listView;

    private int numSelectedCountries;
    private int numNotPickedCountries;
    private int numTotalCoutries;

    String[][] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_countries);
        other_countries = getIntent().getStringArrayListExtra("othercountries");
        admin_countries = getIntent().getStringArrayListExtra("admincountries");

        numSelectedCountries = admin_countries.size();
        numNotPickedCountries = other_countries.size();

        numTotalCoutries = numSelectedCountries + numNotPickedCountries;

        country_status = new ArrayList<>(numTotalCoutries);
        for(int i = 0; i < numTotalCoutries; i++){

            country_status.add(false);
        }

        listView = findViewById(R.id.countrieslist);

        data = placesToString();

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

    private String[][] placesToString(){
        String[][] data;

        if(numTotalCoutries > 0) {

            data = new String[numTotalCoutries][2];
            for(int i = 0; i < numSelectedCountries; i++){

                data[i][0] = admin_countries.get(i);
                data[i][1] = "OnSeason";
            }

            for(int i = numSelectedCountries, j = 0; i < numTotalCoutries; i++, j++){

                data[i][0] = other_countries.get(j);
                data[i][1] = "OffSeason";
            }
        }
        else{

            data = new String[1][2];
            data[0][0] = " ";
            data[0][1] = "nothing to show here";
        }
        return data;
    }
}
