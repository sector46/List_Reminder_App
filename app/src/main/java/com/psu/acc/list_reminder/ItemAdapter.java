package com.psu.acc.list_reminder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by caseybowman on 2/28/16.
 */
public class ItemAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> data;
    ArrayList<String> strikes;
    private static LayoutInflater inflater = null;
    ListView listView;
    DatabaseHelper db;
    ListObject list;

    public ItemAdapter(Context context, ListObject list, ArrayList<String> data, ArrayList<String> strikes, DatabaseHelper db) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
        this.data = data;
        this.strikes = strikes;
        inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listView = (ListView)((Activity)context).findViewById(R.id.item_list);
        this.db = db;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.list_item, null);
        final TextView text = (TextView) vi.findViewById(R.id.item_name);
        text.setText(data.get(position));
        Log.i("Position", Integer.toString(position));
        final RelativeLayout relLayout = (RelativeLayout) vi.findViewById(R.id.rel_layout);
        if (strikes.get(position).equals("true")) {
            relLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            relLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
            text.setPaintFlags(text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        relLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strikes.get(position).equals("false")) {
                    relLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                    text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    strikes.set(position, "true");
                    db.updateStrike(list.getListID(), data.get(position), "true");
                    Log.i("strikes-True: ", strikes.get(position));
                    getItemLogs();
                } else {
                    relLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
                    text.setPaintFlags(text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    strikes.set(position, "false");
                    db.updateStrike(list.getListID(), data.get(position), "false");
                    Log.i("strikes-False: ", strikes.get(position));
                    getItemLogs();
                }
            }
        });
        if (ViewListActivity.getEditMode() == true) {
            relLayout.setClickable(false);
            ImageButton button = (ImageButton) relLayout.findViewById(R.id.delete_item_button);
            button.setVisibility(View.VISIBLE);
        } else {
            relLayout.setClickable(true);
            ImageButton button = (ImageButton) relLayout.findViewById(R.id.delete_item_button);
            button.setVisibility(View.INVISIBLE);
        }
        final ImageButton deleteItemButton = (ImageButton) vi.findViewById(R.id.delete_item_button);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            // delete item from list
            @Override
            public void onClick(View view) {
                data.remove(position);
                strikes.remove(position);
                notifyDataSetChanged();
                updateViews();
                Log.i("layouts = ", Integer.toString(listView.getCount()));
            }
        });
        return vi;
    }

    public void updateViews() {
        RelativeLayout relLayout;
        TextView text;
        for(int i=0; i<getCount(); i++) {
            relLayout = (RelativeLayout)listView.getChildAt(i);
            if (relLayout != null) {
                text = (TextView) relLayout.findViewById(R.id.item_name);
                if (strikes.get(i).equals("true")) {
                    relLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                    text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    relLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
                    text.setPaintFlags(text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        }
    }

    public void addItem(String name) {
        data.add(name);
        strikes.add("false");
        notifyDataSetChanged();
    }

    public void setVisible() {
        int listLength = listView.getChildCount(); //getCount();
        RelativeLayout view;
        ImageButton button;
        for (int i=0; i<listLength; i++) {
            view = (RelativeLayout) listView.getChildAt(i);
            button = (ImageButton) view.findViewById(R.id.delete_item_button);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void setInvisible() {
        int listLength = listView.getChildCount(); //getCount();
        RelativeLayout view;
        ImageButton button;
        for (int i=0; i<listLength; i++) {
            view = (RelativeLayout) listView.getChildAt(i);
            button = (ImageButton) view.findViewById(R.id.delete_item_button);
            button.setVisibility(View.INVISIBLE);
        }
    }

    public void setUnclickable() {
        int listLength = listView.getChildCount(); //getCount();
        RelativeLayout view;
        for (int i=0; i<listLength; i++) {
            view = (RelativeLayout) listView.getChildAt(i);
            view.setClickable(false);
        }
    }

    public void setClickable() {
        int listLength = listView.getChildCount(); //getCount();
        RelativeLayout view;
        for (int i=0; i<listLength; i++) {
            view = (RelativeLayout) listView.getChildAt(i);
            view.setClickable(true);
        }
    }

    public ArrayList<String> getNames() {
        return data;
    }

    public ArrayList<String> getStrikes() {
        return strikes;
    }

    public void getItemLogs() {
        for (String item: data) {
            Log.i("data ", "= " + item);
        }
        for (String item:strikes) {
            Log.i("strikes ", "= " + item);
        }
    }

}
