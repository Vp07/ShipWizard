package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.Other_Activity.Ads_view;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Public_Ads_view;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Ads_History_Adapter;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Ads_History_Items;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Ship_Fragment_View extends Fragment {

    ListView ads;


    public ArrayList<String> recent_search_string;
    public String postObject = "UserPost";
    public String UserIDCol = "UserID";

    public List<ParseObject> postList;
    public ParseObject temp_object;

    private Bitmap[] bitmap = new Bitmap[100];
    private String[] title = new String[100];
    private String[] price = new String[100];
    private String[] time = new String[100];
    private String[] location = new String[100];

    public View view;

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    public UserInfo user;

    public Ship_Fragment_View_Adapter adapter;
    public ListView listView;
    public static ParseQuery<ParseObject> query;

    public static Ship_Fragment_View newInstance(){
        Ship_Fragment_View fragment = new Ship_Fragment_View();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_list_view, container, false);

        user = new UserInfo();

        listview_init();
        //Toast.makeText(getActivity(), Integer.toString(te), Toast.LENGTH_LONG).show();
        return view;
    }

    public void listview_init(){
        query = ParseQuery.getQuery("UserPost");
        query.whereEqualTo("Ads_Type", "Ship");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    setListItem(objectList);
                } else {
                }
            }
        });
    }



    public void getAds(List<ParseObject> objectList){
        this.postList = objectList;
    }

    public void setListItem(final List<ParseObject> objectList){
        // Pass context and data to the custom adapter

        for (int i=0; i<objectList.size(); i++){
            temp_object = objectList.get(i);
            ParseFile img_file = temp_object.getParseFile("Image_1");
            if (img_file != null) {
                try {
                    byte[] data = img_file.getData();
                    if(data!=null){
                        bitmap[i] = BitmapFactory.decodeByteArray(data,0,data.length);

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // Hiep -> Do something to get bitmap data from img_file
            title[i] = objectList.get(i).getString("Title");
            price[i] = objectList.get(i).getString("Price");
            time[i] = objectList.get(i).getString("Time");
            location[i] = objectList.get(i).getString("Buyer_place");
        }

        // Get ListView from activity_main.xml
        listView = (ListView) view.findViewById(R.id.ads_list_view);
        adapter = new Ship_Fragment_View_Adapter(this.getContext(), bitmap,title,time,location,price);
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
