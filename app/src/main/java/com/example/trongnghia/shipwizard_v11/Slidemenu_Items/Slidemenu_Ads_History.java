package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.Library.TinyDB;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.example.trongnghia.shipwizard_v11.User.User_Post_Querry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Type;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Slidemenu_Ads_History extends Fragment {

    List<Group> groupsList;

    public static ArrayList<Slidemenu_Ads_History_Items> items;
    public ArrayList<Slidemenu_Ads_History_Items> array;
    public ArrayList<String> recent_search_string;
    public String postObject = "OrderPost";
    public String UserIDCol = "UserID";

    public Slidemenu_Ads_History_Items temp;

    public User_Post_Querry objectList;
    public List<ParseObject> postList;
    public ParseObject temp_object;

    public Bitmap bitmap;

    public int test = 0;
    public View view;

    public static Slidemenu_Ads_History newInstance(){
        Slidemenu_Ads_History fragment = new Slidemenu_Ads_History();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.slidemenu_ads_history, container, false);
        items = new ArrayList<Slidemenu_Ads_History_Items>();

//        objectList = new User_Post_Querry();
//        objectList.getUserPost();
//        Toast.makeText(getActivity(), Integer.toString(objectList.postList.size()), Toast.LENGTH_SHORT).show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("OrderPost");
        query.whereEqualTo("UserID", UserInfo.userID);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + objectList.size() + " scores");
                    setListItem(objectList);
                    test = 100;
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        //Toast.makeText(getActivity(), Integer.toString(test), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), Integer.toString(postList.size()), Toast.LENGTH_SHORT).show();

        // Pass context and data to the custom adapter
//        Slidemenu_Ads_History_Adapter adapter = new Slidemenu_Ads_History_Adapter(getActivity(), generateData());
//        // Get ListView from activity_main.xml
//        ListView listView = (ListView) view.findViewById(R.id.lvAds_history);
//        // SetListAdapter
//        listView.setAdapter(adapter);
        return view;
    }

    private ArrayList<Slidemenu_Ads_History_Items> generateData(List<ParseObject> objectList){
        //Toast.makeText(getActivity(), Integer.toString(postList.size()), Toast.LENGTH_SHORT).show();
        for (int i=0; i<objectList.size(); i++){
            temp_object = objectList.get(i);
            ParseFile img_file = temp_object.getParseFile("img");

//
            items.add(new Slidemenu_Ads_History_Items(bitmap,
                                                      temp_object.getString("Title"),
                                                      temp_object.getString("Price"),
                                                      "Order",
                                                      temp_object.getString("Time"),
                                                      temp_object.getString("Buyer_place"),
                                                      "status"));
        }
        return items;
    }

    public void getAds(List<ParseObject> objectList){
        this.postList = objectList;
        //Toast.makeText(getActivity(), Integer.toString(postList.size()), Toast.LENGTH_SHORT).show();
    }

    public void setListItem(List<ParseObject> objectList){
        // Pass context and data to the custom adapter
        Slidemenu_Ads_History_Adapter adapter = new Slidemenu_Ads_History_Adapter(getActivity(), generateData(objectList));
        // Get ListView from activity_main.xml
        ListView listView = (ListView) view.findViewById(R.id.lvAds_history);
        // SetListAdapter
        listView.setAdapter(adapter);
    }
}
