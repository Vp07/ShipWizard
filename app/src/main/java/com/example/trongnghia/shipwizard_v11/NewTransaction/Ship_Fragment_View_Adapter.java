package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trongnghia.shipwizard_v11.R;

import java.util.ArrayList;

/**
 * Created by Trong Nghia on 9/22/2015.
 */
public class Ship_Fragment_View_Adapter extends BaseAdapter {


    private final Context context;
    public Bitmap[] bm = new Bitmap[]{null} ;
    public String[] price;
    public String[] title;
    public String[] time;
    public String[] location;


    public Ship_Fragment_View_Adapter(Context context,
                                       Bitmap[] bm,
                                       String[] title,
                                       String[] time,
                                       String[] location,
                                       String[] price)
    {
        this.context = context;
        this.bm = bm;
        this.price = price;
        this.title = title;
        this.time = time;
        this.location = location;

    }

    @Override
    public int getCount() {
        int setCount=0;
        for (int i= 0; i<=100;i++){
            if (title[i]==null){
                setCount = i;
                break;
            }
        }
        return setCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.home_list_item, parent, false);
        // 3. Get the two text view from the rowView
        ImageView img = (ImageView)rowView.findViewById(R.id.ivAds_Img);
        //TextView ads_type = (TextView) rowView.findViewById(R.id.tvOrder_Ship);
        TextView tvPrice = (TextView) rowView.findViewById(R.id.tvPrice);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        TextView tvTime = (TextView) rowView.findViewById(R.id.tvTime);
        TextView tvLocation = (TextView) rowView.findViewById(R.id.tvLocation);
        //TextView status = (TextView) rowView.findViewById(R.id.tvStatus);

        // 4. Set the text for textView
        if (bm[position] == null) {
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            img.setImageResource(R.drawable.ic_photo_camera_white_24dp);
            img.setBackgroundColor(Color.BLUE);

        } else {
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            img.setImageBitmap(bm[position]);
            img.setBackgroundColor(Color.GRAY);
        }
        //ads_type.setText(itemsArrayList.get(position).getAds_type());
        tvPrice.setText(price[position]);
        tvTitle.setText(title[position]);
        tvTime.setText(time[position]);
        tvLocation.setText(location[position]);
        //status.setText(itemsArrayList.get(position).getStatus());
        // 5. retrn rowView
        return rowView;
    }
    public void clear() {
        for (int i=0;i< bm.length;i++){
            bm[i] = null;
            title[i]=null;
            price[i]=null;
            time[i]=null;
            location[i]=null;
        }
    }
}
