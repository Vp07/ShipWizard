package com.example.trongnghia.shipwizard_v11.Other_Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.NewTransaction.View_Transaction;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Public_Ads_view extends AppCompatActivity implements View.OnClickListener {

    public static String title, objectID, objectClass, username, toUserID;

    public TextView Title, Ads_type_price, Time, Location, Category, Condition, Description;

    public Button bInbox, bCall, bSms;

    public static ParseQuery<ParseObject> query;
    public UserInfo user;

    public ParseObject user_message = new ParseObject("UserMessage");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_view_public);

        Title = (TextView)findViewById(R.id.tvTitle);
        Ads_type_price = (TextView)findViewById(R.id.tvPrice);
        Time = (TextView)findViewById(R.id.tvTime);
        Location = (TextView)findViewById(R.id.tvLocation);
        Category = (TextView)findViewById(R.id.tvCategory);
        Condition = (TextView)findViewById(R.id.tvCondition);
        Description = (TextView)findViewById(R.id.tvDescription);

        bInbox = (Button)findViewById(R.id.btInbox);
        bCall = (Button)findViewById(R.id.btCall);
        bSms = (Button)findViewById(R.id.btSms);

        bInbox.setOnClickListener(this);
        bCall.setOnClickListener(this);
        bSms.setOnClickListener(this);

        initialise();
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
            UserInfo user = new UserInfo();
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
                startActivity(intent);
                break;

            case R.id.btCall:
                break;

            case R.id.btSms:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            // Return to Post activity
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
