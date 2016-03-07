package com.psu.acc.list_reminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by caseybowman on 2/13/16.
 */
public class ViewListActivity extends Activity {


    private Button doneButton;
    private ListView listView;
    private ImageButton editReminderButton;
    private ImageButton addItemConfirmationButton;
    private CheckBox enableReminderCheckBox;
    private EditText titleEditText;
    private EditText addItemEditText;
    private TextView reminderTextView;

    private boolean editMode = true;
    private ItemAdapter adapter;
    private DatabaseHelper databaseHelper;
    private ListObject list;

    private ArrayList<String> itemNames;
    private ArrayList<String> strike;
    private HashMap<String,String> map;

    static final int REQUEST_REMSET = 1;
    Boolean callingClass =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list);
        Bundle xtra = getIntent().getExtras();

        doneButton             = (Button) findViewById(R.id.done_button);
        listView               = (ListView) findViewById(R.id.item_list);
        editReminderButton     = (ImageButton) findViewById(R.id.edit_reminder_button);
        addItemConfirmationButton = (ImageButton) findViewById(R.id.add_item_confirmation_button);
        enableReminderCheckBox = (CheckBox) findViewById(R.id.enable_reminder_checkbox);
        titleEditText          = (EditText) findViewById(R.id.title_edittext);
        addItemEditText        = (EditText) findViewById(R.id.add_item_edittext);
        reminderTextView       = (TextView) findViewById(R.id.reminder_textview);

        databaseHelper = DatabaseHelper.getInstance(ViewListActivity.this);

        /* Get list name from main activity, based on click by the user. */

        if (xtra!=null) {
            if (xtra.getString("listname")!= null) {
                String listName = xtra.getString("listname");
                // Create ListObject with the list retrieved from DB

                list = databaseHelper.getList(listName);
            }
            if (xtra.getString("calling_class")!= null) {
                callingClass =true;
            }
        }

        /***** Object Creation (in case you've clicked New at Main Activity) *****/
        String name = "";
        HashMap<String, String> items = new HashMap<String, String>();
//        items.put("Milk", "false");
//        items.put("Eggs", "false");
//        items.put("Bananas", "false");
//        items.put("Bread", "false");
        String reminderDateTime = "No reminder is set"; //"03.01.2016 12:45 AM";
        String reminderRecurrence = "Never"; //Daily";
        String reminderEnabled = "false"; //"true";

        //If list is null, meaning you've clicked new at MainActivity, instantiate ListObject
        if (list == null)
            list = new ListObject(name, items, reminderDateTime, reminderRecurrence, reminderEnabled);

        //if this activity is called from templates class callingClass will be true and then set listname as blank
        if(callingClass)
            titleEditText.setText("");
        else
        titleEditText.setText(list.getListName());


        reminderTextView.setText(list.getReminderDateTime());
        if (list.getReminderEnabled().equals("true")) {
            enableReminderCheckBox.setChecked(true);
        } else {
            enableReminderCheckBox.setChecked(false);
        }
        itemNames = new ArrayList<String>(list.getListItems().keySet());
        strike = new ArrayList<String>(list.getListItems().values());

        adapter = new ItemAdapter(this, itemNames, strike);
        listView.setAdapter(adapter);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        addItemConfirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButton();
                InputMethodManager imm = (InputMethodManager)addItemEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(addItemEditText.getWindowToken(), 0);
            }
        });

        addItemEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setButton();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            // enable/disable visibility on views depending on what mode the user is in
            @Override
            public void onClick(View view) {
                if (editMode == true) {
//                    if (checkedTitle()) {
                        adapter.setInvisible(listView);
                        editReminderButton.setVisibility(View.INVISIBLE);
                        addItemConfirmationButton.setVisibility(View.INVISIBLE);
                        enableReminderCheckBox.setVisibility(View.INVISIBLE);
                        titleEditText.setFocusable(false);
                        addItemEditText.setVisibility(View.INVISIBLE);
                        doneButton.setText(R.string.edit_list_button);
                       setList();


                } else {
                    adapter.setVisible(listView);
                    editReminderButton.setVisibility(View.VISIBLE);
                    addItemConfirmationButton.setVisibility(View.VISIBLE);
                    enableReminderCheckBox.setVisibility(View.VISIBLE);
                    titleEditText.setFocusable(true);
                    addItemEditText.setVisibility(View.VISIBLE);
                    doneButton.setText(R.string.done_button);
                    reminderTextView.setVisibility(View.VISIBLE);
                    editMode = true;


                }

            }
        });

        editReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Reminder.class);
                startActivityForResult(intent, REQUEST_REMSET);

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            reminderTextView.setText(data.getStringExtra("date") + " " + data.getStringExtra("time") +
                            " - " + data.getStringExtra("reminder"));
                    list.setReminderDateTime(data.getStringExtra("date") + " " + data.getStringExtra("time") +
                            " - " + data.getStringExtra("reminder"));
               }

    }

    boolean checkedTitle() {
        ArrayList<String> names = (ArrayList<String>) databaseHelper.getAllListNames();
        for(int i=0; i<names.size(); i++) {
            if (list.getListName().equals(names.get(i))) {
//                Toast.makeText(ViewListActivity.this, "'" + list.getListName() + "' is already in the database!",
//                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    void setButton() {
        ArrayList<String> items = new ArrayList<String>(list.getListItems().keySet());
        for(int i=0; i<items.size(); i++) {
            Log.i("items values: ", items.get(i));
        }
        int count = items.size();
        boolean isUnique = true;
        String text = addItemEditText.getText().toString();
        if (text.isEmpty() || text.replace(" ", "").equals("")) {
            // add toast "Enter a value"
            Toast.makeText(ViewListActivity.this, "Nothing was entered!",
                    Toast.LENGTH_SHORT).show();
        } else {
            for(int i=0; i<count; i++) {
                if(text.equals(items.get(i))) {
                    isUnique = false;
                }
            }
            if (isUnique) {
                list.addItemToList(text);
                adapter.addItem(text);
            } else {
                // add toast "Item name already in list!"
                Toast.makeText(ViewListActivity.this, "'" + text + "' is already in the list!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        addItemEditText.setText("");
    }
    public void setList(){
        map = new HashMap<String,String>();
        itemNames = adapter.getNames();
        strike = adapter.getStrikes();
        if(itemNames.size() == strike.size()){
            for(int index = 0; index < itemNames.size(); index++){
                map.put(itemNames.get(index), strike.get(index));
            }
        }
        list.setListItems(map);
        list.setReminderDateTime(reminderTextView.getText().toString());
        if (enableReminderCheckBox.isChecked()) {
            list.setReminderEnabled("true");
        } else {
            reminderTextView.setVisibility(View.INVISIBLE);
            list.setReminderEnabled("false");
        }
        System.out.print("+++++++++"+titleEditText.getText().toString());
        if(titleEditText.getText().toString().equals("")){

            Toast.makeText(ViewListActivity.this, "You have not entered a Title for the list.Please enter a Title!",
                    Toast.LENGTH_SHORT).show();
            list.setListName("Test Title");

        }else {
            list.setListName(titleEditText.getText().toString());
            databaseHelper.updateList(list);
            editMode = false;
        }
    }

}
