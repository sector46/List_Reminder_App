package com.psu.acc.list_reminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by caseybowman on 2/13/16.
 */
public class ViewListActivity extends AppCompatActivity {


    private Button doneButton;
    private ListView itemList;
    private ArrayAdapter<LinearLayout> listAdapter;
    private ImageButton editTitleButton;
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

        doneButton             = (Button) findViewById(R.id.done_button);
        itemList               = (ListView) findViewById(R.id.item_list);
        editTitleButton        = (ImageButton) findViewById(R.id.edit_title_button);
        editReminderButton     = (ImageButton) findViewById(R.id.edit_reminder_button);
        addItemConfirmationButton = (ImageButton) findViewById(R.id.add_item_confirmation_button);
        enableReminderCheckBox = (CheckBox) findViewById(R.id.enable_reminder_checkbox);
        titleEditText          = (EditText) findViewById(R.id.title_edittext);
        addItemEditText        = (EditText) findViewById(R.id.add_item_edittext);
        reminderTextView       = (TextView) findViewById(R.id.reminder_textview);

        doneButton.setOnClickListener(new View.OnClickListener() {
            // enable/disable visibility on views depending on what mode the user is in
            @Override
            public void onClick(View view) {
                if (editMode == true) {
                    editTitleButton.setVisibility(View.INVISIBLE);
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
                    editTitleButton.setVisibility(View.VISIBLE);
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
    }

}
