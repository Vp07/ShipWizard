package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
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
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Favorite_Ads;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

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


    Bitmap bm;
    Bitmap bm_for_upload[] = {null,null,null,null,null,null,null,null,null};
    GridView gridView;

    ListView Capture_or_pick;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CAMERA = 1;

    int global_position;
    ImageAdapter bm_adapter;
    int current_position;
    Bitmap AdjustedBitmapArray[];


    String post_message = "Your message has been successfully posted on the DashBoard";
    public ParseObject user_post = new ParseObject("UserPost");

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



        preView.setOnClickListener(this);
        post.setOnClickListener(this);
        uploadImage.setOnClickListener(this);

        bm_adapter= new ImageAdapter(getActivity());

        return orderView;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.bUpload_Image:
                //Toast.makeText(getActivity(), "Upload Image ",Toast.LENGTH_SHORT).show();

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.gridview_image_select);

                dialog.setTitle("Select image of your item");
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                gridView = (GridView) dialog.findViewById(R.id.gridView_image);
                ImageView Cancel_button = (ImageView) dialog.findViewById(R.id.check_cancel);
                ImageView Done_button = (ImageView) dialog.findViewById(R.id.check_done);



                bm_adapter.setContext(dialog.getContext());
                bm_adapter.setNumColumn(3);
                gridView.setAdapter(bm_adapter);
                Cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.onBackPressed();
                    }
                });
                Done_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0; i<10;i++){
                            if(bm_adapter.bm[i]!=null){
                                uploadImage.setImageBitmap(bm_adapter.bm[i]);
                                break;
                            }



                        }
                        dialog.onBackPressed();

                    }
                });
//                ImageAdapter adapter = new ImageAdapter(dialog.getContext());
//                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        //Toast.makeText(getActivity(), "pic" + (position + 1) + "selected", Toast.LENGTH_SHORT).show();
                        if(bm_adapter.bm[position]==null) {
                            global_position = position;
                            final Dialog dialog_select_image = new Dialog(getActivity());
                            dialog_select_image.setContentView(R.layout.fragment_transaction_image_list);
                            List<String> items = new ArrayList<String>();
                            items.add("Capture a photo!");
                            items.add("Pick photo from galery!");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(dialog_select_image.getContext(), android.R.layout.simple_list_item_1, items);
                            Capture_or_pick = (ListView) dialog_select_image.findViewById(R.id.capture_or_pick);
                            Capture_or_pick.setAdapter(adapter);
                            Capture_or_pick.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position_1, long id) {
                                    //use POSITION to get item clicked
                                    if (position_1 == 0) {
                                        //Toast.makeText(getActivity(), "Capture photo",Toast.LENGTH_SHORT).show();
                                        TakePicture();
                                        dialog_select_image.onBackPressed();
                                    }
                                    if (position_1 == 1) {
                                        //Toast.makeText(getActivity(), "Pick photo form gallery",Toast.LENGTH_SHORT).show();
                                        openGallery();
                                        dialog_select_image.onBackPressed();
                                    }

                                }
                            });
                            dialog_select_image.show();
                        }
                        else{
                            global_position = position;
                            Toast.makeText(getActivity(), "Image available!", Toast.LENGTH_SHORT).show();
                            final Dialog dialog_select_image = new Dialog(getActivity());
                            dialog_select_image.setContentView(R.layout.fragment_transaction_image_list);
                            List<String> items = new ArrayList<String>();
                            items.add("Choose another photo");
                            items.add("Remove this one");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(dialog_select_image.getContext(), android.R.layout.simple_list_item_1, items);
                            Capture_or_pick = (ListView) dialog_select_image.findViewById(R.id.capture_or_pick);
                            Capture_or_pick.setAdapter(adapter);
                            Capture_or_pick.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position_1, long id) {
                                    //use POSITION to get item clicked
                                    if (position_1 == 0) {
                                        //Toast.makeText(getActivity(), "Capture photo",Toast.LENGTH_SHORT).show();
                                        openGallery();
                                        dialog_select_image.onBackPressed();
                                    }
                                    if (position_1 == 1) {
                                        //Toast.makeText(getActivity(), "Pick photo form gallery",Toast.LENGTH_SHORT).show();
                                        bm_adapter.bm[global_position] = null;
                                        bm_adapter.notifyDataSetChanged();
                                        gridView.invalidateViews();
                                        dialog_select_image.onBackPressed();
                                    }

                                }
                            });
                            dialog_select_image.show();

                        }
                    }

                });
                dialog.show();
                break;

            // Preview a post
            case R.id.bPreview:
                //set up dialog
                final Dialog dialog_2 = new Dialog(getActivity());
                dialog_2.setContentView(R.layout.ads_view);

                dialog_2.setTitle("Preview this Message");
                dialog_2.setCancelable(true);

