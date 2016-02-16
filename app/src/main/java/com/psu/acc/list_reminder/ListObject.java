package com.psu.acc.list_reminder;

import java.util.ArrayList;

/**
 * Created by caseybowman on 2/15/16.
 */
public class ListObject {
    private String listName;
    private ArrayList list;

    ListObject() {
        this.listName = "New List";

    }

    ListObject(String listName, ArrayList list) {
        this.listName = listName;
        this.list = list;
    }

    // for importing lists
    ListObject(ListObject list) {
        this.listName = list.getListName();
        this.list = list.getList();
    }

    void setListName(String listName) {
        this.listName = listName;
    }

    String getListName() {
        return this.listName;
    }

    boolean addItemToList(String name) {
        if (this.list.contains(name)) {
            return false;
        } else {
            this.list.add(name);
            return true;
        }
    }

    boolean removeItemFromList(String name) {
        if(this.list.contains(name)) {
            this.list.remove(name);
            return true;
        } else {
            return false;
        }
    }

    ArrayList getList() {
        return this.list;
    }
}
