package com.psu.acc.list_reminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by chandhnikannatintavida on 2/14/16.
 */
public class Templates extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.templates);

        /*
        if the user selects new list display a custom list which is blank
         */
        Button bNewList =(Button) findViewById(R.id.bNewList);
        bNewList.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                    //display a new list....
                Intent intent = new Intent(v.getContext(), ViewListActivity.class);
                startActivity(intent);
                //setContentView(R.layout.new_list);
                //Button bNewReminder = (Button) findViewById(R.id.bNewReminder);
                //bNewReminder.setOnClickListener(new View.OnClickListener() {

                //    @Override
                //    public void onClick(View v) {

                //    }
                //});
            }
        });

         /*
        if the user selects existing list display a existing list which is blank
         */

        final String array_spinner[];
        array_spinner = new String[4];

        array_spinner[0] = "Select List";
        array_spinner[1] = "Grocery List";
        array_spinner[2] = "Shopping List";
        array_spinner[3] = "Pills list";


        final Spinner s = (Spinner) findViewById(R.id.sUpdateList);

        ArrayAdapter adapter = new ArrayAdapter(Templates.this, android.R.layout.simple_spinner_item, array_spinner);

        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (s.getSelectedItem().toString()) {
                    case "Grocery List":
                        Intent intent = new Intent(getApplicationContext(), ViewListActivity.class);
                        startActivity(intent);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "No List selected!!!", Toast.LENGTH_LONG).show();

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
