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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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

    ImageView ship_upload_image;
    Button ship_preview;
    Button ship_post;

    ListView Capture_or_pick;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CAMERA = 1;
    Uri imageUri;
    ImageView imageView;
    String mCurrentPhotoPath;
    ImageAdapter bm_adapter;
    GridView gv;
    int global_position;
    TextView tv;
    int count;

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

        ship_upload_image = (ImageView)shipView.findViewById(R.id.bShip_upload);
        ship_preview = (Button)shipView.findViewById(R.id.bShip_preview);
        ship_post = (Button)shipView.findViewById(R.id.bShip_post);

        ship_upload_image.setOnClickListener(this);
        ship_preview.setOnClickListener(this);
        ship_post.setOnClickListener(this);

        current_user = ParseUser.getCurrentUser();
        userID = current_user.getObjectId();
        bm_adapter = new ImageAdapter(getActivity());

        return shipView;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bShip_upload:
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.gridview_image_select);
                dialog.setTitle("Select your item image! ");
                gv = (GridView) dialog.findViewById(R.id.gridView_image);
                bm_adapter.setContext(dialog.getContext());
                bm_adapter.setNumColumn(3);
                gv.setAdapter(bm_adapter);
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        global_position = position;
                        if (bm_adapter.bm[position] != null) {

                            final Dialog dialog_change_or_delete = new Dialog(dialog.getContext());
                            dialog_change_or_delete.setContentView(R.layout.fragment_transaction_image_list);
                            List<String> items = new ArrayList<String>();
                            items.add("Choose another photo!");
                            items.add("Remove this one!");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
                            Capture_or_pick = (ListView) dialog_change_or_delete.findViewById(R.id.capture_or_pick);
                            Capture_or_pick.setAdapter(adapter);
                            Capture_or_pick.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position_1, long id) {
                                    //use POSITION to get item clicked
                                    if (position_1 == 0) {
                                        //Toast.makeText(getActivity(), "Capture photo",Toast.LENGTH_SHORT).show();
                                        openGallery();
                                        dialog_change_or_delete.onBackPressed();
                                    }
                                    if (position_1 == 1) {
                                        //Toast.makeText(getActivity(), "Pick photo form gallery",Toast.LENGTH_SHORT).show();
                                        RemovePhoto(global_position);
                                        dialog_change_or_delete.onBackPressed();
                                    }

                                }
                            });
                            dialog_change_or_delete.show();
                        } else {
                            final Dialog dialog_image = new Dialog(getActivity());
                            dialog_image.setContentView(R.layout.fragment_transaction_image_list);
                            List<String> items = new ArrayList<String>();
                            items.add("Capture a photo!");
                            items.add("Pick photo from galery!");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
                            Capture_or_pick = (ListView) dialog_image.findViewById(R.id.capture_or_pick);
                            Capture_or_pick.setAdapter(adapter);
                            Capture_or_pick.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    //use POSITION to get item clicked
                                    if (position == 0) {
                                        //Toast.makeText(getActivity(), "Capture photo",Toast.LENGTH_SHORT).show();
                                        TakePicture();
                                        dialog_image.onBackPressed();
                                    }
                                    if (position == 1) {
                                        //Toast.makeText(getActivity(), "Pick photo form gallery",Toast.LENGTH_SHORT).show();
                                        openGallery();
                                        dialog_image.onBackPressed();
                                    }

                                }
                            });
                            dialog_image.show();
                        }
                    }
                });
                dialog.show();
                ImageView ivCancel = (ImageView) dialog.findViewById(R.id.check_cancel);
                ImageView ivDone = (ImageView) dialog.findViewById(R.id.check_done);
                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
                ivDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < 9; i++) {
                            if (bm_adapter.bm[global_position] != null) {
                                ship_upload_image.setImageBitmap(bm_adapter.bm[global_position]);
                                break;
                            }

                        }
                        dialog.dismiss();
                    }
                });
                break;

            // Preview a post
            case R.id.bShip_preview:
                //set up dialog
                //final Dialog dialog = new Dialog(getActivity(),R.style.PreviewDialog);
                final Dialog dialog_preview = new Dialog(getActivity());
                dialog_preview.setContentView(R.layout.ads_view_public);

                dialog_preview.setTitle("Preview this Message");
                dialog_preview.setCancelable(true);
                dialog_preview.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                final ViewFlipper vfAds_Img = (ViewFlipper) dialog_preview.findViewById(R.id.viewFlipper_Ads_Img_Public);
                for(int i=0;i<9;i++){
                    if(bm_adapter.bm[i]!=null){
                        ImageView iv = new ImageView(dialog_preview.getContext());
                        iv.setImageBitmap(bm_adapter.bm[i]);;
                        vfAds_Img.addView(iv);
                    }
                }
                int DisplayPostion = vfAds_Img.getDisplayedChild()+1;
                tv = (TextView) dialog_preview.findViewById(R.id.ItemNo_Public);
                tv.setText("Image " + DisplayPostion + " / " + vfAds_Img.getChildCount());

                ImageView ivNext = (ImageView) dialog_preview.findViewById(R.id.vf_next_public);
                ImageView ivPrevious = (ImageView) dialog_preview.findViewById(R.id.vf_previous_public);

                ivNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vfAds_Img.showNext();
                        int DisplayPostion = vfAds_Img.getDisplayedChild()+1;
                        tv.setText("Image " + DisplayPostion + " / " + vfAds_Img.getChildCount());
                    }
                });

                ivPrevious.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vfAds_Img.showPrevious();
                        int DisplayPostion = vfAds_Img.getDisplayedChild()+1;
                        tv.setText("Image " + DisplayPostion + " / " + vfAds_Img.getChildCount());
                    }
                });

                //now that the dialog is set up, it's time to show it
                dialog_preview.show();
                break;

            // Post this order transaction to the Dashboard
            case R.id.bShip_post:
                Calendar time = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(time.getTime());
                user_post.put("UserID", userID);
                user_post.put("UserName", UserInfo.username);
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
                count = 1;


                for(int i =0;i<9;i++) {
                    if(bm_adapter.bm[i]!=null){
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bm_adapter.bm[i].compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        final ParseFile image_of_item = new ParseFile(byteArray);
                        user_post.put("Image_"+count,image_of_item);
                        count++;
                    }

                }
                //post to Parse
                // user_post.put("Images", Image_list);
                // user_post.put("test_column",tesst);
                user_post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(getActivity(), post_message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), View_Transaction.class);
                        startActivity(intent);
                    }
                });
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

        imageView = (ImageView)getActivity().findViewById(R.id.bShip_upload);

        if (resultCode == getActivity().RESULT_OK && requestCode == PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                //bm_for_upload[global_position] = bm;
                bm_adapter.bm[global_position] = ThumbnailUtils.extractThumbnail(bm, 500, 500);
                bm_adapter.notifyDataSetChanged();
                gv.invalidateViews();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // imageView.setImageBitmap(bm);



        }
        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK) {
            openGallery();
        }
    }
    public void RemovePhoto(int position){
        bm_adapter.bm[position] = null;
    }
}
