package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.LogIn.DispatchActivity;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Ads_view;
import com.example.trongnghia.shipwizard_v11.Other_Activity.Public_Ads_view;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Ads_History_Adapter;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Ads_History_Items;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class Order_Fragment_View extends Fragment{


    ListView ads;

    public ArrayList<String> recent_search_string;
    public String postObject = "UserPost";
    public String UserIDCol = "UserID";



    public List<ParseObject> postList;
    public ParseObject temp_object;

    ImageView test_view;

    private Bitmap[] bitmap = new Bitmap[100];
    private String[] title = new String[100];
    private String[] price = new String[100];
    private String[] time = new String[100];
    private String[] location = new String[100];

    int position;

    public View view;

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    public UserInfo user;

    public Order_Fragment_View_Adapter adapter;
    public ListView listView;
    public static ParseQuery<ParseObject> query;

    public static Order_Fragment_View newInstance(){
        Order_Fragment_View fragment = new Order_Fragment_View();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__order_view, container, false);
        user = new UserInfo();
        //test_view = (ImageView) view.findViewById(R.id.imageView_test);

        listview_init();
        //Toast.makeText(getActivity(), Integer.toString(te), Toast.LENGTH_LONG).show();
        return view;
    }

    public void listview_init(){
        query = ParseQuery.getQuery("UserPost");
        query.whereEqualTo("Ads_Type", "Order");
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
        for (int i=0; i<objectList.size(); i++) {
            Log.d("","" + objectList.get(i).getObjectId());
            position = i;
            temp_object = objectList.get(i);
            bitmap[i] = null;
            // Get image file from Parse object
            ParseFile img_file = temp_object.getParseFile("Image_1");
            // Hiep -> Do something to get bitmap data from img_file
            if (img_file != null) {
                img_file.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, ParseException e) {
                        bitmap[position] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        //test_view.setImageBitmap(bitmap[position]);
                    }
                });
            }
            else{
                bitmap[position] = null;
            }
            title[position] = objectList.get(i).getString("Title");
            price[position] = objectList.get(i).getString("Price");
            time[position] = objectList.get(i).getString("Time");
            location[position] = objectList.get(i).getString("Buyer_place");
        }



        // Get ListView from activity_main.xml
        listView = (ListView) view.findViewById(R.id.lvOrder_view);
        adapter = new Order_Fragment_View_Adapter(this.getContext() , bitmap,title,time,location,price);
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
