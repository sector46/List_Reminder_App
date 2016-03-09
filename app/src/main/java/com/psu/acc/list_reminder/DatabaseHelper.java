package com.psu.acc.list_reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ARavi1 on 2/27/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /** Database Helper instance. */
    private static DatabaseHelper mInstance = null;
    /** Database Version */
    private static final int DATABASE_VERSION = 1;
    /** Database Name */
    private static final String DATABASE_NAME = "lists.db";
    /** Table Name */
    private static final String LISTS_TABLE = "lists_table";

    private Context mContext;

    /** List Table column names
     *  Stores "list_name", "reminder_date_time", "reminder_recurrence" and "reminder_enabled"
     */
    private static final String LIST_ID = "list_id"; //Primary Key for Lists Table
    private static final String LIST_NAME = "list_name";
    private static final String REMINDER_DATE = "reminder_date";
    private static final String REMINDER_TIME = "reminder_time";
    private static final String REMINDER_RECURRENCE = "reminder_recurrence";
    private static final String REMINDER_ENABLED = "reminder_enabled";

    // Column names to store list items
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_STATUS = "item_status";

    private static final String[] LISTS_TABLE_COLUMNS = {LIST_ID, LIST_NAME, REMINDER_DATE, REMINDER_TIME, REMINDER_RECURRENCE, REMINDER_ENABLED};

    /**
     * Use the application context as suggested by CommonsWare. This will ensure that you don't accidentally leak an Activity's context.
     * (Article: http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
     * @param ctx Context
     * @return Instance of the DatabaseHelper
     */
    public static DatabaseHelper getInstance(Context ctx)
    {
        if (mInstance == null)
        {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation. Make call to static factory method "getInstance()" instead.
     * @param context Context
     */
    private DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    // SQL statement to create lists table
    String CREATE_LISTS_TABLE = "CREATE TABLE " + LISTS_TABLE + " ( " + LIST_ID + " TEXT PRIMARY KEY, " +
                LIST_NAME + " TEXT," +
                REMINDER_DATE + " TEXT," +
                REMINDER_TIME + " TEXT," +
                REMINDER_RECURRENCE + " TEXT, " +
                REMINDER_ENABLED + " TEXT)";

        // Create lists table
        db.execSQL(CREATE_LISTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older lists table if they existed
        db.execSQL("DROP TABLE IF EXISTS " + LISTS_TABLE);

        // Create fresh lists table
        this.onCreate(db);
    }

    // Operations

    /**
     * Get list names of all list IDs that have been inserted into the database.
     * @return List of Strings (list IDs).
     */
    public List<String> getAllListIDs() {
        List<String> lists = new ArrayList<>();
        // Select all list name's Query
        String selectQuery = "SELECT " + LIST_ID + " FROM " + LISTS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor listsCursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (listsCursor.moveToFirst()) {
            do {
                String listName = listsCursor.getString(0);
                // Adding list_name to the list.
                lists.add(listName);
            } while (listsCursor.moveToNext());
        }
        listsCursor.close();
        return lists;
    }

    /**
     * Get list names of all lists that have been inserted into the database.
     * @return List of strings (list names).
     */
    public List<String> getAllListNames() {
        List<String> lists = new ArrayList<>();
        // Select all list name's Query
        String selectQuery = "SELECT " + LIST_NAME + " FROM " + LISTS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor listsCursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (listsCursor.moveToFirst()) {
            do {
                String listName = listsCursor.getString(0).replaceAll("_", " ");
                // Adding list_name to the list.
                lists.add(listName);
            } while (listsCursor.moveToNext());
        }
        listsCursor.close();
        return lists;
    }

    public String getListID(String listName) {
        String[] name = new String[]{ listName.replaceAll(" ", "_") };
        String listID = "";
        String selectQuery = "SELECT " + LIST_ID + " FROM " + LISTS_TABLE + " WHERE " + LIST_NAME + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor listsCursor = db.rawQuery(selectQuery, name);

        // Looping through all rows and adding to list
        if (listsCursor.moveToFirst()) {
            listID = listsCursor.getString(0);
        }
        listsCursor.close();
        return listID;
    }

    /**
     * Add a list to the database.
     * NOTE: Spaces in the list_name are replaced by underscore before insertion to database.
     * @param listObject listObject populated with all its fields.
     * @return true if the list was successfully added to the DB and false if it failed.
     */
    public boolean addList(ListObject listObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        String listID = listObject.getListID();
        String listName = listObject.getListName().replaceAll(" ", "_");
        if (Character.isDigit(listName.charAt(0))) {
            return false;
        } else {
            // Insert list info into the lists_table (all info except list items)
            ContentValues listValues = new ContentValues();
            listValues.put(LIST_ID, listID); // List ID
            listValues.put(LIST_NAME, listName); // List name
            listValues.put(REMINDER_DATE, listObject.getReminderDate()); // Reminder date for list
            listValues.put(REMINDER_TIME, listObject.getReminderTime()); //Reminder time for list (hh:mm AM/PM)
            listValues.put(REMINDER_RECURRENCE, listObject.getReminderRecurrence()); // Recurrence for the reminder
            listValues.put(REMINDER_ENABLED, listObject.getReminderEnabled()); // If reminder is enabled.
            // Inserting Row into lists table
            db.insert(LISTS_TABLE, null, listValues);

            //Create a table with the list ID to store item name and if its done/struck off (value: true) or incomplete (also, default value: false)
            // SQL statement to create list table to store items on the list.
            String CREATE_NEW_LIST_TABLE = "CREATE TABLE " + listID +
                    " ( " + ITEM_NAME + " TEXT PRIMARY KEY, " +
                    ITEM_STATUS + " TEXT)";

            db.execSQL(CREATE_NEW_LIST_TABLE);

            //Insert items into the list table.
            Map<String, String> items = listObject.getListItems();
            for (String key : items.keySet()) {
                String value = items.get(key);
                ContentValues ITEM_VALUES = new ContentValues();
                ITEM_VALUES.put(ITEM_NAME, key); // Item name
                ITEM_VALUES.put(ITEM_STATUS, value); // Item status
                db.insert(listID, null, ITEM_VALUES);
            }
            return true;
        }
    }

    /**
     * Removes the lists table from the database.
     */
    public void dropListsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete lists table, deletes all lists (EVERYTHING!).
        db.execSQL("DROP TABLE IF EXISTS " + LISTS_TABLE);
    }

    /**
     * Removes the specified list from the database.
     * @param inputID ID of the list to be removed
     */
    public void removeList(String inputID) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete list table containing all the items on that list.
        db.execSQL("DROP TABLE IF EXISTS " + inputID);
        //Remove row from lists table for this list.
        db.delete(LISTS_TABLE, LIST_ID + "=?", new String[]{inputID});
    }

    /**
     * Removes the specified list from the database.
     * @param name name of the list to be removed
     */
    public void removeListByName(String name) {
        String ID = getListID(name);
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete list table containing all the items on that list.
        db.execSQL("DROP TABLE IF EXISTS " + ID);
        //Remove row from lists table for this list.
        db.delete(LISTS_TABLE, LIST_ID + "=?", new String[]{ID});
    }

    /**
     * Removes a specific item from the list table in the database.
     * @param listID ID of the list.
     * @param itemName name of the item on the list.
     */
    public void removeItemFromList(String listID, String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(listID, ITEM_NAME + "= ?", new String[]{itemName});
    }

    /**
     * Update a list.
     * @param listObject the list object including the changes to the list.
     * @return true if list was updated in the DB successfully, else returns false.
     */
    public boolean updateList(ListObject listObject) {
        //Remove the list from the database and create it again with the new listObject
        String listID = listObject.getListID();
        if (getAllListIDs().contains(listID)) {
            removeList(listObject.getListID());
        }
        return addList(listObject);
    }

    /**
     * Get an already created list.
     * Replaces underscore from list name back to a space before displaying the list to the user
     * @param listID name of the list (including spaces).
     * @return the listObject based on values in the database for that list.
     */
    public ListObject getList(String listID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor listCursor = db.query(LISTS_TABLE, LISTS_TABLE_COLUMNS, LIST_ID + " = ?", new String[]{listID}, null, null, null, null);

        /* if we got results get the first one; otherwise, return null */
        if (listCursor != null)
            if(!listCursor.moveToFirst())
                return null;

        /* Build a list object based on results for that list */
        ListObject listObject = new ListObject();
        String listIDFromDB = listCursor.getString(0);
        String listNameFromDB = listCursor.getString(1);
        listObject.setListID(listIDFromDB);
        listObject.setListName(listNameFromDB.replaceAll("_", " "));
        listObject.setReminderDate(listCursor.getString(2));
        listObject.setReminderTime(listCursor.getString(3));
        listObject.setReminderRecurrence(listCursor.getString(4));
        listObject.setReminderEnabled(listCursor.getString(5));

        listCursor.close();

        /* Get the items for the list. */
        String selectItemsQuery = "SELECT * FROM " + listID;
        Cursor listItemsCursor = db.rawQuery(selectItemsQuery, null);

        Map<String, String> itemsOnList = new HashMap<>();
        // Looping through all rows and adding to list
        if (listItemsCursor.moveToFirst()) {
            do {
                // Adding item to the map of items.
                itemsOnList.put(listItemsCursor.getString(0), listItemsCursor.getString(1));
            } while (listItemsCursor.moveToNext());
        }
        listItemsCursor.close();
        listObject.setListItems(itemsOnList);

        return listObject;

    }

    public String generateID() {
        ArrayList<String> listIDs = new ArrayList<String>(getAllListIDs());
        int listSize = listIDs.size();
        String id;
        for(int i=0; i<listSize; i++) {
            id = "'" + i + "'";
            if (!listIDs.get(i).equals(id)) {
                return id;
            }
        }
        return "'" + listSize + "'";
    }

    public boolean isNameInUse(String inputID, String name) {
        ArrayList<String> listNames = new ArrayList<String>(getAllListNames());
        String IDFromDB = getListID(name);
        if (listNames.contains(name)) {
            if (IDFromDB.equals(inputID)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
