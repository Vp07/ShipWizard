package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.R;



/**
 * Created by HIEP on 9/15/2015.
 */
public class Slidemenu_Profile extends Fragment {
    private de.hdodenhof.circleimageview.CircleImageView profile_image_view;
    private TextView change_image_view;
    private TextView name_view;
    private TextView change_name_view;
    private TextView password_view;
    private TextView change_password_view;
    private TextView email_view;
    private TextView change_email_view;

    private FrameLayout FL_change_image;
    private FrameLayout FL_change_name;
    private  FrameLayout FL_change_password;
    private FrameLayout FL_change_email;

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
        profile_image_view = (de.hdodenhof.circleimageview.CircleImageView) getActivity().findViewById(R.id.slidemenu_profile_image);
        change_image_view = (TextView)getActivity().findViewById(R.id.slidemenu_profile_changeAvatar);
        name_view = (TextView) getActivity().findViewById(R.id.slidemenu_profile_name);
        change_name_view = (TextView) getActivity().findViewById(R.id.slidemenu_profile_changeName);
        password_view = (TextView) getActivity().findViewById(R.id.slidemenu_profile_password);
        change_password_view = (TextView)getActivity().findViewById(R.id.slidemenu_profile_changePassword);
        email_view = (TextView) getActivity().findViewById(R.id.slidemenu_profile_email);
        change_email_view = (TextView) getActivity().findViewById(R.id.slidemenu_profile_changeEmail);

        FL_change_image = (FrameLayout) getActivity().findViewById(R.id.framelayout_change_avatar);
        FL_change_name = (FrameLayout) getActivity().findViewById(R.id.framelayout_change_name);
        FL_change_password = (FrameLayout) getActivity().findViewById(R.id.framelayout_change_password);
        FL_change_email = (FrameLayout)getActivity(). findViewById(R.id.framelayout_change_email);

        FL_change_image.setOnClickListener((View.OnClickListener) this);
        FL_change_name.setOnClickListener((View.OnClickListener)this);
        FL_change_password.setOnClickListener((View.OnClickListener)this);
        FL_change_email.setOnClickListener((View.OnClickListener)this);

        return view;
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
}
