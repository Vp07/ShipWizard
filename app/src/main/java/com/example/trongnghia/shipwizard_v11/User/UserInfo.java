package com.example.trongnghia.shipwizard_v11.User;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.LogIn.DispatchActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Trong Nghia on 9/9/2015.
 */
public class
        UserInfo {

    public UserInfo user;
    public static String userID;
    public static String username;
    public static String email;
    public static ParseFile avatar;
    public static String objectID;
    //current_user = ParseUser.getCurrentUser();

    public UserInfo(){

        this.userID = ParseUser.getCurrentUser().getObjectId();
        this.username = ParseUser.getCurrentUser().getUsername();
        this.email = ParseUser.getCurrentUser().getEmail();
       // this.avatar = ParseUser.getCurrentUser().getParseFile("img");
    }



    public ParseFile getAvatar(){
        return ParseUser.getCurrentUser().getParseFile("img");
    }
    public void setAvatar(ParseFile file){
        ParseUser.getCurrentUser().put("img", file);
    }

    public void PrepareForUpload(String string, ParseFile file){
        ParseUser.getCurrentUser().put(string, file);
    }
    public void PutInfo(String Column, String Value){
        ParseUser.getCurrentUser().put(Column,Value);
    }
    public void UploadtoParse(){
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("check", "save success");
                } else {
                    Log.d("check", "save fail");
                }
            }
        });
    }

    public String getObjectID(String objectID){
        this.objectID = objectID;
        return this.objectID;
    }

}
