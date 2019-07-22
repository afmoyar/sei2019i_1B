package presentation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;

import java.util.ArrayList;

import dataAccess.Models.Place;
import dataAccess.Models.User;
import presentation.Adapters.ListViewAdapter;

public class SeePlacesActivity extends AppCompatActivity {
    private ListView listView;
    private User myUser;
    private String[][] data;
    private final String placeKey = "placeName";
    private final String userKey = "user";
    private final String currentPlaceKey = "place";
    private final String placeIndexKey = "placeIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        myUser=(User) getIntent().getExtras().get(userKey);
        final ArrayList<Place> places = myUser.getPlaces();
        data = placestoString(places);

        listView = findViewById(R.id.listView);

        listView.setAdapter(new ListViewAdapter(this,data));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String placeName = (String) parent.getItemAtPosition(position);
                if(!placeName.equals(" ")){

                    Intent i = new Intent(getApplicationContext(), PlaceDescriptionActivity.class);
                    i.putExtra(placeKey, placeName);
                    i.putExtra(userKey, myUser);
                    i.putExtra(currentPlaceKey, places.get(position));
                    i.putExtra(placeIndexKey, position);
                    startActivityForResult(i, 1);
                }else{
                    Toast.makeText(SeePlacesActivity.this, "No place to show description", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onBackPressed(){

        Intent i = new Intent();
        i.putExtra(userKey ,myUser);
        setResult(RESULT_OK, i);
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){

            myUser = (User) data.getExtras().get(userKey);
            final ArrayList<Place> places = myUser.getPlaces();
            String [][] placesMatrix = SeePlacesActivity.placestoString(places);
            listView.setAdapter(new ListViewAdapter(getApplicationContext(),placesMatrix));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String placeName = (String) parent.getItemAtPosition(position);
                    if(!placeName.equals(" ")){

                        Intent i = new Intent(getApplicationContext(), PlaceDescriptionActivity.class);
                        i.putExtra(placeKey, placeName);
                        i.putExtra(userKey, myUser);
                        i.putExtra(currentPlaceKey, places.get(position));
                        i.putExtra(placeIndexKey, position);
                        startActivityForResult(i, 1);
                    }else{
                        Toast.makeText(SeePlacesActivity.this, "No place to show description", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private static String[][] placestoString(ArrayList<Place> places){
        String[][] data;
        Place place;
        if(!places.isEmpty()) {
            data = new String[places.size()][3];
            for (int i = 0;i < places.size(); i++) {
                place = places.get(i);
                data[i][0] = place.getName() + ", " + place.getCountryName();
                data[i][1] = place.getDescription();
                data[i][2] = String.valueOf(place.getRating());
            }
        }else{
             data = new String[1][3];
            data[0][0] = " ";
            data[0][1] = "nothing to show here";
            data[0][2] = "0";
        }
        return data;
    }
}