//                EditText preTitle = (EditText) getActivity().findViewById(R.id.etTitle);
//                EditText preCurrentPlace = (EditText) getActivity().findViewById(R.id.etCurrent_place);
//                EditText preOrderPlace = (EditText) getActivity().findViewById(R.id.etOrder_place);
//                EditText preOrderPrice = (EditText) getActivity().findViewById(R.id.etOrder_price);
//                EditText preOrderItem = (EditText) getActivity().findViewById(R.id.etOrder_item);
//                EditText preDescription = (EditText) getActivity().findViewById(R.id.etDescription);
//
//                TextView tvPreTitle = (TextView) dialog_2.findViewById(R.id.tvTitle);
//                TextView tvCurrentPlace = (TextView) dialog_2.findViewById(R.id.tvLocation);
//                TextView tvPreTitle = (TextView) dialog_2.findViewById(R.id.tvTitle);
//                TextView tvPreTitle = (TextView) dialog_2.findViewById(R.id.tvTitle);
//                TextView tvPreTitle = (TextView) dialog_2.findViewById(R.id.tvTitle);
//                TextView tvPreTitle = (TextView) dialog_2.findViewById(R.id.tvTitle);
//                TextView tvPreTitle = (TextView) dialog_2.findViewById(R.id.tvTitle);
                bm_adapter.setContext(dialog_2.getContext());


                final ViewFlipper vfAds_img = (ViewFlipper) dialog_2.findViewById(R.id.viewFlipper_Ads_Img);
                ImageView Next = (ImageView) dialog_2.findViewById(R.id.vf_next);
                ImageView Previous = (ImageView) dialog_2.findViewById(R.id.vf_previous);
                current_position = 1;

                Next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vfAds_img.showNext();
                       // current_position++;
                    }
                });
                Previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vfAds_img.showPrevious();
                    }
                });
                for(int i=0;i<9;i++){
                    if(bm_adapter.bm[i]!=null){
                        ImageView iv = new ImageView(dialog_2.getContext());
                        iv.setImageBitmap(bm_adapter.bm[i]);
                        vfAds_img.addView(iv);
                    }
                }

//                for (int i=0;i<10;i++){
//                    int j=0;
//                    if(bm_adapter.bm[i]!=null){
//                        AdjustedBitmapArray[j]=bm_adapter.bm[i];
//                        j++;
//                    }
//                }


                dialog_2.show();
                break;

            // Post this order transaction to the Dashboard
            case R.id.bPost:
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
                if(bm!=null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    ParseFile image_of_item = new ParseFile(byteArray);
                    user_post.put("img", image_of_item);
                    //post to Parse

                }

                user_post.saveInBackground();
                    Toast.makeText(getActivity(), post_message, Toast.LENGTH_SHORT).show();
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

        //imageView = (ImageView)getActivity().findViewById(R.id.Order_imageView);

        if (resultCode == getActivity().RESULT_OK && requestCode == PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            try {
                Toast.makeText(getActivity(), ""+global_position, Toast.LENGTH_SHORT).show();
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImageUri);
                bm_for_upload[global_position] = bm;
                Bitmap bm_for_show = ThumbnailUtils.extractThumbnail(bm, bm_adapter.Item_width, bm_adapter.Item_height);
               // imageView.setImageBitmap(bm_for_show);
                bm_adapter.bm[global_position] = bm_for_show;
                bm_adapter.notifyDataSetChanged();
                gridView.invalidateViews();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // imageView.setImageBitmap(bm);

        }
        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK) {
            openGallery();
        }
    }
    public int getCurrentDisplayPosition(){
        return current_position;
    }

}
