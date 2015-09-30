package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.trongnghia.shipwizard_v11.LogIn.DispatchActivity;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserAction;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    EditText title;
    EditText description;

    ImageView uploadImage;
    Button preView;
    Button post;

    TextView Pre_buyer_place;
    TextView Pre_carrier_place;
    TextView Pre_item;
    TextView Pre_price;

    ImageView pre_order_image;
    Bitmap bm;
    ImageAdapter bm_adapter;
    int global_position;
    GridView gv;
    TextView tv;
    Bitmap[] bm_for_upload = new Bitmap[10];


    ListView Capture_or_pick;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CAMERA = 1;
    Uri imageUri;

    String mCurrentPhotoPath;

    String post_message = "Your message has been successfully posted on the DashBoard";
    public ParseObject user_post = new ParseObject("UserPost");

    public List<String> list_temp = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View orderView = inflater.inflate(R.layout.fragment_order, container, false);

        buyer_place = (EditText)orderView.findViewById(R.id.etCurrent_place);
        carrier_place = (EditText)orderView.findViewById(R.id.etOrder_place);
        item = (EditText)orderView.findViewById(R.id.etOrder_item);
        price = (EditText)orderView.findViewById(R.id.etOrder_price);
        title = (EditText)orderView.findViewById(R.id.etTitle);
        description = (EditText)orderView.findViewById(R.id.etDescription);

        uploadImage = (ImageView)orderView.findViewById(R.id.bUpload_Image);
        preView = (Button)orderView.findViewById(R.id.bPreview);
        post = (Button)orderView.findViewById(R.id.bPost);

        uploadImage.setOnClickListener(this);
        preView.setOnClickListener(this);
        post.setOnClickListener(this);
        bm_adapter = new ImageAdapter(getActivity());

        return orderView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bUpload_Image:
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
                        dialog.onBackPressed();
                    }
                });
                ivDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i=0;i<9;i++) {
                            if(bm_adapter.bm[global_position]!=null) {
                                uploadImage.setImageBitmap(bm_adapter.bm[global_position]);
                                break;
                            }

                        }
                        dialog.onBackPressed();
                    }
                });
                break;

            // Preview a post
            case R.id.bPreview:
                //set up dialog
                //final Dialog dialog = new Dialog(getActivity(),R.style.PreviewDialog);
                final Dialog dialog_preview = new Dialog(getActivity());
                dialog_preview.setContentView(R.layout.ads_view);

                dialog_preview.setTitle("Preview this Message");
                dialog_preview.setCancelable(true);
                dialog_preview.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                final ViewFlipper vfAds_Img = (ViewFlipper) dialog_preview.findViewById(R.id.viewFlipper_Ads_Img);
                for(int i=0;i<9;i++){
                    if(bm_adapter.bm[i]!=null){
                        ImageView iv = new ImageView(dialog_preview.getContext());
                        iv.setImageBitmap(bm_adapter.bm[i]);;
                        vfAds_Img.addView(iv);
                    }
                }
                int DisplayPostion = vfAds_Img.getDisplayedChild()+1;
                tv = (TextView) dialog_preview.findViewById(R.id.ItemNo);
                tv.setText("Image " + DisplayPostion + " / " + vfAds_Img.getChildCount());

                ImageView ivNext = (ImageView) dialog_preview.findViewById(R.id.vf_next);
                ImageView ivPrevious = (ImageView) dialog_preview.findViewById(R.id.vf_previous);

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
            case R.id.bPost:

                // Post this Ads to Post class
                Calendar time = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(time.getTime());
                user_post.put("UserID", UserInfo.userID);
                user_post.put("UserName", UserInfo.username);
                user_post.put("Ads_Type", "Order");
                user_post.put("Title", title.getText().toString());
                user_post.put("Buyer_place", buyer_place.getText().toString());
                user_post.put("Carrier_place", carrier_place.getText().toString());
                user_post.put("Item", item.getText().toString());
                user_post.put("Price", price.getText().toString());
                user_post.put("Time", formattedDate);
                user_post.put("Description", description.getText().toString());
                // upload image
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                for(int i =0;i<9;i++) {
                    if(bm_adapter.bm[i]!=null){
                        bm_adapter.bm[i].compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        final ParseFile image_of_item = new ParseFile(byteArray);
                        image_of_item.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                user_post.addUnique("Images",image_of_item);
                                Log.d("checking", "Images uploaded!");
                            }
                        });
                    }
                }
                //post to Parse
                user_post.saveInBackground();
                Toast.makeText(getActivity(),post_message,Toast.LENGTH_SHORT).show();

                // Come back to home
                Intent intent = new Intent(getActivity(), View_Transaction.class);
                startActivity(intent);
                break;
        }
    }
    private void TakePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

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
