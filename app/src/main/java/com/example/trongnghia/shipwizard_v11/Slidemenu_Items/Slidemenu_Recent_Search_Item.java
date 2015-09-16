package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

/**
 * Created by Trong Nghia on 9/16/2015.
 */
public class Slidemenu_Recent_Search_Item {
    private String ads_type;
    private String category;
    private String time;
    private String location;

    public Slidemenu_Recent_Search_Item(String ads_type, String category,
                                        String time, String location) {
        super();
        this.ads_type = ads_type;
        this.category = category;
        this.time = time;
        this.location = location;
    }

    public String getAds_type(){
        return ads_type;
    }

    public void setAds_type(String ads_type){
        this.ads_type = ads_type;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

}
