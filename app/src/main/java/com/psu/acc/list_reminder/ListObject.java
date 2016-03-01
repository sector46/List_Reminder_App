package com.psu.acc.list_reminder;

import java.util.Map;

/**
 * Created by caseybowman on 2/15/16.
 */
public class ListObject {
    //Name of the list
    private String listName;
    // Value for the key is "true" if item is struck off list and "false" by default.
    private Map<String, String> listItems;
    // Date and time selected for the reminder.
    private String reminderDateTime;
    // Recurrence (none, hourly, weekly, monthly)
    private String reminderRecurrence;
    //True when reminder is enabled, false when disabled.
    private String reminderEnabled;

    ListObject() {
        this.listName = "New List";
    }

    /**
     * Constructor.
     * @param listName name of the list
     * @param listItems map containing item names and their status
     * @param reminderDateTime date and time of the reminder
     * @param reminderRecurrence recurrence set for the reminder
     * @param reminderEnabled is reminder enabled
     */
    ListObject(String listName, Map<String, String> listItems, String reminderDateTime, String reminderRecurrence, String reminderEnabled) {
        this.listName = listName;
        this.listItems = listItems;
        this.reminderDateTime = reminderDateTime;
        this.reminderRecurrence = reminderRecurrence;
        this.reminderEnabled = reminderEnabled;
    }

    // for importing lists
    ListObject(ListObject list) {
        this.listName = list.getListName();
        this.listItems = list.getListItems();
        this.reminderDateTime = list.getReminderDateTime();
        this.reminderRecurrence = list.getReminderRecurrence();
        this.reminderEnabled = list.getReminderEnabled();
    }

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

    //getter and setter for the date and time set for the reminder
    public String getReminderDateTime() {
        return reminderDateTime;
    }

    public void setReminderDateTime(String reminderDateTime) {
        this.reminderDateTime = reminderDateTime;
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

    //Displays the list, just an sop for now, to be modified accordingly
    public void displayList() {
        System.out.println("\tListName: " + this.listName + "\n\tReminder DateTime: " + this.reminderDateTime +
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
