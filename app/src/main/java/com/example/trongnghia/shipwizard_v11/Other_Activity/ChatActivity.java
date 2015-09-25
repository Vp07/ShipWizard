package com.example.trongnghia.shipwizard_v11.Other_Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trong Nghia on 9/23/2015.
 */
public class ChatActivity extends AppCompatActivity {
    private static final String TAG = ChatActivity.class.getName();
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    private static String From_UserID, To_UserID, To_User_name, AdsID;
    private static String sUserId;

    public static final String FROM_USER_ID_KEY = "FromUserID";
    public static final String TO_USER_ID_KEY = "ToUserID";
    public EditText etMessage;
    public Button btSend;

    private ListView lvChat;
    private ArrayList<Message> mMessages;
    private ChatAdapter mAdapter;
    // Keep track of initial load to scroll to the bottom of the ListView
    private boolean mFirstLoad;

    // Create a handler which can run code periodically
    // Get the message from other user
    private Handler handler = new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send__message);
        From_UserID = UserInfo.userID;
        //sUserId = ParseUser.getCurrentUser().getObjectId();
        setupMessagePosting();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            To_UserID = extras.getString("UserID");
            To_User_name = extras.getString("UserName");
            AdsID = extras.getString("AdsID");
        }
        toolbar_setup();
        // Run the runnable object defined every 100ms
        handler.postDelayed(runnable, 100);

    }

    // Setup button event handler which posts the entered message to Parse
    private void setupMessagePosting() {
        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etInbox);
//        InputMethodManager imm = (InputMethodManager)ChatActivity.this.getSystemService(ChatActivity.this.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(etMessage.getWindowToken(), 0)
        lvChat = (ListView) findViewById(R.id.lvContent);
        mMessages = new ArrayList<Message>();
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;
        mAdapter = new ChatAdapter(ChatActivity.this, From_UserID, mMessages);
        lvChat.setAdapter(mAdapter);

        btSend = (Button) findViewById(R.id.btSend);
        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
                Message message = new Message();
                message.setFromUserID(From_UserID);
                message.setFromUsername(UserInfo.username);
                message.setToUserID(To_UserID);
                message.setToUserName(To_User_name);
                message.setConnectionString(From_UserID + To_UserID);
                message.setContent(data);
                // AdsID is ObjectID of current post ads
                message.setAdsID(AdsID);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        receiveMessage();
                    }
                });
                etMessage.setText("");
            }
        });
    }

    // Query messages from Parse so we can load them into the chat adapter
    private void receiveMessage() {
        // Make a list of query
        List<ParseQuery<Message>> queries = new ArrayList<ParseQuery<Message>>();

        // First query
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.whereEqualTo("Connection", From_UserID+To_UserID);
        //query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        //query.orderByAscending("createdAt");

        // Second query
        ParseQuery<Message> query1 = ParseQuery.getQuery(Message.class);
        query1.whereEqualTo("Connection", To_UserID+From_UserID);

        queries.add(query);
        queries.add(query1);

        ParseQuery<Message> mainQuery = ParseQuery.or(queries);
        mainQuery.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        mainQuery.orderByAscending("createdAt");

        mainQuery.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    // Defines a runnable which is run every 100ms
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 1000);
        }
    };

    private void refreshMessages() {
        receiveMessage();
    }

    public void toolbar_setup(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Send message to "+To_User_name);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            // Return to detail view activity
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}