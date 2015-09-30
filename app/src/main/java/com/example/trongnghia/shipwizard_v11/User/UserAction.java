package com.example.trongnghia.shipwizard_v11.User;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trong Nghia on 9/28/2015.
 */
@ParseClassName("UserAction")
public class UserAction extends ParseObject {

    public static List<String> message_ads = new ArrayList<>();

    public String getUserID() {
        return getString("UserID");
    }

    public void setUserID(String UserID) {
        put("UserID", UserID);
    }

    public List<String> getAdsID() {
        return getList("AdsID");
    }

    public void setAdsID(List<String> AdsID) {
        put("AdsID", AdsID);
    }

    public Boolean getCheckFlag() {
        return getBoolean("CheckFlag");
    }

    public void setCheckFlag(Boolean flag) {
        put("CheckFlag", flag);
    }

    public void getAdsMessageList(String AdsID){
        this.message_ads.add(AdsID);
    }

    public void setAdsMessageList(List<String> AdsID) {
        put("AdsIDMessage", AdsID);
    }

}
