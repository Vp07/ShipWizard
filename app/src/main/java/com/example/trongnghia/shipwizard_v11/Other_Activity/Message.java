package com.example.trongnghia.shipwizard_v11.Other_Activity;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Trong Nghia on 9/23/2015.
 */
@ParseClassName("Message")
public class Message extends ParseObject {

    public String getFromUserID() {
        return getString("FromUserID");
    }

    public void setFromUserID(String FromUserID) {
        put("FromUserID", FromUserID);
    }

    public String getToUserID() {
        return getString("ToUserID");
    }

    public void setToUserID(String ToUserID) {
        put("ToUserID", ToUserID);
    }

    public String getFromUserName() {
        return getString("FromUserName");
    }

    public void setFromUsername(String FromUserName) {
        put("FromUserName", FromUserName);
    }

    public String getToUserName() {
        return getString("ToUserName");
    }

    public void setToUserName(String ToUserName) {
        put("ToUserName", ToUserName);
    }

    public String getContent() {
        return getString("Content");
    }

    public void setContent(String Content) {
        put("Content", Content);
    }

    public void setConnectionString(String ConnectString) {
        put("Connection", ConnectString);
    }
}
