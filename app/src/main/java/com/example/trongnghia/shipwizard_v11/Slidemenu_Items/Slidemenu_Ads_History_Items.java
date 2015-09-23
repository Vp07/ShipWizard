package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Trong Nghia on 9/18/2015.
 */
public class Slidemenu_Ads_History_Items {
    private Bitmap ads_img;
    private String ads_type;
    private String price;
    private String title;
    private String time;
    private String location;
    private String status;

    public Slidemenu_Ads_History_Items(Bitmap ads_img, String title, String price,
                                        String ads_type, String time, String location, String status) {
        super();
        this.ads_img = ads_img;
        this.ads_type = ads_type;
        this.price = price;
        this.title = title;
        this.time = time;
        this.location = location;
        this.status = status;
    }

    public Bitmap getAds_img(){ return ads_img; }

    public void setAds_img(Bitmap ads_img){ this.ads_img = ads_img; }

    public String getAds_type(){ return ads_type; }

    public void setAds_type(String ads_type){ this.ads_type = ads_type; }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){ this.title = title; }

    public String getTime(){
        return time;
    }

    public void setTime(String time){ this.time = time; }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
