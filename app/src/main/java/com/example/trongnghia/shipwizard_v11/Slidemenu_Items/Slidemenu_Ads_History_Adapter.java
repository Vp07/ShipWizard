package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trongnghia.shipwizard_v11.R;

import java.util.ArrayList;

/**
 * Created by Trong Nghia on 9/18/2015.
 */
public class Slidemenu_Ads_History_Adapter extends ArrayAdapter<Slidemenu_Ads_History_Items> {
    private final Context context;
    private final ArrayList<Slidemenu_Ads_History_Items> itemsArrayList;

    public Slidemenu_Ads_History_Adapter(Context context, ArrayList<Slidemenu_Ads_History_Items> itemsArrayList)
    {
        super(context, R.layout.slidemenu_ads_history_list_item, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.slidemenu_ads_history_list_item, parent, false);
        // 3. Get the two text view from the rowView
        ImageView img = (ImageView)rowView.findViewById(R.id.ivAds_Img);
        TextView ads_type = (TextView) rowView.findViewById(R.id.tvOrder_Ship);
        TextView price = (TextView) rowView.findViewById(R.id.tvPrice);
        TextView title = (TextView) rowView.findViewById(R.id.tvTitle);
        TextView time = (TextView) rowView.findViewById(R.id.tvTime);
        TextView location = (TextView) rowView.findViewById(R.id.tvLocation);
        TextView status = (TextView) rowView.findViewById(R.id.tvStatus);

        // 4. Set the text for textView
        img.setImageBitmap(itemsArrayList.get(position).getAds_img());
        ads_type.setText(itemsArrayList.get(position).getAds_type());
        price.setText(itemsArrayList.get(position).getPrice());
        title.setText(itemsArrayList.get(position).getTitle());
        time.setText(itemsArrayList.get(position).getTime());
        location.setText(itemsArrayList.get(position).getLocation());
        status.setText(itemsArrayList.get(position).getStatus());
        // 5. retrn rowView
        return rowView;
    }
}
