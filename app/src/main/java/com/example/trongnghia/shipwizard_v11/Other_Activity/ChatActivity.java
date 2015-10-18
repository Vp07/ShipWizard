package com.example.trongnghia.shipwizard_v11.Other_Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.Library.TinyDB;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserAction;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trong Nghia on 9/23/2015.
 */
public class ChatActivity extends AppCompatActivity {
    private static final String TAG = ChatActivity.class.getName();
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    private static String From_UserID, To_UserID, To_User_name, AdsID;
    private static String title;

    public EditText etMessage;
    public Button btSend;

    private ListView lvChat;
    //private ArrayList<Message> mMessages;
    private ArrayList<String[][]> mMessages;
    private ArrayList<Message> mTemp;

    private ChatAdapter mAdapter;
    // Keep track of initial load to scroll to the bottom of the ListView
    private boolean mFirstLoad = true;

    // Create a handler which can run code periodically
    // Get the message from other user
    private Handler handler = new Handler();

    // Save ads having message
    public UserAction AdsMessageList = new UserAction();
    public List<String> adsList = new ArrayList<>();
    public List<String> senderList = new ArrayList<>();
    public List<String> message_list = new ArrayList<>();
    public TinyDB temp_save;

    // Query to update last message to UserPost class
    ParseQuery<ParseObject> query_last_msg = ParseQuery.getQuery("UserPost");
    ParseQuery<Message> query_msg = ParseQuery.getQuery(Message.class);

    // Each message is a
    List<String[][]> content_list = new ArrayList<String[][]>();
    String[][] content = new String[1][2];
    String[][] test = new String[1][1];

    String temp, caller, objectID;
    public Gson gson = new Gson();
    Message message = new Message();

    JSONArray myArray = new JSONArray();
    JSONObject myObject = new JSONObject();

