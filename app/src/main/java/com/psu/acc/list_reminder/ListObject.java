package com.psu.acc.list_reminder;

import java.util.Map;

/**
 * Created by caseybowman on 2/15/16.
 */
public class ListObject {

    //ID of the list
    private String listID;
    //Name of the list
    private String listName;
    // Value for the key is "true" if item is struck off list and "false" by default.
    private Map<String, String> listItems;
    // Date and time selected for the reminder.
    private String reminderDate;
    // Time selected for the reminder.
    private String reminderTime;
    // Recurrence (none, hourly, weekly, monthly)
    private String reminderRecurrence;
    //True when reminder is enabled, false when disabled.
    private String reminderEnabled;

    ListObject() {
        this.listName = "New List";
    }

    /**
     * Constructor.
     * @param listID ID of the list
     * @param listName name of the list
     * @param listItems map containing item names and their status
     * @param reminderDate date of the reminder
     * @param reminderTime time of the reminder
     * @param reminderRecurrence recurrence set for the reminder
     * @param reminderEnabled is reminder enabled
     */
    ListObject(String listID, String listName, Map<String, String> listItems, String reminderDate, String reminderTime, String reminderRecurrence, String reminderEnabled) {
        this.listID = listID;
        this.listName = listName;
        this.listItems = listItems;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
        this.reminderRecurrence = reminderRecurrence;
        this.reminderEnabled = reminderEnabled;
    }

    // for importing lists
    ListObject(ListObject list) {
        this.listID = list.getListID();
        this.listName = list.getListName();
        this.listItems = list.getListItems();
        this.reminderDate = list.getReminderDate();
        this.reminderTime = list.getReminderTime();
        this.reminderRecurrence = list.getReminderRecurrence();
        this.reminderEnabled = list.getReminderEnabled();
    }

    //Getting and setting listID
    public String getListID() { return listID; }

    public void setListID(String listID) { this.listID = listID; }

    //Getter and setter for list name
    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    //getter and setter for all items on the list
    public Map<String, String> getListItems() {
        return listItems;
    }

    public void setListItems(Map<String, String> listItems) {
        this.listItems = listItems;
    }

    //getter and setter for the date set for the reminder
    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    //getter and setter for the time set for the reminder
    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderDateTime) {
        this.reminderTime = reminderDateTime;
    }

    //getter and setter for recurrence
    public String getReminderRecurrence() {
        return reminderRecurrence;
    }

    public void setReminderRecurrence(String reminderRecurrence) {
        this.reminderRecurrence = reminderRecurrence;
    }

    //getter and setter to enable/disable reminder
    public String getReminderEnabled() {
        return reminderEnabled;
    }

    public void setReminderEnabled(String reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
    }

    //get count of items in list
    public int getCount() {
        return this.getListItems().entrySet().size();
    }

    //Displays the list, just an sop for now, to be modified accordingly
    public void displayList() {
        System.out.println("\tListName: " + this.listName + "\n\tReminder Date: " + this.reminderDate + "\n\tReminder Time: " + this.reminderTime +
                "\n\tRecurring: " + reminderRecurrence + "\n\tReminder enabled?: " + this.reminderEnabled + "\n\tItems:");
        for(String key : this.listItems.keySet())
            System.out.println("\t\t" + key + "\t" + this.listItems.get(key));
    }

    //Below are the methods added long back, not sure if they could be re-used, can remove it if unnecessary
    boolean addItemToList(String name) {
        if (this.listItems.containsKey(name)) {
            return false;
        } else {
            this.listItems.put(name, "false");
            return true;
        }
    }

    boolean removeItemFromList(String name) {
        if(this.listItems.containsKey(name)) {
            this.listItems.remove(name);
            return true;
        } else {
            return false;
        }
    }

    boolean strikeItemFromList(String name) {
        if(this.listItems.containsKey(name)) {
            this.listItems.put(name, "true");
            return true;
        } else {
            return false;
        }
    }
}
