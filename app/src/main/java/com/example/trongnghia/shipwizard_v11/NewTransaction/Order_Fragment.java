package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.R;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    Button uploadImage;
    Button preView;
    Button post;

    TextView Pre_buyer_place;
    TextView Pre_carrier_place;
    TextView Pre_item;
    TextView Pre_price;

    ListView Capture_or_pick;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CAMERA = 1;
    Uri imageUri;
    ImageView imageView;
    String mCurrentPhotoPath;

    ParseUser current_user;
    String userID;
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

        current_user = ParseUser.getCurrentUser();
        userID = current_user.getObjectId();

        return orderView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bUpload_Image:
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
                Order_post.put("UserID", userID);
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

        imageView = (ImageView)getActivity().findViewById(R.id.Order_imageView);

        if (resultCode == getActivity().RESULT_OK && requestCode == PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            String[] projection = { MediaStore.MediaColumns.DATA };
            Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null,
                    null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            String selectedImagePath = cursor.getString(column_index);
            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(selectedImagePath, options);
            imageView.setImageBitmap(bm);



        }
        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK) {
            openGallery();
        }
    }

    //end method for picking image from gallery

}
