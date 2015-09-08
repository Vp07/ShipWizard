package com.example.trongnghia.shipwizard_v11.NewTransaction;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.R;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ship_Fragment extends Fragment implements View.OnClickListener {

    EditText order_place;
    EditText carrier_place;
    EditText ship_item;
    EditText ship_price;
    EditText ship_time;

    TextView Pre_ship_place;
    TextView Pre_destination_place;
    TextView Pre_ship_item;
    TextView Pre_ship_price;
    TextView Pre_ship_time;

    Button ship_upload_image;
    Button ship_preview;
    Button ship_post;

    ParseUser current_user;
    String userID;
    String post_message = "Your message has been successfully posted on the DashBoard";
    public ParseObject Ship_post = new ParseObject("ShipPost");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View shipView = inflater.inflate(R.layout.fragment_ship, container, false);

        order_place = (EditText)shipView.findViewById(R.id.etCurrent_place);
        carrier_place = (EditText)shipView.findViewById(R.id.etShip_place);
        ship_item = (EditText)shipView.findViewById(R.id.etShip_item);
        ship_price = (EditText)shipView.findViewById(R.id.etShip_price);
        ship_time = (EditText) shipView.findViewById(R.id.etShip_time);

        ship_upload_image = (Button)shipView.findViewById(R.id.bShip_upload);
        ship_preview = (Button)shipView.findViewById(R.id.bShip_preview);
        ship_post = (Button)shipView.findViewById(R.id.bShip_post);

        ship_upload_image.setOnClickListener(this);
        ship_preview.setOnClickListener(this);
        ship_post.setOnClickListener(this);

        current_user = ParseUser.getCurrentUser();
        userID = current_user.getObjectId();

        return shipView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bShip_upload:
                break;

            // Preview a post
            case R.id.bShip_preview:
                //set up dialog
                //final Dialog dialog = new Dialog(getActivity(),R.style.PreviewDialog);
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.ship_preview_dialog);
                dialog.setTitle("Preview this Message");
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                //setup preview ship information
                Pre_ship_place = (TextView) dialog.findViewById(R.id.etPre_curent_info);
                Pre_destination_place = (TextView) dialog.findViewById(R.id.etPre_destination_info);
                Pre_ship_item = (TextView) dialog.findViewById(R.id.etPre_item_info);
                Pre_ship_price = (TextView) dialog.findViewById(R.id.etPre_price_info);
                Pre_ship_time = (TextView) dialog.findViewById(R.id.etPre_time_info);

                Pre_ship_place.setText(order_place.getText().toString());
                Pre_destination_place.setText(carrier_place.getText().toString());
                Pre_ship_item.setText(ship_item.getText().toString());
                Pre_ship_price.setText(ship_price.getText().toString());
                Pre_ship_time.setText(ship_time.getText().toString());

                //set up button
                Button button = (Button)dialog.findViewById(R.id.Button01);
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                dialog.show();
                break;

            // Post this order transaction to the Dashboard
            case R.id.bShip_post:
                Ship_post.put("UserID", userID);
                Ship_post.put("Order_place", order_place.getText().toString());
                Ship_post.put("Carrier_place", carrier_place.getText().toString());
                Ship_post.put("Ship_Item", ship_item.getText().toString());
                Ship_post.put("Ship_Price", ship_price.getText().toString());
                Ship_post.saveInBackground();
                Toast.makeText(getActivity(), post_message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NewTrans_Option.class);
                startActivity(intent);
                break;
        }
    }
}
