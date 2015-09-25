package com.example.trongnghia.shipwizard_v11.Other_Activity;

import android.graphics.Bitmap;

/**
 * Created by Trong Nghia on 9/24/2015.
 */
public class Message_History_List_Item {

    private Bitmap ads_img;
    private String user;
    private String title;
    private String time;
    private String location;
    private String status;
    private String last_msg;

    public Message_History_List_Item(Bitmap ads_img, String title, String user,
                                          String time, String status, String last_msg) {

        super();
        this.ads_img = ads_img;
        this.user = user;
        this.title = title;
        this.time = time;
        this.status = status;
        this.last_msg = last_msg;
    }

    public Bitmap getAds_img(){ return ads_img; }

    public void setAds_img(Bitmap ads_img){ this.ads_img = ads_img; }

    public String getUser(){
        return user;
    }

    public void setUser(String price){
        this.user = user;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){ this.title = title; }

    public String getTime(){
        return time;
    }

    public void setTime(String time){ this.time = time; }


    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getLast_msg(){
        return last_msg;
    }

    public void setLast_msg(String last_msg){
        this.last_msg = last_msg;
    }

}
