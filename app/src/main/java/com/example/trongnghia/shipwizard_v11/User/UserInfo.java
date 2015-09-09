package com.example.trongnghia.shipwizard_v11.User;

import com.parse.ParseUser;

/**
 * Created by Trong Nghia on 9/9/2015.
 */
public class UserInfo {

    public ParseUser user;
    public static String userID;
    public static String username;
    public static String email;
    //current_user = ParseUser.getCurrentUser();

    public UserInfo(){
        user = ParseUser.getCurrentUser();
        this.userID = user.getObjectId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
