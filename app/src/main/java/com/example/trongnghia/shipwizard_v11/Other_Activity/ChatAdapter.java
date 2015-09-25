package com.example.trongnghia.shipwizard_v11.Other_Activity;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

/**
 * Created by Trong Nghia on 9/23/2015.
 */
public class ChatAdapter extends ArrayAdapter<Message> {

    private String FromUserID;

    public ChatAdapter(Context context, String FromUserID, List<Message> messages) {
        super(context, 0, messages);
        this.FromUserID = FromUserID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.send__message_list_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.imageLeft = (ImageView)convertView.findViewById(R.id.ivProfileLeft);
            holder.imageRight = (ImageView)convertView.findViewById(R.id.ivProfileRight);
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