package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.LogIn.DispatchActivity;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class Order_Fragment extends Fragment implements View.OnClickListener {

    EditText buyer_place;
    EditText carrier_place;
    EditText item;
    EditText price;

    Button uploadImage;
    Button preView;
    Button post;

    TextView Pre_buyer_place;
    TextView Pre_carrier_place;
    TextView Pre_item;
    TextView Pre_price;


    String post_message = "Your message has been successfully posted on the DashBoard";
    public ParseObject Order_post = new ParseObject("OrderPost");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View orderView = inflater.inflate(R.layout.fragment_order, container, false);

        buyer_place = (EditText)orderView.findViewById(R.id.etCurrent_place);
        carrier_place = (EditText)orderView.findViewById(R.id.etOrder_place);
        item = (EditText)orderView.findViewById(R.id.etOrder_item);
        price = (EditText)orderView.findViewById(R.id.etOrder_price);

        uploadImage = (Button)orderView.findViewById(R.id.bUpload_Image);
        preView = (Button)orderView.findViewById(R.id.bPreview);
        post = (Button)orderView.findViewById(R.id.bPost);

        uploadImage.setOnClickListener(this);
        preView.setOnClickListener(this);
        post.setOnClickListener(this);

        return orderView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bUpload_Image:
                //Toast.makeText(getActivity(), UserInfo.username,Toast.LENGTH_SHORT).show();
                break;

            // Preview a post
            case R.id.bPreview:
                //set up dialog
                //final Dialog dialog = new Dialog(getActivity(),R.style.PreviewDialog);
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.order_preview_dialog);

                dialog.setTitle("Preview this Message");
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                //set up text content for preview
                Pre_buyer_place = (TextView) dialog.findViewById(R.id.etPre_current_place);
                Pre_carrier_place = (TextView) dialog.findViewById(R.id.etPre_order_place);
                Pre_item = (TextView) dialog.findViewById(R.id.etPre_order_item);
                Pre_price =(TextView) dialog.findViewById(R.id.etPre_order_price);

                Pre_buyer_place.setText(buyer_place.getText().toString());
                Pre_carrier_place.setText(carrier_place.getText().toString());
                Pre_item.setText(item.getText().toString());
                Pre_price.setText(price.getText().toString());

                //set up button
                Button button = (Button) dialog.findViewById(R.id.Button01);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                dialog.show();
                break;

            // Post this order transaction to the Dashboard
            case R.id.bPost:
                Order_post.put("UserID", UserInfo.userID);
                Order_post.put("Buyer_place", buyer_place.getText().toString());
                Order_post.put("Carrier_place", carrier_place.getText().toString());
                Order_post.put("Item", item.getText().toString());
                Order_post.put("Price", price.getText().toString());
                Order_post.saveInBackground();
                Toast.makeText(getActivity(),post_message,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NewTrans_Option.class);
                startActivity(intent);
                break;
        }
    }
}
