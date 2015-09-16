package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.trongnghia.shipwizard_v11.R;

/**
 * Created by Trong Nghia on 9/16/2015.
 */
public class Slidemenu_Recent_Search_Adapter extends ArrayAdapter<Slidemenu_Recent_Search_Item>
{
    private final Context context;
    private final ArrayList<Slidemenu_Recent_Search_Item> itemsArrayList;

    public Slidemenu_Recent_Search_Adapter(Context context, ArrayList<Slidemenu_Recent_Search_Item> itemsArrayList)
    {
        super(context, R.layout.slidemenu_recent_search_list_item, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.slidemenu_recent_search_list_item, parent, false);
        // 3. Get the two text view from the rowView
        TextView title = (TextView) rowView.findViewById(R.id.tvTitle);
        TextView body1 = (TextView) rowView.findViewById(R.id.tvBody1);
        TextView body2 = (TextView) rowView.findViewById(R.id.tvBody2);
        TextView end = (TextView) rowView.findViewById(R.id.tvEnding);
        // 4. Set the text for textView
        title.setText(itemsArrayList.get(position).getAds_type());
        body1.setText(itemsArrayList.get(position).getCategory());
        body2.setText(itemsArrayList.get(position).getTime());
        end.setText(itemsArrayList.get(position).getLocation());
        // 5. retrn rowView
        return rowView;
    }
}
