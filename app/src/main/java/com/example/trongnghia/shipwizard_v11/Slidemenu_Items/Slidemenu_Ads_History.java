package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trongnghia.shipwizard_v11.R;

public class Slidemenu_Ads_History extends Fragment {

    public static Slidemenu_Ads_History newInstance(){
        Slidemenu_Ads_History fragment = new Slidemenu_Ads_History();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.slidemenu_ads_history, container, false);
        return view;
    }
}
