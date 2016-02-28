package com.psu.acc.list_reminder;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by chandhnikannatintavida on 2/14/16.
 */
public class Reminder extends AppCompatActivity {
    String date1,time1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);

        //repeat alarm...
        final String array_spinner[];
        array_spinner = new String[4];

        array_spinner[0] = "Never";
        array_spinner[1] = "Daily";
        array_spinner[2] = "Weekly";
        array_spinner[3] = "Monthly";


        final Spinner s = (Spinner) findViewById(R.id.sReminder);

        ArrayAdapter adapter = new ArrayAdapter(Reminder.this, android.R.layout.simple_spinner_item, array_spinner);

        s.setAdapter(adapter);
        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        TextView tv = (TextView) findViewById(R.id.tvTime);

        tvDate.setText("DD:MM:YY");
        tv.setText("HH:MM");

        // set date and time....

        Button bDate = (Button) findViewById(R.id.bDate);

        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragmentTrans();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
        Button bTime = (Button) findViewById(R.id.bTime);
        bTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePicker");

            }
        });

        Button bDone = (Button) findViewById(R.id.bDone);
        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name ="set";
                Intent intent = new Intent(v.getContext(), ViewListActivity.class);
                intent.putExtra("set",name);
                intent.putExtra("time",time1);
                intent.putExtra("date",date1);
                startActivity(intent);


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("ValidFragment")
    public class SelectDateFragmentTrans extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }


        public void populateSetDate(int year, int month, int day) {
           date1 = month + "/" + day + "/" + year;
//            TransferFragment.date.setText(month+"/"+day+"/"+year);
            TextView tvDate = (TextView) findViewById(R.id.tvDate);
            tvDate.setText(date1);
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {


            populateSetDate(year, monthOfYear + 1, dayOfMonth);

        }
    }

    @SuppressLint("ValidFragment")
    public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //Use the current time as the default values for the time picker
            final Calendar c = Calendar.getInstance();

            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            //Create and return a new instance of TimePickerDialog
            return new TimePickerDialog(getActivity(),this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        //onTimeSet() callback method
        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            //Do something with the user chosen time
            //Get reference of host activity (XML Layout File) TextView widget

            //Set a message for user
            TextView tv = (TextView) findViewById(R.id.tvTime);
            //Display the user changed time on TextView
            time1 = String.valueOf(hourOfDay)+" : "
                    + String.valueOf(minute);
            tv.setText(String.valueOf(hourOfDay)+" : "
                    + String.valueOf(minute));
        }
    }

}
