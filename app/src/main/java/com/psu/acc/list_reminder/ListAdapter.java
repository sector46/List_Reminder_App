package com.psu.acc.list_reminder;

import android.app.Activity;
import android.content.Context;
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
 * Created by chandhnikannatintavida on 3/1/16.
 */
public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> data;
    private DatabaseHelper databaseHelper;
    private static LayoutInflater inflater = null;
    ListView listView;
    public String itemValue;



    public ListAdapter(Context context, ArrayList<String> data , DatabaseHelper db){
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listView = (ListView)((Activity)context).findViewById(R.id.item_list);
        databaseHelper =db;

    }

   // @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

   // @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

   // @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    //@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.list_item, null);
        final TextView text = (TextView) vi.findViewById(R.id.item_name);
        text.setText(data.get(position));
        final RelativeLayout relLayout = (RelativeLayout) vi.findViewById(R.id.rel_layout);
        relLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemValue = (String) data.get(position);
                //trial....
                ViewListActivity vl = new ViewListActivity();
                vl.ViewActivity(v.getContext(),data.get(position).toString());



            }
        });
        final ImageButton deleteItemButton = (ImageButton) vi.findViewById(R.id.delete_item_button);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            // delete item from
            @Override
            public void onClick(View view) {

                Log.d("data selected----+", data.get(position).toString());
                Log.d("position selected----+", String.valueOf(position));
                databaseHelper.removeList(data.get(position).toString());
                data.remove(position);
                notifyDataSetChanged();

            }
        });
        return vi;
    }


    public void addItem(String name) {
        data.add(name);
    }

    public void setVisible(ListView listView) {
        int listLength = getCount();
        RelativeLayout view;
        ImageButton button;
        for (int i=0; i<listLength; i++) {
            view = (RelativeLayout) listView.getChildAt(i);
            button = (ImageButton) view.findViewById(R.id.delete_item_button);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void setInvisible(ListView listView) {
        int listLength = getCount();
        RelativeLayout view;
        ImageButton button;
        for (int i=0; i<listLength; i++) {
            view = (RelativeLayout) listView.getChildAt(i);
            button = (ImageButton) view.findViewById(R.id.delete_item_button);
            button.setVisibility(View.INVISIBLE);
        }
    }


}
