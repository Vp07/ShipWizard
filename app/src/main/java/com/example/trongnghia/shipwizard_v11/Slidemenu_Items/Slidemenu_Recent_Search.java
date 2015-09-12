package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trongnghia.shipwizard_v11.NewTransaction.PageAdapter_View;
import com.example.trongnghia.shipwizard_v11.NewTransaction.PagerAdapter;
import com.example.trongnghia.shipwizard_v11.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Slidemenu_Recent_Search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Slidemenu_Recent_Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Slidemenu_Recent_Search extends Fragment {

    public static Slidemenu_Recent_Search newInstance(){
        Slidemenu_Recent_Search fragment = new Slidemenu_Recent_Search();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.slidemenu_recent_search_fragment, container, false);
        return view;
    }
}
