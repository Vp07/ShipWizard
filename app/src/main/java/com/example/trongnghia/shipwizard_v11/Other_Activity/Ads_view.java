package com.example.trongnghia.shipwizard_v11.Other_Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Ads_view extends AppCompatActivity implements View.OnClickListener{

    public String title, objectID, objectClass;

    public TextView Title, Ads_type_price, Time, Location, Category, Condition, Description;

    public Button bPromote, bEdit, bPause, bDelete, bUnpause, bDelete1;

    public LinearLayout layout1;
    public LinearLayout layout2;

    public static ParseQuery<ParseObject> query;
    public UserInfo user;

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
        bUnpause = (Button)findViewById(R.id.btUn_Pause);
        bDelete1 = (Button)findViewById(R.id.btDelete1);

        layout1 = (LinearLayout)findViewById(R.id.layout1);
        layout2 = (LinearLayout)findViewById(R.id.layout2);

        bPromote.setOnClickListener(this);
        bEdit.setOnClickListener(this);
        bPause.setOnClickListener(this);
        bDelete.setOnClickListener(this);
        bUnpause.setOnClickListener(this);
        bDelete1.setOnClickListener(this);

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
            ParseObject saved_post = new ParseObject(extras.getString("ObjectClass"));
            saved_post.put("objectID", objectID);
            saved_post.pinInBackground();
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
            case R.id.btPromote:
                Toast.makeText(Ads_view.this,UserInfo.objectID,Toast.LENGTH_SHORT).show();
                break;

            case R.id.btEdit:
                break;

            case R.id.btPause:
                //Toast.makeText(Ads_view.this,UserInfo.objectID,Toast.LENGTH_SHORT).show();
                query.getInBackground(objectID, new GetCallback<ParseObject>() {
                    public void done(ParseObject post, ParseException e) {
                        if (e == null) {
                            post.put("Status", "Pause");
                            post.saveInBackground();
                        }
                    }
                });
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);

                break;

            case R.id.btDelete|R.id.btDelete1:
                new AlertDialog.Builder(this)
                        .setTitle("Delete an Ads")
                        .setMessage("Delete this Ads?")
                        .setCancelable(false)
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ParseObject.createWithoutData(objectClass,objectID).deleteEventually(new DeleteCallback() {
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Intent intent = new Intent();

                                            // put the message in Intent
                                            intent.putExtra("MESSAGE", "DONE");
                                            // Set The Result in Intent
                                            setResult(1, intent);
                                            // finish The activity
                                            finish();
                                        } else {
                                            //myObjectDeleteDidNotSucceed();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
                break;

            case R.id.btUn_Pause:
                query.getInBackground(objectID, new GetCallback<ParseObject>() {
                    public void done(ParseObject post, ParseException e) {
                        if (e == null) {
                            post.put("Status", "Active");
                            post.saveInBackground();
                        }
                    }
                });
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
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
               // NavUtils.navigateUpFromSameTask(this);
//                FragmentManager fm = getFragmentManager();
//                if (fm.getBackStackEntryCount() > 0) {
//                    //Log.i("MainActivity", "popping backstack");
//                    fm.popBackStack();
//                } else {
//                    //Log.i("MainActivity", "nothing on backstack, calling super");
//                    super.onBackPressed();
//                }

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
