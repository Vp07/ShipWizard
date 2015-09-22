package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.Library.TinyDB;
import com.example.trongnghia.shipwizard_v11.NewTransaction.View_Transaction;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Ads_view;
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

public class Slidemenu_Ads_History extends Fragment{

    ListView ads;

    public static ArrayList<Slidemenu_Ads_History_Items> items;
    public ArrayList<Slidemenu_Ads_History_Items> array;
    public ArrayList<String> recent_search_string;
    public String postObject = "UserPost";
    public String UserIDCol = "UserID";

    public Slidemenu_Ads_History_Items temp;

    public User_Post_Querry objectList;
    public List<ParseObject> postList;
    public ParseObject temp_object;

    public Bitmap bitmap;

    public View view;

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    public UserInfo user;

    public Slidemenu_Ads_History_Adapter adapter;
    public ListView listView;
    public static ParseQuery<ParseObject> query;

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
        user = new UserInfo();

        listview_init();
        //Toast.makeText(getActivity(), Integer.toString(te), Toast.LENGTH_LONG).show();
        return view;
    }

    public void listview_init(){
        query = ParseQuery.getQuery("UserPost");
        query.whereEqualTo("UserID", UserInfo.userID);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    setListItem(objectList);
                } else {
                }
            }
        });
    }

    private ArrayList<Slidemenu_Ads_History_Items> generateData(List<ParseObject> objectList){
        //Toast.makeText(getActivity(), Integer.toString(postList.size()), Toast.LENGTH_SHORT).show();
        for (int i=0; i<objectList.size(); i++){
            temp_object = objectList.get(i);

            // Get image file from Parse object
            ParseFile img_file = temp_object.getParseFile("img");
            // Hiep -> Do something to get bitmap data from img_file



            items.add(new Slidemenu_Ads_History_Items(bitmap,
                                                      temp_object.getString("Title"),
                                                      temp_object.getString("Price"),
                                                      temp_object.getString("Ads_Type"),
                                                      temp_object.getString("Time"),
                                                      temp_object.getString("Buyer_place"),
                                                      temp_object.getString("Status")));
        }
        return items;
    }

    public void getAds(List<ParseObject> objectList){
        this.postList = objectList;
    }

    public void setListItem(final List<ParseObject> objectList){
        // Pass context and data to the custom adapter
        adapter = new Slidemenu_Ads_History_Adapter(getActivity(), generateData(objectList));
        // Get ListView from activity_main.xml
        listView = (ListView) view.findViewById(R.id.lvAds_history);
        // SetListAdapter
        listView.setAdapter(adapter);

        // React to user clicks on item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
                // We know the View is a TextView so we can cast it
                Intent intent = new Intent(getActivity(), Ads_view.class);
                intent.putExtra("Title", objectList.get(position).getString("Title"));
                intent.putExtra("Price", objectList.get(position).getString("Price"));
                intent.putExtra("Time", objectList.get(position).getString("Time"));
                intent.putExtra("Item", objectList.get(position).getString("Item"));
                intent.putExtra("Description", objectList.get(position).getString("Description"));
                intent.putExtra("Ads_Type", objectList.get(position).getString("Ads_Type"));
                intent.putExtra("Buyer_place", objectList.get(position).getString("Buyer_place"));
                intent.putExtra("ObjectID", objectList.get(position).getObjectId().toString());
                intent.putExtra("ObjectClass", objectList.get(position).getClassName().toString());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            items.clear();
            if(null!=data)
            {
                adapter.clear();
                listView.setAdapter(adapter);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            }
        }
    }

}
