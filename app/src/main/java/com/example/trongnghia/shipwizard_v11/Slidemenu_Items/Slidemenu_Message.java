package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.trongnghia.shipwizard_v11.R;

public class Slidemenu_Message extends Fragment {

    public static Slidemenu_Message newInstance(){
        Slidemenu_Message fragment = new Slidemenu_Message();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.slidemenu_message, container, false);
        return view;
    }
}
