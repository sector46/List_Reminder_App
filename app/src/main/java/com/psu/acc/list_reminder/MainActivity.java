package com.psu.acc.list_reminder;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView existingListsView;
    private DatabaseHelper databaseHelper;
    ArrayList<String> listNames;
    List<String> existingLists;
//    ListAdapter adapter;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        showIdentity();

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
        /*List<String> existingListIDs = databaseHelper.getAllListIDs();
        System.out.println("Lists so far: (then remove them all)");
        for (String listName : existingListIDs){
            System.out.println("\t" + listName);
            databaseHelper.removeList(listName);
        }*/
//       databaseHelper.removeList();
//
//        //Display existing lists, and remove all initially
        /*List<String> existingLists = databaseHelper.getAllListNames();
        System.out.println("Lists so far: (then remove them all)");
        for (String listName : existingLists){
            System.out.println("\t" + listName);
            databaseHelper.removeListUsingName(listName);
        }
        databaseHelper.dropListsTable();
        databaseHelper.onCreate(db);*/
//
//        //Create lists_table and three new lists.
//        // onCreate method to be called only if you hadn't dropped the lists table (like line 67)

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

        startAlarmService();
    }

    private void startAlarmService() {
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DatabasePollingService.class);
        startService(intent);
    }

    //Display existing lists on the MainActivity page
    private void displayExistingLists() {
        if (databaseHelper.getAllListNames().size() != 0) {

            listNames = new ArrayList<>(databaseHelper.getAllListNames());
//            adapter = new ListAdapter(this,listNames,databaseHelper);
            existingListsView.setAdapter(adapter);
            //For debugging
            existingLists = databaseHelper.getAllListNames();
            for (String list : existingLists) {
                String listID = databaseHelper.getListID(list);
                ListObject listObject = databaseHelper.getList(listID);
                listObject.displayList();
            }
//
//            for (String list : existingLists)
//                System.out.println(list);
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
                    existingListsView.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                       int pos, long id) {
                            // TODO Auto-generated method stub
                            final String  listname    = (String) existingListsView.getItemAtPosition(pos);
                            System.out.print("++++++++++++++++++++"+listname);
                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Do you really want to delete the list "+listname+"?")
                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    })
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            databaseHelper.removeListByName(listname);
                                            existingLists.remove(listname);
                                            adapter.notifyDataSetChanged();
                                           // displayExistingLists();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();


                            return true;
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

    /*The help function*/
    private void showIdentity() {

        // get parent and overlay layouts, use inflator to parse
        // layout.xml to view component. Reuse existing instance if one is found.
        ViewGroup parent = (ViewGroup)findViewById(R.id.mainlayout);
        View identity = findViewById(R.id.identitylayout);
        if (identity==null) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            identity = inflater.inflate(R.layout.identity, parent, false);
            parent.addView(identity);
        }

//        TextView text = (TextView)identity.findViewById(R.id.identityview);
//        identity.bringToFront();
//        Button bOk =(Button) identity.findViewById(R.id.bOk);
//        identity.bringToFront();
//        final View finalIdentity = identity;
//        bOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ((ViewGroup) finalIdentity.getParent()).removeView(finalIdentity);
//            }
//        });

        ImageView iv =(ImageView) identity.findViewById(R.id.image);
        identity.bringToFront();
        final View finalIdentity = identity;
        iv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((ViewGroup) finalIdentity.getParent()).removeView(finalIdentity);
            }
        });
//        // use timer to hide after timeout, make sure there's only
//        // one instance in a message queue.
//        Runnable identityTask = new Runnable(){
//            @Override public void run() {
//                View identity = findViewById(R.id.identitylayout);
//                if (identity!=null)
//                    ((ViewGroup)identity.getParent()).removeView(identity);
//            }
//        };
//        messageHandler.removeCallbacksAndMessages("identitytask");
//        messageHandler.postAtTime(identityTask, "identitytask", SystemClock.uptimeMillis()+duration);
    }


}
