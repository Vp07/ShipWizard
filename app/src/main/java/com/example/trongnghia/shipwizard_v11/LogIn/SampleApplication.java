package com.example.trongnghia.shipwizard_v11.LogIn;

import android.app.Application;

import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Recent_Search_Item;
import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

import java.util.ArrayList;

/**
 * Created by rufflez on 7/8/14.
 */
public class SampleApplication extends Application {

    static final String TAG = "MyApp";
    private ArrayList<Slidemenu_Recent_Search_Item> search_array;

    public void onCreate(){
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_id));
        ParseFacebookUtils.initialize(this);
    }

    public void set_items(Slidemenu_Recent_Search_Item item){
        search_array.add(item);
        //search_string = gson.toJson(search_array);
        //return search_array;
    }

    public ArrayList<Slidemenu_Recent_Search_Item> get_items() {
        return search_array;
    }
}
