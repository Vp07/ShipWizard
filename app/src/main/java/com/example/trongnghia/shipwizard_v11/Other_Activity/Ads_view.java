package com.example.trongnghia.shipwizard_v11.Other_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.LogIn.DispatchActivity;
import com.example.trongnghia.shipwizard_v11.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Ads_view extends AppCompatActivity implements View.OnClickListener{

    public String title, objectID, objectClass;

    public TextView Title, Ads_type_price, Time, Location, Category, Condition, Description;

    public Button bPromote, bEdit, bPause, bDelete;

    ParseQuery<ParseObject> query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_view);

        Title = (TextView)findViewById(R.id.tvTitle);
        Ads_type_price = (TextView)findViewById(R.id.tvPrice);
        Time = (TextView)findViewById(R.id.tvTime);
        Location = (TextView)findViewById(R.id.tvLocation);
        Category = (TextView)findViewById(R.id.tvCategory);
        Condition = (TextView)findViewById(R.id.tvCondition);
        Description = (TextView)findViewById(R.id.tvDescription);

        bPromote = (Button)findViewById(R.id.btPromote);
        bEdit = (Button)findViewById(R.id.btEdit);
        bPause = (Button)findViewById(R.id.btPause);
        bDelete = (Button)findViewById(R.id.btDelete);

        bPromote.setOnClickListener(this);

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
            query = ParseQuery.getQuery(extras.getString("ObjectClass"));

            query.getInBackground(objectID, new GetCallback<ParseObject>() {
                public void done(ParseObject post, ParseException e) {
                    if (e == null) {
                        // Now let's update it with some new data. In this case, only cheatMode and score
                        // will get sent to the Parse Cloud. playerName hasn't changed.
                        post.put("Item", "Change");
                        post.saveInBackground();
                    }
                }
            });
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
            case R.id.btPromote:
                break;

            case R.id.btEdit:
                break;

            case R.id.btPause:
                query.getInBackground(objectID, new GetCallback<ParseObject>() {
                    public void done(ParseObject post, ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data. In this case, only cheatMode and score
                            // will get sent to the Parse Cloud. playerName hasn't changed.
                            post.put("Item", "Change");
                            post.saveInBackground();
                        }
                    }
                });
                break;

            case R.id.btDelete:
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
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
