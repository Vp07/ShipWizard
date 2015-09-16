package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.LogIn.DispatchActivity;
import com.example.trongnghia.shipwizard_v11.NewTransaction.View_Transaction;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by HIEP on 9/15/2015.
 */
public class Slidemenu_Profile extends Fragment implements View.OnClickListener{
    public de.hdodenhof.circleimageview.CircleImageView profile_image_view;
    private TextView change_image_view;
    private TextView name_view;
    private TextView change_name_view;
    private TextView password_view;
    private TextView change_password_view;
    private TextView email_view;
    private TextView change_email_view;

    private FrameLayout FL_change_image;
    private FrameLayout FL_change_name;
    private FrameLayout FL_change_password;
    private FrameLayout FL_change_email;

    private Button Save_profile;

    ParseFile avatar_for_upload;
    Dialog dialog;

    // TODO: Rename and change types and number of parameters
    public static Slidemenu_Profile newInstance() {
        Slidemenu_Profile fragment = new Slidemenu_Profile();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.slidemenu_profile, container, false);
        profile_image_view = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.slidemenu_profile_image);

        name_view = (TextView) view.findViewById(R.id.slidemenu_profile_name);

        password_view = (TextView) view.findViewById(R.id.slidemenu_profile_password);

        email_view = (TextView) view.findViewById(R.id.slidemenu_profile_email);




        FL_change_image = (FrameLayout) view.findViewById(R.id.framelayout_change_avatar);
        FL_change_name = (FrameLayout) view.findViewById(R.id.framelayout_change_name);
        FL_change_password = (FrameLayout) view.findViewById(R.id.framelayout_change_password);
        FL_change_email = (FrameLayout)view. findViewById(R.id.framelayout_change_email);

        FL_change_image.setOnClickListener(this);
        FL_change_name.setOnClickListener(this);
        FL_change_password.setOnClickListener(this);
        FL_change_email.setOnClickListener(this);

        Save_profile = (Button) view.findViewById(R.id.button_save_profile);
        Save_profile.setOnClickListener(this);


            ParseFile avatar = DispatchActivity.current_user.getAvatar();

            avatar.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null) {

                        Bitmap bmp = BitmapFactory
                                .decodeByteArray(data, 0, data.length);
                        Bitmap bm_for_show = ThumbnailUtils.extractThumbnail(bmp, 500, 500);
                        profile_image_view.setImageBitmap(bm_for_show);
                    }
                }
            });

           // profile_image_view.setImageURI(DispatchActivity.current_user.getAvatar().getData());


        name_view.setText(UserInfo.username);
        email_view.setText(UserInfo.email);




        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.framelayout_change_avatar:
                Toast.makeText(getActivity(), "Change avatar", Toast.LENGTH_SHORT).show();
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_transaction_image_list);
                dialog.setTitle("Select your photo");
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                List<String> items = new ArrayList<String>();
                items.add("Capture a photo!");
                items.add("Pick photo from galery!");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
                ListView Capture_or_pick = (ListView) dialog.findViewById(R.id.capture_or_pick);
                Capture_or_pick.setAdapter(adapter);
                Capture_or_pick.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        //use POSITION to get item clicked
                        if (position == 0) {
                            //Toast.makeText(getActivity(), "Capture photo",Toast.LENGTH_SHORT).show();
                            TakePicture();
                            dialog.onBackPressed();
                        }
                        if (position == 1) {
                            //Toast.makeText(getActivity(), "Pick photo form gallery",Toast.LENGTH_SHORT).show();
                            openGallery();
                            dialog.onBackPressed();
                        }

                    }
                });
                dialog.show();
                break;
            case R.id.framelayout_change_name:
                Toast.makeText(getActivity(), "Change name", Toast.LENGTH_SHORT).show();
                break;
            case R.id.framelayout_change_password:
                Toast.makeText(getActivity(), "Change password", Toast.LENGTH_SHORT).show();
                break;
            case R.id.framelayout_change_email:
                Toast.makeText(getActivity(), "Change email", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_save_profile:
                Toast.makeText(getActivity(), "save", Toast.LENGTH_SHORT).show();
                DispatchActivity.current_user.PrepareForUpload("img", avatar_for_upload);
                DispatchActivity.current_user.UploadtoParse();
                startActivity(new Intent(getContext(), View_Transaction.class));
                break;
        }
    }

    private void TakePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        startActivityForResult(gallery, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        de.hdodenhof.circleimageview.CircleImageView Avatar = (de.hdodenhof.circleimageview.CircleImageView) getActivity().findViewById(R.id.slidemenu_profile_image);

        if (resultCode == getActivity().RESULT_OK && requestCode == 100) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                Bitmap bm_for_show = ThumbnailUtils.extractThumbnail(bm, 500, 500);
                Avatar.setImageBitmap(bm_for_show);

                //prepare the byte array to upload to Parse
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                avatar_for_upload = new ParseFile(byteArray);
//                try {
//                    avatar_for_upload.save();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            // imageView.setImageBitmap(bm);


        }
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            openGallery();
        }
    }
}


//    public void onClick(View view){
//        switch (view.getId()) {
//            case R.id.slidemenu_profile_changeAvatar:
//                Toast.makeText(getActivity(), "change Avatar", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.slidemenu_profile_changeName:
//                Toast.makeText(getActivity(), "change Name", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.slidemenu_profile_changePassword:
//                Toast.makeText(getActivity(), "change Password", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.slidemenu_profile_changeEmail:
//                Toast.makeText(getActivity(), "change Email", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }


