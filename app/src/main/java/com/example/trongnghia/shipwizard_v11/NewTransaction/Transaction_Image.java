package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.R;
import com.parse.ParseUser;

/**
 * Created by HIEP on 9/9/2015.
 */
public class Transaction_Image extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View orderView = inflater.inflate(R.layout.fragment_transaction_image_list, container, false);
//
//        final ListView lv_capture_or_pick = (ListView) getView().findViewById(R.id.capture_or_pick);
//
//
//        lv_capture_or_pick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                int selected_item = lv_capture_or_pick.getSelectedItemPosition();
//                //Toast.makeText(getContext(), "Item selected : "+ selected_item, Toast.LENGTH_SHORT).show();
//                Log.d("checking", "abc");
//            }
//        });
        return orderView;
    }
 
}
