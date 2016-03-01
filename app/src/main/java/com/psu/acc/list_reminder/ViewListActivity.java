package com.psu.acc.list_reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by caseybowman on 2/13/16.
 */
public class ViewListActivity extends Activity {


    private Button doneButton;
    private ListView itemList;
    private ArrayAdapter<LinearLayout> listAdapter;
    private ImageButton editReminderButton;
    private ImageButton addItemConfirmationButton;
    private CheckBox enableReminderCheckBox;
    private EditText titleEditText;
    private EditText addItemEditText;
    private TextView reminderTextView;

    boolean editMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list);
        Bundle xtra = getIntent().getExtras();

        doneButton             = (Button) findViewById(R.id.done_button);
        itemList               = (ListView) findViewById(R.id.item_list);
        editReminderButton     = (ImageButton) findViewById(R.id.edit_reminder_button);
        addItemConfirmationButton = (ImageButton) findViewById(R.id.add_item_confirmation_button);
        enableReminderCheckBox = (CheckBox) findViewById(R.id.enable_reminder_checkbox);
        titleEditText          = (EditText) findViewById(R.id.title_edittext);
        addItemEditText        = (EditText) findViewById(R.id.add_item_edittext);
        reminderTextView       = (TextView) findViewById(R.id.reminder_textview);

        ArrayList<String> list = new ArrayList<String>();
        list.add("data1");
        list.add("data2");

        itemList.setAdapter(new ItemAdapter(this, itemList, list));

        if (xtra!=null)
        {
            reminderTextView.setText("Reminder set to \n"+xtra.getString("date")+" "+xtra.getString("time"));
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        doneButton.setOnClickListener(new View.OnClickListener() {
            // enable/disable visibility on views depending on what mode the user is in
            @Override
            public void onClick(View view) {
                if (editMode == true) {
                    editReminderButton.setVisibility(View.INVISIBLE);
                    addItemConfirmationButton.setVisibility(View.INVISIBLE);
                    enableReminderCheckBox.setVisibility(View.INVISIBLE);
                    titleEditText.setFocusable(false);
                    addItemEditText.setVisibility(View.INVISIBLE);
                    doneButton.setText(R.string.edit_list_button);
                    if (!enableReminderCheckBox.isChecked()) {
                        reminderTextView.setVisibility(View.INVISIBLE);
                    }
                    editMode = false;
                } else {
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
                startActivity(intent);
            }
        });
    }

}
