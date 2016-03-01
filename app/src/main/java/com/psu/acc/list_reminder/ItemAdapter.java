package com.psu.acc.list_reminder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
    ListView listView;
    private static LayoutInflater inflater = null;

    public ItemAdapter(Context context, ListView listView, ArrayList<String> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        this.listView = listView;
        inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        final RelativeLayout relLayout = (RelativeLayout) vi.findViewById(R.id.rel_layout);
        relLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });
        final ImageButton deleteItemButton = (ImageButton) vi.findViewById(R.id.delete_item_button);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            // delete item from
            @Override
            public void onClick(View view) {
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        return vi;
    }

    public void addItem(String name) {
        data.add(name);
    }
}
