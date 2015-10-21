package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.Library.TinyDB;
import com.example.trongnghia.shipwizard_v11.Other_Activity.ChatActivity;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Message;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Message_History_Adapter;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Message_History_List_Item;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Slidemenu_Message extends Fragment {

    ListView ads;

    public static ArrayList<Message_History_List_Item> items;
    public ArrayList<Message_History_List_Item> array;
    public String postObject = "UserPost";
    public String UserIDCol = "UserID";

    public List<String> display_msg = new ArrayList<String>();;
    public ParseObject temp_object;

    public Bitmap bitmap;
    public View view;
    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    public Message_History_Adapter adapter;
    public ListView listView;

    // Save ads message list
    TinyDB storage;
    // Used to save parameter settings
    public static List<String> temp = new ArrayList<>();
    public List<String> adsList = new ArrayList<>();
    public List<String> list_temp = new ArrayList<>();
    public List<String> list_temp_1 = new ArrayList<>();
    public ParseQuery<ParseObject> query_temp;
    public ParseQuery<Message> query_msg;
    public static List<ParseQuery<Message>> query_msg_list;
    public static List<ParseQuery<ParseObject>> queries;
    public List<String> contentList = new ArrayList<String>();
    public List<String> senderList = new ArrayList<String>();
    public String content;
    public String sender;

    public static Slidemenu_Message newInstance(){
        Slidemenu_Message fragment = new Slidemenu_Message();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.slidemenu_message, container, false);
        items = new ArrayList<Message_History_List_Item>();
        storage = new TinyDB(getActivity());
        queries = new ArrayList<ParseQuery<ParseObject>>();
        query_msg_list = new ArrayList<ParseQuery<Message>>();
        listview_init();
        return view;
    }

    public void listview_init(){
        // Get Ads message list from UserAction class
        ParseQuery<Message> query1 = ParseQuery.getQuery(Message.class);
        query1.whereEqualTo("FromUserID", UserInfo.userID);

        ParseQuery<Message> query2 = ParseQuery.getQuery(Message.class);
        query2.whereEqualTo("ToUserID", UserInfo.userID);

        query_msg_list.add(query1);
        query_msg_list.add(query2);

        ParseQuery<Message> mainQuery = ParseQuery.or(query_msg_list);

        mainQuery.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> objectList, ParseException e) {
                if (e == null) {
                    //list_temp = object.get(0).getList("Content");
                    setListItem(objectList);
                    Toast.makeText(getActivity(), Integer.toString(objectList.size()), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void setListItem(final List<Message> objectList){
        // Pass context and data to the custom adapter
        adapter = new Message_History_Adapter(getActivity(), generateData(objectList));
        // Get ListView from activity_main.xml
        listView = (ListView) view.findViewById(R.id.lvMessage_history);
        // SetListAdapter
        listView.setAdapter(adapter);
        // React to user clicks on item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
                // We know the View is a TextView so we can cast it
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("UserID", UserInfo.userID );
                intent.putExtra("UserName", UserInfo.username);
                intent.putExtra("MessageID",objectList.get(position).getObjectId().toString());
                intent.putExtra("Source", "Message_History");
                startActivityForResult(intent, 1);
            }
        });
    }

    private ArrayList<Message_History_List_Item> generateData(List<Message> objectList){
       // Toast.makeText(getActivity(), Integer.toString(objectList.size()), Toast.LENGTH_SHORT).show();

        for (int i=0; i<objectList.size(); i++) {
            temp_object = objectList.get(i);
            contentList = temp_object.getList("Content");
            content = contentList.get(contentList.size() - 1);
            senderList = temp_object.getList("Sender");
            sender = senderList.get(senderList.size()-1);
                items.add(new Message_History_List_Item(bitmap,
//                        temp_object.getString("Title"),
//                        temp_object.getString("User"),
//                        temp_object.getString("Time"),
//                        temp_object.getString("Status"),
//                        temp_object.getString("Content")));
                        temp_object.getString("AdsTitle"),
                        sender,
                        "",
                        "",
                        content));
        }
      //  }
        return items;
    }

//    private ArrayList<Message_History_List_Item> generateData(List<ParseObject> objectList){
//        for (int i=0; i<objectList.size(); i++){
//            temp_object = objectList.get(i);
//            // Get image file from Parse object
//            // ParseFile img_file = temp_object.getParseFile("img");
//            // Hiep -> Do something to get bitmap data from img_file
//            if (temp_object.getString("LastMsg")!= "")
//            {
//                Toast.makeText(getActivity(), temp_object.getString("LastMsg"), Toast.LENGTH_SHORT).show();
//                items.add(new Message_History_List_Item(bitmap,
//                        temp_object.getString("Title"),
//                        temp_object.getString("User"),
//                        temp_object.getString("Time"),
//                        temp_object.getString("Status"),
//                        temp_object.getString("LastMsg")));
//            }
//        }
//        return items;
//    }
}
