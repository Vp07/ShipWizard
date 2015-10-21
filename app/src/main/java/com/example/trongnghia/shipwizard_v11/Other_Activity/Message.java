package com.example.trongnghia.shipwizard_v11.Other_Activity;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Trong Nghia on 9/23/2015.
 */
@ParseClassName("Message")
public class Message extends ParseObject {

    public String text;
    public String sender;

    public Message(){
    };

    public Message(String content, String UserID){
        this.text = content;
        this.sender = UserID;
    }

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

    public String getContent(String text) {
        return text;
    }

    public void setContent(String text) {
        this.text = text;
    }

    public List<String> getContentList() {
        return getList("Content");
    }

    public void setContentList(List<String> Content) {
        put("Content", Content);
    }


    public void setSenderList(List<String> Sender) {
        put("Sender", Sender);
    }

    public List<String> getSender() {
        return getList("Sender");
    }

    public void setSender(String Sender) {
        this.sender = Sender;
    }

    public void setConnectionString(String ConnectString) {
        put("Connection", ConnectString);
    }

    public void setAdsID(String AdsID) {
        put("AdsID", AdsID);
    }

    public void setAdsTitle(String title) {
        put("AdsTitle", title);
    }
}
