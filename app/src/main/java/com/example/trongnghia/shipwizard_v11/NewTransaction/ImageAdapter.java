package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.trongnghia.shipwizard_v11.R;

import static com.example.trongnghia.shipwizard_v11.R.color.Color_1;

/**
 * Created by HIEP on 9/23/2015.
 */
public class ImageAdapter extends BaseAdapter
{
    public int Item_height;
    public int Item_width;
    ImageView imageView;
    public Bitmap bm[] = {null,null,null,null,null,null,null,null,null,};
    Integer empty_slot[]={
            R.drawable.ic_photo_camera_white_24dp,
            R.drawable.ic_photo_camera_white_24dp,
            R.drawable.ic_photo_camera_white_24dp,
            R.drawable.ic_photo_camera_white_24dp,
            R.drawable.ic_photo_camera_white_24dp,
            R.drawable.ic_photo_camera_white_24dp,
            R.drawable.ic_photo_camera_white_24dp,
            R.drawable.ic_photo_camera_white_24dp,
            R.drawable.ic_photo_camera_white_24dp,
    };
    private Context context;
    public ImageAdapter(Context c)
    {
        context = c;
    }
    //---returns the number of images---
    public int getCount()
    {
        return empty_slot.length;
    }
    //---returns the ID of an item---
    public Object getItem(int position)
    {
        return position;
    }
    //---returns the ID of an item---
    public long getItemId(int position)
    {
        return position;
    }
    //---returns an ImageView view---
    public View getView(int position, View convertView,ViewGroup parent)
    {


        Item_height = parent.getWidth() / 3 - 4;
        Item_width = parent.getWidth() / 3 - 4;

            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth() / 3 - 4, parent.getWidth() / 3 - 4));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setBackgroundColor(Color.BLUE);
                imageView.setPadding(5, 5, 5, 5);

            } else {
                imageView = (ImageView) convertView;
                imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth() / 3 - 4, parent.getWidth() / 3 - 4));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setBackgroundColor(Color.BLUE);
                imageView.setPadding(5, 5, 5, 5);
            }

        if(bm[position]!=null)
        imageView.setImageBitmap(bm[position]);
        else
        imageView.setImageResource(empty_slot[position]);

        return imageView;
    }

}
