package com.example.trongnghia.shipwizard_v11.LogIn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.trongnghia.shipwizard_v11.NewTransaction.View_Transaction;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.ParseUser;

/**
 * Created by rufflez on 7/8/14.
 */
public class DispatchActivity extends Activity {

    // Just create a UserInfo object here and just call UserInfo.xxx at anywhere
    public static UserInfo current_user;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            current_user = new UserInfo();
            startActivity(new Intent(this, View_Transaction.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, SignUpOrLoginActivity.class));
        }
    }
}