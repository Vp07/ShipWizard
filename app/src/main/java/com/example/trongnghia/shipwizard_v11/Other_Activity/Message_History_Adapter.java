package com.example.trongnghia.shipwizard_v11.Other_Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trongnghia.shipwizard_v11.NewTransaction.Order_Fragment_View_List_Items;
import com.example.trongnghia.shipwizard_v11.R;

import java.util.ArrayList;

/**
 * Created by Trong Nghia on 9/24/2015.
 */
public class Message_History_Adapter extends ArrayAdapter<Message_History_List_Item> {

    private final Context context;
    private final ArrayList<Message_History_List_Item> itemsArrayList;

    public Message_History_Adapter(Context context, ArrayList<Message_History_List_Item> itemsArrayList)
    {
        super(context, R.layout.slidemenu_message_list_item, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.slidemenu_message_list_item, parent, false);
        // 3. Get the two text view from the rowView
        ImageView img = (ImageView)rowView.findViewById(R.id.ivAds_Img);
        TextView title = (TextView) rowView.findViewById(R.id.tvTitle);
        TextView time = (TextView) rowView.findViewById(R.id.tvTime);
        TextView user = (TextView) rowView.findViewById(R.id.tvUser);
        TextView last_message = (TextView) rowView.findViewById(R.id.tvLast_Message);
        TextView status = (TextView) rowView.findViewById(R.id.tvStatus);

        // 4. Set the text for textView
        img.setImageBitmap(itemsArrayList.get(position).getAds_img());
        title.setText(itemsArrayList.get(position).getTitle());
        time.setText(itemsArrayList.get(position).getTime());
        user.setText(itemsArrayList.get(position).getUser());
        last_message.setText(itemsArrayList.get(position).getLast_msg());
        status.setText(itemsArrayList.get(position).getStatus());
        // 5. retrn rowView
        return rowView;
    }
}
