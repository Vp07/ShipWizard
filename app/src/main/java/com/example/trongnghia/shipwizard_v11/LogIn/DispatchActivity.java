package com.example.trongnghia.shipwizard_v11.LogIn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.trongnghia.shipwizard_v11.Library.Recent_search_saver;
import com.example.trongnghia.shipwizard_v11.NewTransaction.View_Transaction;
import com.example.trongnghia.shipwizard_v11.SlideMenu.MainActivity;
import com.example.trongnghia.shipwizard_v11.User.UserAction;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rufflez on 7/8/14.
 */
public class DispatchActivity extends Activity {

    // Just create a UserInfo object here and just call UserInfo.xxx at anywhere
    public static UserInfo current_user;
    public static UserAction bookmarks;
    public List<String> user_bookmarks = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Check if there is current user info
        ParseQuery<UserAction> query = ParseQuery.getQuery(UserAction.class);
        query.whereEqualTo("ABC", UserInfo.userID);
        query.findInBackground(new FindCallback<UserAction>() {
            public void done(List<UserAction> object, ParseException e) {
                if (e == null) {
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });

        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            current_user = new UserInfo();
            bookmarks = new UserAction();
            bookmarks.setUserID(UserInfo.userID);
            bookmarks.setAdsID(user_bookmarks);
            bookmarks.setAdsMessageList(user_bookmarks);
            bookmarks.saveInBackground();
            startActivity(new Intent(this, View_Transaction.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, SignUpOrLoginActivity.class));
        }
    }
}