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
public class Order_Fragment_View extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View orderView = inflater.inflate(R.layout.fragment__order_view, container, false);
        return orderView;
    }
}
