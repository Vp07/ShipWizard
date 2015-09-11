package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trongnghia.shipwizard_v11.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Slidemenu_Search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Slidemenu_Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Slidemenu_Search extends Fragment {

    // TODO: Rename and change types and number of parameters
    public static Slidemenu_Search newInstance() {
        Slidemenu_Search fragment = new Slidemenu_Search();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.slidemenu_search_fragment, container, false);
        return view;
    }
}
