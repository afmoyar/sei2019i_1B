package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

        final TextView currentDate = findViewById(R.id.currentdate);
        final TextView adminDate = findViewById(R.id.admindate);

        currentDate.setText(dateFormat.format(today));
        adminDate.setText(dateFormat.format(addate));

        if(addate.compareTo(today) < 0){

            Toast.makeText(getApplicationContext(), "the event has expired", Toast.LENGTH_SHORT).show();
        }

        final TextView year = findViewById(R.id.year);
        final TextView month = findViewById(R.id.month);
        final TextView day = findViewById(R.id.day);

        final Button setDate = findViewById(R.id.setdate);
        setDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!year.getText().toString().equals("") &&
                        !month.getText().toString().equals("") &&
                        !day.getText().toString().equals("")){

                    String dat = year.getText().toString()+"/"+month.getText().toString()+"/"+day.getText().toString();

                    if(verifyDate(Integer.parseInt(year.getText().toString()),Integer.parseInt(month.getText().toString())-1,Integer.parseInt(day.getText().toString()))){

                        if(controller.verify(getApplicationContext(),dat)){

                            adminDate.setText(dateFormat.format(addate));
                            controller.updateDate(getApplicationContext(),dat);
                            WelcomeAdminActivity.admin.setLimitDate(dat);
                        }
                    }
                }
                else{

                    Toast.makeText(getApplicationContext(), "Fill all blank spaces", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Boolean verifyDate(int year, int month, int day){

        Calendar Calendar = new GregorianCalendar(year, month, day);
        Date date = Calendar.getTime();

        if(date.compareTo(today) < 0){

            Toast.makeText(getApplicationContext(), "invalid date", Toast.LENGTH_SHORT).show();
            return false;
        }else{

            addate = date;
            return true;
        }
    }
}
