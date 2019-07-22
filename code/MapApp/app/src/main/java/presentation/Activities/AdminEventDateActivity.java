package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapapp.BuildConfig;
import com.example.mapapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import businessLogic.Controllers.DateController;

public class AdminEventDateActivity extends AppCompatActivity {

    private String limitdate;
    private Date addate;
    private Date today;
    DateController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_date);

        controller = new DateController();

        limitdate = getIntent().getStringExtra("date");
        String[] chain = controller.split(limitdate);

        Calendar todayCalendar = new GregorianCalendar(Integer.parseInt(chain[0]), Integer.parseInt(chain[1])-1, Integer.parseInt(chain[2]));
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        addate = todayCalendar.getTime();
        today = new Date();

        final TextView currentdate = findViewById(R.id.currentdate);
        final TextView admindate = findViewById(R.id.admindate);

        currentdate.setText(dateFormat.format(today));
        admindate.setText(dateFormat.format(addate));

        int x = addate.compareTo(today);

        if(x == -1){
            Toast.makeText(getApplicationContext(), "the event has expired", Toast.LENGTH_SHORT).show();
        }

        final TextView year = findViewById(R.id.year);
        final TextView month = findViewById(R.id.month);
        final TextView day = findViewById(R.id.day);

        final Button setdate = (Button) findViewById(R.id.setdate);
        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(year.getText().toString().equals("") == false && month.getText().toString().equals("") == false && day.getText().toString().equals("") == false){
                    String dat = year.getText().toString()+"/"+month.getText().toString()+"/"+day.getText().toString();
                    if(true == verifydate(Integer.parseInt(year.getText().toString()),Integer.parseInt(month.getText().toString())-1,Integer.parseInt(day.getText().toString()))){
                        if(controller.verify(getApplicationContext(),dat) == true){
                            admindate.setText(dateFormat.format(addate));
                            controller.updatedate(getApplicationContext(),dat);
                            WelcomeAdminActivity.admin.setLimitDate(dat);
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Fill all blank spaces", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public Boolean verifydate(int year, int month, int day){
        Calendar Calendar = new GregorianCalendar(year, month, day);
        Date date = Calendar.getTime();
        int x = date.compareTo(today);
        if(x == -1){
            Toast.makeText(getApplicationContext(), "invalid date", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            addate = date;
            return true;
        }
    }

}