    public Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send__message);
        From_UserID = UserInfo.userID;
        temp_save = new TinyDB(this);
        extras = getIntent().getExtras();
        if (extras != null) {
            To_UserID = extras.getString("UserID"); // User who posts this Ads
            To_User_name = extras.getString("UserName");
            AdsID = extras.getString("AdsID");
            title = extras.getString("AdsTitle");
            caller = extras.getString("Source");
        }

        firstQuery(caller);
        setupMessagePosting();
        toolbar_setup();

        // Run the runnable object defined every 100ms
        handler.postDelayed(runnable, 100);
    }

    // Setup button event handler which posts the entered message to Parse
    private void setupMessagePosting() {
        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etInbox);
        lvChat = (ListView) findViewById(R.id.lvContent);
        //mMessages = new ArrayList<Message>();
        mMessages = new ArrayList<String[][]>();
        mTemp = new ArrayList<>();

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
                //Toast.makeText(ChatActivity.this, objectID, Toast.LENGTH_SHORT).show();
                final String data = etMessage.getText().toString();
                objectID = temp_save.getString("ObjectID");
                //Toast.makeText(ChatActivity.this, objectID, Toast.LENGTH_SHORT).show();
                // If there is no object of this msg box on Parse
                if (objectID.equals("")) {
                    //Toast.makeText(ChatActivity.this, "i am A", Toast.LENGTH_SHORT).show();
                    //content_list.add(temp);
                    adsList.add(data);
                    senderList.add(UserInfo.username);
                    message.setSenderList(senderList);
                    message.setContentList(adsList);
                    //message.setContent(content_list);
                    message.setFromUserID(From_UserID);
                    message.setFromUsername(UserInfo.username);
                    message.setToUserID(To_UserID);
                    message.setToUserName(To_User_name);
                    message.setConnectionString(From_UserID + To_UserID + AdsID);
                    message.setAdsTitle(title);
//                // AdsID is ObjectID of current post ads
                    message.setAdsID(AdsID);
                    message.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            query_msg.whereEqualTo("Connection", From_UserID + To_UserID + AdsID);
                            //Toast.makeText(ChatActivity.this, "abcd: " + From_UserID + To_UserID, Toast.LENGTH_SHORT).show();
                            query_msg.findInBackground(new FindCallback<Message>() {
                                public void done(List<Message> object, ParseException e) {
                                    if (e == null) {
                                        // if there is already this object on Parse, then store the data
                                        if (object.size() > 0) {
                                            objectID = object.get(0).getObjectId().toString();
                                            //temp_save.putListString("Content", (ArrayList) adsList);
                                            temp_save.putString("ObjectID", objectID);
                                            Toast.makeText(ChatActivity.this, objectID, Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Log.d("message", "Error: " + e.getMessage());
                                    }
                                }
                            });
                        }
                    });
                    //adsList = temp_save.getListString("Content");
                    //receiveMessage();
                } else { // If there is already this chat box on Parse, just update it
                    //Toast.makeText(ChatActivity.this, "i am B", Toast.LENGTH_SHORT).show();
                    query_msg.whereEqualTo("objectId", objectID);
                    query_msg.findInBackground(new FindCallback<Message>() {
                        public void done(List<Message> object, ParseException e) {
                            if (e == null) {
                                // if there is already this object on Parse, then store the data
                                if (object.size() > 0) {
                                    adsList = object.get(0).getContentList();
                                    senderList = object.get(0).getSender();
                                    adsList.add(data);
                                    senderList.add(UserInfo.username);
                                    object.get(0).setContentList(adsList);
                                    object.get(0).setSenderList(senderList);
                                    object.get(0).saveInBackground();
                                    receiveMessage();
                                }
                            } else {
                                Log.d("message", "Error: " + e.getMessage());
                            }
                        }
                    });

                }
                etMessage.setText("");
            }
        });
    }

    // Query messages from Parse so we can load them into the chat adapter
    private void receiveMessage() {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.getInBackground(objectID, new GetCallback<Message>() {
            public void done(Message object, ParseException e) {
                if (e == null) {
                    adsList = object.getContentList();
                    senderList = object.getSender();
                    mMessages.clear();
                    content_list.clear();
                    for (int i=0; i<adsList.size(); i++){
                        //content[0][0] = adsList.get(i);
                        content[0][0] = adsList.get(i);
                        content[0][1] = senderList.get(i);
                        content_list.add(content);
                        //Toast.makeText(ChatActivity.this,adsList.get(i), Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(ChatActivity.this,content_list.get(0)[0][0], Toast.LENGTH_SHORT).show();
                    mMessages.addAll(content_list);
                    mAdapter.notifyDataSetChanged();
                    if (mFirstLoad) {
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
                } else {
                    // something went wrong
                }
            }
        });
//        query.findInBackground(new FindCallback<Message>() {
//            public void done(List<Message> messages, ParseException e) {
//                if (e == null) {
//                    mMessages.clear();
//                    mTemp.clear();
//                    mMessages.addAll(messages);
//                    mAdapter.notifyDataSetChanged(); // update adapter
////                    // Scroll to the bottom of the list on initial load
//                    if (mFirstLoad) {
//                        lvChat.setSelection(mAdapter.getCount() - 1);
//                        mFirstLoad = false;
//                    }
//                } else {
//                    Log.d("message", "Error: " + e.getMessage());
//                }
//            }
//        });
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

    //
    public void firstQuery(String caller){
        // If this is called from a public ads_view_activity,
        // then query chat list using "Connection" key
        //temp_save.putString("ObjectID", "");
        temp_save.remove("ObjectID");
        if (caller.equals("Ads_view")) {
            query_msg.whereEqualTo("Connection", From_UserID + To_UserID + AdsID);
            query_msg.findInBackground(new FindCallback<Message>() {
                public void done(List<Message> object, ParseException e) {
                    if (e == null) {
                        // if there is already this object on Parse, then store the data
                        if (object.size() > 0) {
                            objectID = object.get(0).getObjectId().toString();
                            temp_save.putString("ObjectID", objectID);
                            //Toast.makeText(ChatActivity.this, objectID, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("message", "Error: " + e.getMessage());
                    }
                }
            });
            // Retrieve stored data
            // objectID = temp_save.getString("ObjectID");
            //Toast.makeText(ChatActivity.this, objectID , Toast.LENGTH_SHORT).show();
        }else{ // This chat box is called from slidemenu_message fragment
            // We just need to get objectID of current message box
            Toast.makeText(ChatActivity.this, "The caller is Message History" , Toast.LENGTH_SHORT).show();
            objectID = extras.getString("MessageID");
            temp_save.putString("ObjectID", objectID);
        }
    }
}