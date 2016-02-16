package com.psu.acc.list_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by chandhnikannatintavida on 2/14/16.
 */
public class Reminder extends AppCompatActivity {
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

        // set date and time....

        Button bDate =(Button) findViewById(R.id.bDate);
        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DatePicker.class);
                startActivity(intent);
            }
        });
        Button bTime =(Button) findViewById(R.id.bTime);
        bTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TimePicker.class);
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

}
