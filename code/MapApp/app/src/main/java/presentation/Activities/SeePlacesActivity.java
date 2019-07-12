package presentation.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mapapp.R;

import java.util.ArrayList;

import dataAccess.Models.Place;
import dataAccess.Models.User;

public class SeePlacesActivity extends AppCompatActivity {
    ListView listView;
    private User myUser;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        myUser=(User) getIntent().getExtras().get("user");
        ArrayList<Place> places = myUser.getPlaces();
        arrayList = placestoString(places);

        listView = findViewById(R.id.listView);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

    private ArrayList<String> placestoString(ArrayList<Place> places){
        ArrayList<String> arrayList = new ArrayList<String>();
        if(!places.isEmpty()) {
            for (Place place : places) {
                arrayList.add("[" + place.getLatitude() + "," + place.getLongitude() + "] " + place.getCountryName() + ", " + place.getName() + ": '" + place.getDescription() + "' ");
            }
        }else{
            arrayList.add("nothing to show here");
        }
        return arrayList;
    }
}
