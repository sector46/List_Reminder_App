package com.psu.acc.list_reminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.CollapsibleActionView;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView existingListsView;
    private DatabaseHelper databaseHelper;
    ArrayList<String> listNames;
    List<String> existingLists;
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

        databaseHelper = DatabaseHelper.getInstance(MainActivity.this);

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

            existingLists = databaseHelper.getAllListNames();

            Collections.sort(existingLists);
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
                            i.putExtra("main_menu", "true");
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
