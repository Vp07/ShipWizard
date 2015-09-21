package com.example.trongnghia.shipwizard_v11.NewTransaction;


import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.R;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ship_Fragment extends Fragment implements View.OnClickListener {

    EditText title;
    EditText order_place;
    EditText carrier_place;
    EditText ship_item;
    EditText ship_price;
    EditText ship_time;
    EditText description;

    TextView Pre_shipper_place;
    TextView Pre_destination_place;
    TextView Pre_item;
    TextView Pre_price;
    TextView Pre_time;

    ImageView pre_ship_image;
    Bitmap bm;

    Button ship_upload_image;
    Button ship_preview;
    Button ship_post;

    ListView Capture_or_pick;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CAMERA = 1;
    Uri imageUri;
    ImageView imageView;
    String mCurrentPhotoPath;

    ParseUser current_user;
    String userID;
    String post_message = "Your message has been successfully posted on the DashBoard";
    public ParseObject user_post = new ParseObject("UserPost");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View shipView = inflater.inflate(R.layout.fragment_ship, container, false);

        title = (EditText)shipView.findViewById(R.id.etTitle);
        order_place = (EditText)shipView.findViewById(R.id.etCurrent_place);
        carrier_place = (EditText)shipView.findViewById(R.id.etShip_place);
        ship_item = (EditText)shipView.findViewById(R.id.etShip_item);
        ship_price = (EditText)shipView.findViewById(R.id.etShip_price);
        ship_time = (EditText) shipView.findViewById(R.id.etShip_time);
        description = (EditText)shipView.findViewById(R.id.etDescription);

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
                final Dialog dialog_image = new Dialog(getActivity());
                dialog_image.setContentView(R.layout.fragment_transaction_image_list);
                List<String> items = new ArrayList<String>();
                items.add("Capture a photo!");
                items.add("Pick photo from galery!");


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items );
                Capture_or_pick = (ListView) dialog_image.findViewById(R.id.capture_or_pick);
                Capture_or_pick.setAdapter(adapter);
                Capture_or_pick.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        //use POSITION to get item clicked
                        if(position==0){
                            //Toast.makeText(getActivity(), "Capture photo",Toast.LENGTH_SHORT).show();
                            TakePicture();
                            dialog_image.onBackPressed();
                        }
                        if(position ==1){
                            //Toast.makeText(getActivity(), "Pick photo form gallery",Toast.LENGTH_SHORT).show();
                            openGallery();
                            dialog_image.onBackPressed();
                        }

                    }
                });


                //dialog_image.

                dialog_image.show();
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
                //set up preview
                Pre_shipper_place = (TextView) dialog.findViewById(R.id.etPre_ship_text1);
                Pre_destination_place = (TextView) dialog.findViewById(R.id.etPre_ship_text2);
                Pre_item = (TextView) dialog.findViewById(R.id.etPre_ship_text3);
                Pre_price =(TextView) dialog.findViewById(R.id.etPre_ship_text4);
                Pre_time = (TextView) dialog.findViewById(R.id.etPre_ship_text5);
                pre_ship_image = (ImageView) dialog.findViewById(R.id.pre_ship_image);


                Pre_shipper_place.setText(order_place.getText().toString());
                Pre_destination_place.setText(carrier_place.getText().toString());
                Pre_item.setText(ship_item.getText().toString());
                Pre_price.setText(ship_price.getText().toString());
                Pre_time.setText(ship_time.getText().toString());
                pre_ship_image.setImageBitmap(bm);
                //end set up preview
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
                Calendar time = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(time.getTime());
                user_post.put("UserID", userID);
                user_post.put("Title", title.getText().toString());
                user_post.put("Ads_Type", "Ship");
                user_post.put("Buyer_place", order_place.getText().toString());
                user_post.put("Carrier_place", carrier_place.getText().toString());
                user_post.put("Item", ship_item.getText().toString());
                user_post.put("Price", ship_price.getText().toString());
                user_post.put("Time", formattedDate);
                user_post.put("Ship_time", ship_time.getText().toString());
                user_post.put("Description", description.getText().toString());

                //prepare image for upload
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                ParseFile image_of_item = new ParseFile(byteArray);
                user_post.put("img",image_of_item);

                // post to parse
                user_post.saveInBackground();
                Toast.makeText(getActivity(), post_message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), View_Transaction.class);
                startActivity(intent);
                break;
        }
    }
    //methods for capturing a photo by camera
    private void TakePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    //end methods for capturing a photo by camera
    // methods for picking image from gallery
    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageView = (ImageView)getActivity().findViewById(R.id.Ship_imageView);

        if (resultCode == getActivity().RESULT_OK && requestCode == PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImageUri);
                Bitmap bm_for_show = ThumbnailUtils.extractThumbnail(bm,500,500);
                imageView.setImageBitmap(bm_for_show);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // imageView.setImageBitmap(bm);



        }
        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK) {
            openGallery();
        }
    }
}
