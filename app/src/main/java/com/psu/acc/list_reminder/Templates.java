package com.psu.acc.list_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by chandhnikannatintavida on 2/14/16.
 */
public class Templates extends AppCompatActivity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.templates);

        // Get ListView object from xml
        list = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[] { "Grocery List",
                "Christmas shopping list",
                "Pills"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        list.setAdapter(adapter);

        // ListView Item Click Listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                String  itemValue    = (String) list.getItemAtPosition(position);
                switch (itemValue) {
                    case "Grocery List":
                        Intent intent = new Intent(getApplicationContext(), ViewListActivity.class);
                        startActivity(intent);
                        break;
                }

            }

        });

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
