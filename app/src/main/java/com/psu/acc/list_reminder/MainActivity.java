package com.psu.acc.list_reminder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView existingListsView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Button bAddList =(Button) findViewById(R.id.bAddList);

        bAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Templates.class);
                startActivity(intent);
            }
        });

        existingListsView = (ListView)findViewById(R.id.existingListsView);

        //Testing the DatabaseHelper methods. (to be removed after integration)
        databaseHelper = DatabaseHelper.getInstance(MainActivity.this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        //Display existing lists, and remove all initially
        List<String> existingLists = databaseHelper.getAllListNames();
        System.out.println("Lists so far: (then remove them all)");
        for (String listName : existingLists){
            System.out.println("\t" + listName);
            databaseHelper.removeList(listName);
        }
        databaseHelper.dropListsTable();

        //Create lists_table and three new lists.
        // onCreate method to be called only if you hadn't dropped the lists table (like above)
        databaseHelper.onCreate(db);
        HashMap<String, String> items = new HashMap<>();
        items.put("Egg", "false");
        items.put("Milk", "false");
        ListObject listObject1 = new ListObject("List 1", items, "Jan 11, 2016 5:00 am", "weekly", "true");
        databaseHelper.addList(listObject1);
        items.put("Bread", "false");
        ListObject listObject2 = new ListObject("List 2", items, "Jan 12, 2016 6:00 am", "monthly", "true");
        databaseHelper.addList(listObject2);
        items.put("Banana", "false");
        ListObject listObject3 = new ListObject("List 3", items, "Jan 13, 2016 7:00 am", "hourly", "true");
        databaseHelper.addList(listObject3);

        //Display newly created list names
        existingLists = databaseHelper.getAllListNames();
        System.out.println("Created 3 new lists:");
        for (String listName : existingLists){
            System.out.println("\t" + listName);
        }

        //Retrieve a list
        System.out.println("Displaying List2");
        ListObject aList = databaseHelper.displayList("List2");
        aList.displayList();
        //Removes item Egg from list and display it
        System.out.println("Remove item Egg from List2");
        databaseHelper.removeItemFromList("List2", "Egg");
        aList = databaseHelper.displayList("List2");
        System.out.println("Updated List2");
        aList.displayList();
        //Remove list1
        System.out.println("Remove List1");
        databaseHelper.removeList("List1");

        //Display existing lists
        existingLists = databaseHelper.getAllListNames();
        System.out.println("Lists so far:");
        for (String listName : existingLists){
            System.out.println("\t" + listName);
        }

        //Update List3, strike off bread (change value from false to true), and add new item 'Nutella'
        aList = databaseHelper.displayList("List3");
        System.out.println("Original List3");
        aList.displayList();
        Map<String, String> itemsList3 = aList.getListItems();
        itemsList3.put("Nutella", "false");
        itemsList3.put("Milk", "true");
        aList.setListItems(itemsList3);
        //Update list in the database, removes old one and creates a new one with the same name.
        databaseHelper.updateList(aList);
        System.out.println("Updated List3");
        aList = databaseHelper.displayList("List3");
        aList.displayList();
        displayExistingLists();
    }

    private void displayExistingLists(){
        List<String> existingLists = databaseHelper.getAllListNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, existingLists);
        existingListsView.setAdapter(adapter);
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
