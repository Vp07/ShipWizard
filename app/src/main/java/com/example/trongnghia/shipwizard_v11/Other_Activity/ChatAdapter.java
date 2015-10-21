package com.example.trongnghia.shipwizard_v11.Other_Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

/**
 * Created by Trong Nghia on 9/23/2015.
 */
public class ChatAdapter extends ArrayAdapter<Message> {

    private String FromUserID;
    ParseQuery<ParseObject> query;
    ParseObject FromUser;
    Bitmap avatar_other_user;
    Bitmap avatar_current_user;

    public ChatAdapter(Context context, final String FromUserID, List<Message> messages) {
        super(context, 0, messages);
        this.FromUserID = FromUserID;
        UserInfo current_user = new UserInfo();
        // get avatar from current user
        ParseFile temp_file_1 = current_user.getAvatar();
        if(temp_file_1!=null){
            try {
                byte[] data = temp_file_1.getData();
                if(data!=null){
                    avatar_other_user = BitmapFactory.decodeByteArray(data, 0, data.length);
                }
            } catch (ParseException e_) {
                e_.printStackTrace();
            }
        }

        //get avatar of other user
        query = ParseQuery.getQuery("User");
        query.whereEqualTo("objectId", FromUserID);
        try {
            ParseObject user = query.get(FromUserID);
            ParseFile temp_file_2 = user.getParseFile("img");
            if (temp_file_2 != null) {
                try {
                    byte[] data = temp_file_2.getData();
                    if (data != null) {
                        avatar_other_user = BitmapFactory.decodeByteArray(data, 0, data.length);
                    }
                } catch (ParseException e_) {
                    e_.printStackTrace();
                }
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }





    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.send__message_list_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.imageLeft = (ImageView)convertView.findViewById(R.id.ivProfileLeft);
            holder.imageRight = (ImageView)convertView.findViewById(R.id.ivProfileRight);
            if(avatar_current_user!=null){
                holder.imageRight.setImageBitmap(avatar_current_user);
            }
            if(avatar_other_user!=null){
                holder.imageLeft.setImageBitmap(avatar_other_user);
            }

            holder.content = (TextView)convertView.findViewById(R.id.tvMessage);
            convertView.setTag(holder);
        }
        final Message message = (Message)getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        final boolean isMe = message.getFromUserID().equals(FromUserID);

        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.

        if (isMe) {
            holder.imageRight.setVisibility(View.VISIBLE);
            holder.imageLeft.setVisibility(View.GONE);
            holder.content.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else {
            holder.imageLeft.setVisibility(View.VISIBLE);
            holder.imageRight.setVisibility(View.GONE);
            holder.content.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        final ImageView profileView = isMe ? holder.imageRight : holder.imageLeft;
        Picasso.with(getContext()).load(getProfileUrl(message.getFromUserID())).into(profileView);
        holder.content.setText(message.getContent());
        return convertView;
    }

    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

    final class ViewHolder {
        public ImageView imageLeft;
        public ImageView imageRight;
        public TextView content;
    }
}
