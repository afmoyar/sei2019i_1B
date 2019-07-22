package presentation.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;

import java.util.ArrayList;

import businessLogic.Controllers.AdminSetupController;
import dataAccess.Models.Administrator;
import dataAccess.Repositories.AdminLogInResult;
import dataAccess.Repositories.ResponseType;
import presentation.Adapters.AdminListAdapter;

public class SeeCountriesActivity extends AppCompatActivity {

    private ArrayList<String> other_countries;
    private ArrayList<String> admin_countries;
    private ArrayList<Boolean> country_status;
    private AdminLogInResult result;
    private ListView listView;
    private Button saveButton;
    private Button cancelButton;

    private int numSelectedCountries;
    private int numNotPickedCountries;
    private int numTotalCountries;

    String[][] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_countries);
        result = (AdminLogInResult) getIntent().getExtras().get("adminResult");
        other_countries = result.countries;
        admin_countries = result.admin.getCountries();

        numSelectedCountries = admin_countries.size();
        numNotPickedCountries = other_countries.size();

        numTotalCountries = numSelectedCountries + numNotPickedCountries;

        country_status = new ArrayList<>(numTotalCountries);
        for (int i = 0; i < numTotalCountries; i++) {

            country_status.add(false);
        }

        listView = findViewById(R.id.countrieslist);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AdminUpdateCountriesTask updateTask = new AdminUpdateCountriesTask(getApplicationContext(), SeeCountriesActivity.this, result, country_status);

                updateTask.execute();


            }
        });

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               onBackPressedWithResult(RESULT_CANCELED);
            }
        });

        data = placesToString();

        listView.setAdapter(new AdminListAdapter(this, data));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateView(position);
            }
        });
    }

    private void updateView(int index) {
        View v = listView.getChildAt(index - listView.getFirstVisiblePosition());

        if (v == null)
            return;

        TextView someText = v.findViewById(R.id.season);

        if (someText.getText().toString() == "OffSeason") {

            someText.setText("OnSeason");
        } else {

            someText.setText("OffSeason");
        }

        country_status.set(index, !country_status.get(index));
    }

    public void onBackPressedWithResult(int resultCode) {

        Intent i = new Intent();
        i.putExtra("adminResult", result);
        setResult(resultCode, i);
        onBackPressed();
    }

    private String[][] placesToString(){
        String[][] data;

        if(numTotalCountries > 0) {

            data = new String[numTotalCountries][2];
            for(int i = 0; i < numSelectedCountries; i++){

                data[i][0] = admin_countries.get(i);
                data[i][1] = "OnSeason";
            }

            for(int i = numSelectedCountries, j = 0; i < numTotalCountries; i++, j++){

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

    class AdminUpdateCountriesTask extends AsyncTask<Void, Void, ResponseType> {

        private Context context;
        private Activity activity;
        private Administrator admin;
        private ArrayList<String> otherCountries;
        private AdminLogInResult data;
        private ArrayList<Boolean> status;
        private ProgressDialog progress;

        public AdminUpdateCountriesTask(Context context, Activity activity, AdminLogInResult data, ArrayList<Boolean> status) {

            this.context = context;
            this.admin = data.admin;
            this.otherCountries = data.countries;
            this.data = data;
            this.status = status;
            this.progress = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progress.setMessage("Saving changes");
            progress.setIndeterminate(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected ResponseType doInBackground(Void... voids) {

            return AdminSetupController.updateAdminCountries(context, admin, otherCountries, status);
        }

        @Override
        protected void onPostExecute(ResponseType response){

            progress.dismiss();

            if(response == ResponseType.SUCCES){

                AdminLogInResult result = AdminSetupController.computeNewCountryDistribution(admin, otherCountries, status);
                data.admin = result.admin;
                data.countries = result.countries;
                onBackPressedWithResult(RESULT_OK);
                return;
            }

            switch (response){

                case CONNECT_ERROR:

                    Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                    break;

                case DB_ERROR:

                    Toast.makeText(context, "Something didn't go as planned...", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }
}
