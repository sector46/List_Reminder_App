package com.psu.acc.list_reminder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView existingListsView;
    private DatabaseHelper databaseHelper;
    ArrayList<String> listNames;
    List<String> existingLists;
    //ListAdapter adapter;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Button bAddList = (Button) findViewById(R.id.bAddList);

        bAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Templates.class);
                startActivity(intent);
            }
        });

        existingListsView = (ListView) findViewById(R.id.existingListsView);

        //Testing the DatabaseHelper methods. (to be removed after integration)
        databaseHelper = DatabaseHelper.getInstance(MainActivity.this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
//       databaseHelper.removeList(" ");
//
//        //Display existing lists, and remove all initially
//        List<String> existingLists = databaseHelper.getAllListNames();
//        System.out.println("Lists so far: (then remove them all)");
//        for (String listName : existingLists){
//            System.out.println("\t" + listName);
//            databaseHelper.removeList(listName);
//        }
//        databaseHelper.dropListsTable();
//
//        //Create lists_table and three new lists.
//        // onCreate method to be called only if you hadn't dropped the lists table (like line 67)
//        databaseHelper.onCreate(db);
//        HashMap<String, String> items = new HashMap<>();
//        items.put("Egg", "false");
//        items.put("Milk", "false");
//        items.put("Bread", "false");
//        items.put("Banana", "false");
//        ListObject listObject1 = new ListObject("Grocery List", items, "March 12, 2016 10:00 am", "weekly", "true");
//        databaseHelper.addList(listObject1);
//        items = new HashMap<>();
//        items.put("Chairs", "false");
//        items.put("Dining table", "false");
//        items.put("Mattress", "false");
//        items.put("Coffee table", "false");
//        ListObject listObject2 = new ListObject("Garage Sale", items, "March 20, 2016 7:00 pm", "none", "true");
//        databaseHelper.addList(listObject2);
//        items = new HashMap<>();
//        items.put("Tees", "false");
//        items.put("Boots", "false");
//        items.put("Skateboard", "false");
//        ListObject listObject3 = new ListObject("Thanksgiving Shopping", items, "Nov 15, 2016 5:00 pm", "none", "true");
//        databaseHelper.addList(listObject3);
//
//        //Display newly created list names
//        existingLists = databaseHelper.getAllListNames();
//        System.out.println("Created 3 new lists:");
//        for (String listName : existingLists){
//            System.out.println("\t" + listName);
//        }
//
//        //Retrieve a list and display its attributes.
//        System.out.println("Displaying Garage Sale List");
//        ListObject aList = databaseHelper.getList("Garage Sale");
//        aList.displayList();
//        //Removes item Dining table from list and display it
//        System.out.println("Remove item Dining table from Garage Sale");
//        databaseHelper.removeItemFromList("Garage Sale", "Dining table");
//        aList = databaseHelper.getList("Garage Sale");
//        System.out.println("Updated Garage Sale List");
//        aList.displayList();
//        //Remove Grocery List
//        System.out.println("Remove Grocery List");
//        databaseHelper.removeList("Grocery List");
//
//        //Update Thanksgiving Shopping, strike off boots (change value from false to true), and add new item 'Home theatre'
//        aList = databaseHelper.getList("Thanksgiving Shopping");
//        System.out.println("Original Thanksgiving Shopping List");
//        aList.displayList();
//        Map<String, String> itemsList3 = aList.getListItems();
//        itemsList3.put("Home theatre", "false");
//        itemsList3.put("Boots", "true");
//        aList.setListItems(itemsList3);
//        //Update list in the database, removes old one and creates a new one with the same name.
//        databaseHelper.updateList(aList);
//        System.out.println("Updated Thanksgiving Shopping");
//        aList = databaseHelper.getList("Thanksgiving Shopping");
//        aList.displayList();

        displayExistingLists();
    }

    //Display existing lists on the MainActivity page
    private void displayExistingLists() {
        if (databaseHelper.getAllListNames().size() != 0) {

//            listNames = new ArrayList<>(databaseHelper.getAllListNames());
//            adapter = new ListAdapter(this,listNames,databaseHelper);
//            existingListsView.setAdapter(adapter);
            existingLists = databaseHelper.getAllListNames();

            for (String list : existingLists)
                System.out.println(list);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, existingLists);
            existingListsView.setAdapter(adapter);

                    // ListView Item Click Listener
                    existingListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String  itemValue    = (String) existingListsView.getItemAtPosition(position);
                            Intent i =new Intent(getApplicationContext(),ViewListActivity.class);
                            i.putExtra("listname", itemValue);
                            startActivity(i);
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onResume() {

        super.onResume();
        displayExistingLists();

    }
    @Override
    public void onRestart() {

        super.onRestart();
        displayExistingLists();

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
