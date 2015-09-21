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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.LogIn.DispatchActivity;
import com.example.trongnghia.shipwizard_v11.R;
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

    Button uploadImage;
    Button preView;
    Button post;

    TextView Pre_buyer_place;
    TextView Pre_carrier_place;
    TextView Pre_item;
    TextView Pre_price;

    ImageView pre_order_image;
    Bitmap bm;

    ListView Capture_or_pick;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CAMERA = 1;
    Uri imageUri;
    ImageView imageView;
    String mCurrentPhotoPath;

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


                //dialog_image.

                dialog_image.show();
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

                pre_order_image = (ImageView) dialog.findViewById(R.id.pre_order_image);
                pre_order_image.setImageBitmap(bm);

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
                Calendar time = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(time.getTime());
                user_post.put("UserID", UserInfo.userID);
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
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                ParseFile image_of_item = new ParseFile(byteArray);
                user_post.put("img",image_of_item);
                //post to Parse
                user_post.saveInBackground();
                Toast.makeText(getActivity(),post_message,Toast.LENGTH_SHORT).show();
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

        imageView = (ImageView)getActivity().findViewById(R.id.Order_imageView);

        if (resultCode == getActivity().RESULT_OK && requestCode == PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImageUri);
                Bitmap bm_for_show = ThumbnailUtils.extractThumbnail(bm, 500, 500);
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
