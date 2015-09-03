package com.example.trongnghia.shipwizard_v11;

import android.app.Application;

import com.example.trongnghia.shipwizard_v11.R;
import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
/**
 * Created by rufflez on 7/8/14.
 */
public class SampleApplication extends Application {

    static final String TAG = "MyApp";

    public void onCreate(){
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_id));
        ParseFacebookUtils.initialize(this);
    }
}
