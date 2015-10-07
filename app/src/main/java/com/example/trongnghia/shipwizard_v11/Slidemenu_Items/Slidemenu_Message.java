package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.Library.TinyDB;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Message_History_Adapter;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Message_History_List_Item;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserAction;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public static List<ParseQuery<ParseObject>> queries;

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
        listview_init();
        return view;
    }

    public void listview_init(){
        // Get Ads message list from UserAction class
        ParseQuery<UserAction> query = ParseQuery.getQuery(UserAction.class);
        query.whereEqualTo("UserID", UserInfo.userID);
        query.findInBackground(new FindCallback<UserAction>() {
            public void done(List<UserAction> object, ParseException e) {
                if (e == null) {
                    list_temp = object.get(0).getList("AdsIDMessage");
                    storage.putListString("AdsIDMessage", (ArrayList) list_temp);

                    query_temp = ParseQuery.getQuery("UserPost");
                    query_temp.whereEqualTo("UserID", UserInfo.userID);
                    query_temp.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> object, ParseException e) {
                            if (e == null) {
                                list_temp.clear();
                                for (int i = 0; i < object.size(); i++) {
                                    list_temp.add(object.get(i).getObjectId().toString());
                                }

                                adsList = storage.getListString("AdsIDMessage");
                                List<String> newList = new ArrayList<String>(adsList);
                                newList.addAll(list_temp);
                                Toast.makeText(getActivity(), "Text :"+Integer.toString(newList.size()), Toast.LENGTH_SHORT).show();

                                // Then retrieve all ads
                                for (int i=0; i<newList.size(); i++){
                                    query_temp = ParseQuery.getQuery("UserPost");
                                    query_temp.whereEqualTo("objectId", newList.get(i));
                                    queries.add(query_temp);
                                }
                                ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
                                mainQuery.findInBackground(new FindCallback<ParseObject>() {
                                    public void done(List<ParseObject> objectList, ParseException e) {
                                        if (e == null) {
                                            if (objectList.size() > 0) {
                                               setListItem(objectList);
                                            }
                                        } else {
                                        }
                                    }
                                });
                            } else {
                                Log.d("message", "Error: " + e.getMessage());
                            }
                        }
                    });
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void setListItem(final List<ParseObject> objectList){
        // Pass context and data to the custom adapter
        adapter = new Message_History_Adapter(getActivity(), generateData(objectList));
        // Get ListView from activity_main.xml
        listView = (ListView) view.findViewById(R.id.lvMessage_history);
        // SetListAdapter
        listView.setAdapter(adapter);
        // React to user clicks on item
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
//                                    long id) {
//                // We know the View is a TextView so we can cast it
//                Intent intent = new Intent(getActivity(), Public_Ads_view.class);
//                intent.putExtra("Title", objectList.get(position).getString("Title"));
//                intent.putExtra("Price", objectList.get(position).getString("Price"));
//                intent.putExtra("Time", objectList.get(position).getString("Time"));
//                intent.putExtra("Item", objectList.get(position).getString("Item"));
//                intent.putExtra("Description", objectList.get(position).getString("Description"));
//                intent.putExtra("Ads_Type", objectList.get(position).getString("Ads_Type"));
//                intent.putExtra("Buyer_place", objectList.get(position).getString("Buyer_place"));
//                intent.putExtra("UserName", objectList.get(position).getString("UserName"));
//                intent.putExtra("UserID", objectList.get(position).getString("UserID").toString());
//                intent.putExtra("ObjectID", objectList.get(position).getObjectId().toString());
//                intent.putExtra("ObjectClass", objectList.get(position).getClassName().toString());
//                startActivityForResult(intent, 1);
//            }
//        });
    }

    private ArrayList<Message_History_List_Item> generateData(List<ParseObject> objectList){
        for (int i=0; i<objectList.size(); i++){
            temp_object = objectList.get(i);
            // Get image file from Parse object
            // ParseFile img_file = temp_object.getParseFile("img");
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
}
