package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.Library.TinyDB;
import com.example.trongnghia.shipwizard_v11.NewTransaction.Order_Fragment_View_Adapter;
import com.example.trongnghia.shipwizard_v11.NewTransaction.Order_Fragment_View_List_Items;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Public_Ads_view;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class Slidemenu_Favorite_Ads extends Fragment {

    // Get list of favorite Ads
    public static TinyDB tinydb;
    public static ArrayList<String> favorite_ads_list;
    public static List<ParseQuery<ParseObject>> queries;
    public static ParseQuery<ParseObject> query;

    public static ArrayList<Order_Fragment_View_List_Items> items;
    public ArrayList<Order_Fragment_View_List_Items> array;
    public List<ParseObject> postList;
    public ParseObject temp_object;

    public Bitmap bitmap;

    public View view;

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    public UserInfo user;

    public Order_Fragment_View_Adapter adapter;
    public ListView listView;


    public static Slidemenu_Favorite_Ads newInstance(){
        Slidemenu_Favorite_Ads fragment = new Slidemenu_Favorite_Ads();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.slidemenu_favorite_ads_fragment, container, false);

        // Get list of favorite ads
        tinydb = new TinyDB(getActivity());
        favorite_ads_list = new ArrayList<String>();
        favorite_ads_list = tinydb.getListString("FavoriteAds");
        queries = new ArrayList<ParseQuery<ParseObject>>();

        items = new ArrayList<Order_Fragment_View_List_Items>();
        listview_init();
        //Toast.makeText(getActivity(), Integer.toString(te), Toast.LENGTH_LONG).show();
        return view;
    }

    public void listview_init(){

        for (int i=0; i<favorite_ads_list.size(); i++){
            query = ParseQuery.getQuery("UserPost");
            query.whereEqualTo("objectId", favorite_ads_list.get(i));
            queries.add(query);
        }

        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    setListItem(objectList);
                } else {
                }
            }
        });
    }

    private ArrayList<Order_Fragment_View_List_Items> generateData(List<ParseObject> objectList){
        //Toast.makeText(getActivity(), Integer.toString(postList.size()), Toast.LENGTH_SHORT).show();
        for (int i=0; i<objectList.size(); i++){
            temp_object = objectList.get(i);

            // Get image file from Parse object
            ParseFile img_file = temp_object.getParseFile("img");
            // Hiep -> Do something to get bitmap data from img_file

            items.add(new Order_Fragment_View_List_Items(bitmap,
                    temp_object.getString("Title"),
                    temp_object.getString("Price"),
                    temp_object.getString("Time"),
                    temp_object.getString("Buyer_place")));
        }
        return items;
    }

    public void getAds(List<ParseObject> objectList){
        this.postList = objectList;
    }

    public void setListItem(final List<ParseObject> objectList){
        // Pass context and data to the custom adapter
        adapter = new Order_Fragment_View_Adapter(getActivity(), generateData(objectList));
        // Get ListView from activity_main.xml
        listView = (ListView) view.findViewById(R.id.lvOrder_view);
        // SetListAdapter
        listView.setAdapter(adapter);

        // React to user clicks on item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
                // We know the View is a TextView so we can cast it
                Intent intent = new Intent(getActivity(), Public_Ads_view.class);
                intent.putExtra("Title", objectList.get(position).getString("Title"));
                intent.putExtra("Price", objectList.get(position).getString("Price"));
                intent.putExtra("Time", objectList.get(position).getString("Time"));
                intent.putExtra("Item", objectList.get(position).getString("Item"));
                intent.putExtra("Description", objectList.get(position).getString("Description"));
                intent.putExtra("Ads_Type", objectList.get(position).getString("Ads_Type"));
                intent.putExtra("Buyer_place", objectList.get(position).getString("Buyer_place"));
                intent.putExtra("UserName", objectList.get(position).getString("UserName"));
                intent.putExtra("UserID", objectList.get(position).getString("UserID").toString());
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
