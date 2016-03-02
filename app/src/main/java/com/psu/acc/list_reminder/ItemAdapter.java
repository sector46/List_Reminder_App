package com.psu.acc.list_reminder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
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
    ArrayList<String> strike;
    private static LayoutInflater inflater = null;
    ListView listView;

    public ItemAdapter(Context context, ArrayList<String> data, ArrayList<String> strike) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        this.strike = strike;
        inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listView = (ListView)((Activity)context).findViewById(R.id.item_list);
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
                if (strike.get(position) == "false") {
                    relLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                    text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    strike.set(position, "true");
                } else {
                    relLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
                    text.setPaintFlags(text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    strike.set(position, "false");
                }
            }
        });
        final ImageButton deleteItemButton = (ImageButton) vi.findViewById(R.id.delete_item_button);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            // delete item from
            @Override
            public void onClick(View view) {
                data.remove(position);
                strike.remove(position);
                notifyDataSetChanged();
                updateViews(position);
            }
        });
        return vi;
    }

    private void updateViews(int index) {
        if (index < getCount()) {
            RelativeLayout relLayout;
            TextView text;
            for(int i=index; i<getCount(); i++) {
                relLayout = (RelativeLayout)listView.getChildAt(i);
                text = (TextView) relLayout.findViewById(R.id.item_name);
                if (strike.get(i) == "true") {
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
