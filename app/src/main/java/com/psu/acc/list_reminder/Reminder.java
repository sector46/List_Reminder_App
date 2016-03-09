package com.psu.acc.list_reminder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chandhnikannatintavida on 2/14/16.
 */
public class Reminder extends AppCompatActivity {
    String date1,time1,reminder1,listName;
    Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
        date1 = ft.format(date).toString();

        Bundle xtra = getIntent().getExtras();
        if (xtra!=null) {

                listName = xtra.getString("listname");

        }


        SimpleDateFormat ft1 = new SimpleDateFormat ("hh:mm a");
        time1 = ft1.format(date).toString();

        //repeat alarm...
        final String array_spinner[];
        array_spinner = new String[4];

        array_spinner[0] = "Never";
        array_spinner[1] = "Daily";
        array_spinner[2] = "Weekly";
        array_spinner[3] = "Monthly";


        s = (Spinner) findViewById(R.id.sReminder);

        ArrayAdapter adapter = new ArrayAdapter(Reminder.this, android.R.layout.simple_spinner_item, array_spinner);

        s.setAdapter(adapter);
        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        TextView tv = (TextView) findViewById(R.id.tvTime);


        tvDate.setText(date1);
        tv.setText(time1);

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

                DialogHandler newFragment = new DialogHandler(getApplicationContext());
                newFragment.show(getFragmentManager() , "time_Picker");

            }
        });

        Button bDone = (Button) findViewById(R.id.bDone);
        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name ="set";
                reminder1 = s.getSelectedItem().toString();

                Intent intent = getIntent();
                intent.putExtra("time",time1);
                intent.putExtra("date",date1);
                intent.putExtra("reminder",reminder1);
                setResult(RESULT_OK,intent);
                finish();


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
           // return new DatePickerDialog(getActivity(), this, yy, mm, dd);
            DatePickerDialog dialog= new DatePickerDialog((new ContextThemeWrapper(getActivity(),R.style.Dialog1)), this, yy, mm,dd);
            return dialog;
        }


        public void populateSetDate(int year, int month, int day) {

            String datenow =date1;
            date1 = month + "/" + day + "/" + year;
            if(date1.compareTo(datenow)<0){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Warning!");
                builder.setMessage("The date you selected is a past date.Please select the current date or a future one.!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
            else{

            TextView tvDate = (TextView) findViewById(R.id.tvDate);
            tvDate.setText(date1);
            }
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {


            populateSetDate(year, monthOfYear + 1, dayOfMonth);

        }
    }

    @SuppressLint("ValidFragment")
    public class DialogHandler extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        Context context;

        public DialogHandler(Context context){
            this.context=context;

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog dialog= new TimePickerDialog(new ContextThemeWrapper(getActivity(),R.style.Dialog), this, hour, minute,DateFormat.is24HourFormat(getActivity()));
            // Create a new instance of TimePickerDialog and return it
            return dialog;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

            String AMPM ="am";
            String min;

            if(hourOfDay>12)
            {
                hourOfDay = hourOfDay -12;
                AMPM = "pm";

            }
            else{
                if(hourOfDay==0)
                    hourOfDay =12;
                else if(hourOfDay==12)
                    AMPM="pm";
            }
            if(minute<10){
                min= "0"+minute;
            }
            else
            {
               min =String.valueOf(minute);
            }

            //Set a message for user
            TextView tv = (TextView) findViewById(R.id.tvTime);
            //Display the user changed time on TextView
            time1 = String.valueOf(hourOfDay)+":"
                    + min+ AMPM;
            tv.setText(String.valueOf(hourOfDay) + ":"
                    + min + AMPM);
        }


    }



}
