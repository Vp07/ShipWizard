package com.example.trongnghia.shipwizard_v11.Other_Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.trongnghia.shipwizard_v11.Library.TinyDB;
import com.example.trongnghia.shipwizard_v11.NewTransaction.ImageAdapter;
import com.example.trongnghia.shipwizard_v11.NewTransaction.View_Transaction;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Recent_Search_Item;
import com.example.trongnghia.shipwizard_v11.User.UserAction;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Public_Ads_view extends AppCompatActivity implements View.OnClickListener {

    public static String title, objectID, objectClass, username, toUserID;

    public TextView Title, Ads_type_price, Time, Location, Category, Condition, Description;

    public Button bInbox, bCall, bSms;

    public static ParseQuery<ParseObject> query;
    public UserInfo user;

    // Save favorite Ads
    public static TinyDB tinydb;
    public List<String> favorite_ads_list = new ArrayList<String>();
    public boolean bookmark_flag = false;
    //public UserAction user_favorite_ads = new UserAction();
    public Drawable bookmark_icon;
    public List<String> user_bookmarks = new ArrayList<>();
    TextView tv;
    ImageAdapter bm_adapter;
    int i;
    Bitmap[] bitmap;
    ParseObject temp_object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_view_public);

//        tinydb = new TinyDB(this);
//
        Title = (TextView)findViewById(R.id.title_public);
        Ads_type_price = (TextView)findViewById(R.id.price_public);
        Time = (TextView)findViewById(R.id.posted_time);
        Location = (TextView)findViewById(R.id.location_public);
        Category = (TextView)findViewById(R.id.category_public);
        Condition = (TextView)findViewById(R.id.condition_public);
        Description = (TextView)findViewById(R.id.description_public);
//
        bInbox = (Button)findViewById(R.id.btInbox);
        bCall = (Button)findViewById(R.id.btCall);
        bSms = (Button)findViewById(R.id.btSms);

        bInbox.setOnClickListener(this);
        bCall.setOnClickListener(this);
        bSms.setOnClickListener(this);
//
        bookmark_icon = getResources().getDrawable(R.drawable.ic_grade_white_24dp);
        initialise();
        bm_adapter = new ImageAdapter(this);
        try {
            temp_object = query.get(objectID);
        }catch (Exception e){
            Toast.makeText(this, "Object not found!", Toast.LENGTH_SHORT).show();
        }
        if(temp_object!=null){
            for(int i=1;i<10;i++){
                if(temp_object.get("Image_"+i)!=null) {
                    ParseFile temp_file = temp_object.getParseFile("Image_" + i);

                    try {
                        byte[] data = temp_file.getData();
                        if(data!=null){
                            bm_adapter.bm[i-1] = BitmapFactory.decodeByteArray(data,0,data.length);
                            //Log.d("assign possition",""+i);
                        }
                    }catch (Exception e){
                        Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    break;
                }
            }
            //ImageView tesst = (ImageView) findViewById(R.id.Test);
            //tesst.setImageBitmap(bm_adapter.bm[2]);
        }



        final ViewFlipper vfAds_Img = (ViewFlipper) findViewById(R.id.viewFlipper_Ads_Img_Public);

        for(int i=0;i<9;i++){
            if(bm_adapter.bm[i]!=null){
                ImageView iv = new ImageView(this);
                iv.setImageBitmap(bm_adapter.bm[i]);;
                vfAds_Img.addView(iv);

            }
        }
        int DisplayPostion = vfAds_Img.getDisplayedChild()+1;
        tv = (TextView) findViewById(R.id.ItemNo_Public);
        tv.setText("Image " + DisplayPostion + " / " + vfAds_Img.getChildCount());

        ImageView ivNext = (ImageView) findViewById(R.id.vf_next_public);
        ImageView ivPrevious = (ImageView) findViewById(R.id.vf_previous_public);

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vfAds_Img.showNext();
                int DisplayPostion = vfAds_Img.getDisplayedChild()+1;
                tv.setText("Image " + DisplayPostion + " / " + vfAds_Img.getChildCount());
            }
        });

        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vfAds_Img.showPrevious();
                int DisplayPostion = vfAds_Img.getDisplayedChild()+1;
                tv.setText("Image " + DisplayPostion + " / " + vfAds_Img.getChildCount());
            }
        });


    }

    /**
     * Create, bind and set up the resources
     */
    private void initialise()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("Title");
            Title.setText(title);
            Ads_type_price.setText(extras.getString("Ads_Type") + " - " + extras.getString("Price"));
            Time.setText(extras.getString("Time"));
            Location.setText(extras.getString("Buyer_place"));
            Category.setText(extras.getString("Item"));
            // Condition.setText(extras.getString("Item"));
            Description.setText(extras.getString("Description"));
            objectID = extras.getString("ObjectID");
            objectClass = extras.getString("ObjectClass");
            toUserID = extras.getString("UserID");
            username = extras.getString("UserName");
            ParseObject saved_post = new ParseObject(extras.getString("ObjectClass"));
            user = new UserInfo();
            user.getObjectID(objectID);
            query = ParseQuery.getQuery(extras.getString("ObjectClass"));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btInbox:
                Intent intent = new Intent(Public_Ads_view.this, ChatActivity.class);
                intent.putExtra("UserID", toUserID );
                intent.putExtra("UserName", username);
                intent.putExtra("AdsID",objectID);
                startActivity(intent);
                break;

            case R.id.btCall:
                break;

            case R.id.btSms:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_public__ads_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            // Return to Post activity
            case android.R.id.home:
                //finish();
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_bookmark:

                ParseQuery<UserAction> query = ParseQuery.getQuery(UserAction.class);
                 // Retrieve the object by id
                query.whereEqualTo("UserID", UserInfo.userID);
                query.findInBackground(new FindCallback<UserAction>() {
                    public void done(List<UserAction> object, ParseException e) {
                        if (e == null) {
                            favorite_ads_list = object.get(0).getAdsID();
                                // if this Ads is already saved as favorite, then uncheck it
                            if(favorite_ads_list.contains(objectID)==true){
                                Toast.makeText(Public_Ads_view.this,"check", Toast.LENGTH_SHORT).show();
                                favorite_ads_list.remove(objectID);
                                ColorFilter filter = new LightingColorFilter( Color.YELLOW, Color.YELLOW);
                                //ColorFilter filter = new LightingColorFilter( Color.WHITE, Color.WHITE);
                                bookmark_icon.setColorFilter(filter);
                            }
                            else { // If this ads has not saved as favorite
                                Toast.makeText(Public_Ads_view.this,"uncheck", Toast.LENGTH_SHORT).show();
                                favorite_ads_list.add(objectID);
                                //ColorFilter filter = new LightingColorFilter( Color.YELLOW, Color.YELLOW);
                                ColorFilter filter = new LightingColorFilter( Color.WHITE, Color.WHITE);
                                bookmark_icon.setColorFilter(filter);
                            }
                            Toast.makeText(Public_Ads_view.this, Integer.toString(favorite_ads_list.size()), Toast.LENGTH_SHORT).show();
                            object.get(0).setAdsID(favorite_ads_list);
                            object.get(0).saveInBackground();
                        } else {
                            Log.d("message", "Error: " + e.getMessage());
                        }
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
