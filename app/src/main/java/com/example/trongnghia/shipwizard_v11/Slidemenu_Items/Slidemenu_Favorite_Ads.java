package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trongnghia.shipwizard_v11.R;


public class Slidemenu_Favorite_Ads extends Fragment {

    // TODO: Rename and change types and number of parameters
    public static Slidemenu_Favorite_Ads newInstance() {
        Slidemenu_Favorite_Ads fragment = new Slidemenu_Favorite_Ads();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.slidemenu_favorite_ads_fragment, container, false);
        return view;
    }
}
