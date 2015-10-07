package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.graphics.Bitmap;

/**
 * Created by Trong Nghia on 9/22/2015.
 */
public class Ship_Fragment_View_List_Item {
    private Bitmap ads_img;
    private String ads_type;
    private String price;
    private String title;
    private String time;
    private String location;
    private String status;



    public Ship_Fragment_View_List_Item(Bitmap ads_img, String title, String price,
                                          String time, String location) {

        super();
        this.ads_img = ads_img;
        //this.ads_type = ads_type;
        this.price = price;
        this.title = title;
        this.time = time;
        this.location = location;
        //this.status = status;
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
