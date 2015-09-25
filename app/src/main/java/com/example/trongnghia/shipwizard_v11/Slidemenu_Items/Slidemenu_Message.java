package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.NewTransaction.Order_Fragment_View_Adapter;
import com.example.trongnghia.shipwizard_v11.NewTransaction.Order_Fragment_View_List_Items;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Message;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Message_History_Adapter;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Message_History_List_Item;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Public_Ads_view;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.example.trongnghia.shipwizard_v11.User.User_Post_Querry;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Slidemenu_Message extends Fragment {

    ListView ads;

    
    public static ArrayList<Message_History_List_Item> items;
    public ArrayList<Message_History_List_Item> array;
    public ArrayList<String> recent_search_string;
    public String postObject = "UserPost";
    public String UserIDCol = "UserID";

    public Message_History_List_Item temp;

    public User_Post_Querry objectList;
    public List<ParseObject> postList;
    public List<String> display_msg = new ArrayList<String>();;
    public List<String> temp_list;
    public ParseObject temp_object;

    public Bitmap bitmap;

    public View view;

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    public UserInfo user;

    public Message_History_Adapter adapter;
    public ListView listView;
    public static ParseQuery<Message> query;

    public static Slidemenu_Message newInstance(){
        Slidemenu_Message fragment = new Slidemenu_Message();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.slidemenu_message, container, false);

        items = new ArrayList<Message_History_List_Item>();
        user = new UserInfo();
        listview_init();
        return view;
    }

    public void listview_init(){
        List<ParseQuery<Message>> queries = new ArrayList<ParseQuery<Message>>();
        // First query
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.whereEqualTo("FromUserID", UserInfo.userID);

        // Second query
        ParseQuery<Message> query1 = ParseQuery.getQuery(Message.class);
        query1.whereEqualTo("ToUserID", UserInfo.userID);

        queries.add(query);
        queries.add(query1);
        ParseQuery<Message> mainQuery = ParseQuery.or(queries);
        mainQuery.orderByAscending("createdAt");
        mainQuery.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> objectList, ParseException e) {
                if (e == null) {
                    getDisplayMsg(objectList);
                    Toast.makeText(getActivity(), Integer.toString(display_msg.size()), Toast.LENGTH_SHORT).show();
                    // setListItem(objectList);
                } else {
                }
            }
        });
    }

    public List<String> getDisplayMsg(List<Message> objectList){
        // Obtain last object for each AdsID group
        int index = 0;
        for (int i=0; i<objectList.size()-1; i++){
            if (objectList.get(i).getString("AdsID") != objectList.get(i+1).getString("AdsID")){
                display_msg.add(index, objectList.get(i).getString("AdsID"));
                index++;
//                if (objectList.get(objectList.size()-2).getString("AdsID") !=
//                        objectList.get(objectList.size()-1).getString("AdsID")){
//                    display_msg.add(index+1, objectList.get(objectList.size()-1).getString("AdsID"));
//                    Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
//                }
            }
        }
        return display_msg;
    }

    private ArrayList<Message_History_List_Item> generateData(List<Message> objectList){
        //Toast.makeText(getActivity(), Integer.toString(postList.size()), Toast.LENGTH_SHORT).show();
        for (int i=0; i<objectList.size(); i++){
            temp_object = objectList.get(i);

            // Get image file from Parse object
            ParseFile img_file = temp_object.getParseFile("img");
            // Hiep -> Do something to get bitmap data from img_file

            items.add(new Message_History_List_Item(bitmap,
                    temp_object.getString("Title"),
                    temp_object.getString("User"),
                    temp_object.getString("Time"),
                    temp_object.getString("Status"),
                    "Last_msg"));
        }
        return items;
    }

    public void setListItem(final List<Message> objectList){
        // Pass context and data to the custom adapter
        adapter = new Message_History_Adapter(getActivity(), generateData(objectList));
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
