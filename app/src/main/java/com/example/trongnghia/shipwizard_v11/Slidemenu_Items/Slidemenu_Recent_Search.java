package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.trongnghia.shipwizard_v11.Library.TinyDB;
import com.example.trongnghia.shipwizard_v11.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Slidemenu_Recent_Search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Slidemenu_Recent_Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Slidemenu_Recent_Search extends Fragment {

    public SharedPreferences sp;
    public static ArrayList<Slidemenu_Recent_Search_Item> items;
    public ArrayList<Slidemenu_Recent_Search_Item> array;
    public ArrayList<String> recent_search_string;

    public Slidemenu_Recent_Search_Item temp;
    public String recent_search_jason;
    public Gson gson = new Gson();
    public static final String Search= "Search";
    public static TinyDB tinydb;

    public static Slidemenu_Recent_Search newInstance(){
        Slidemenu_Recent_Search fragment = new Slidemenu_Recent_Search();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.slidemenu_recent_search_fragment, container, false);
        sp = this.getActivity().getSharedPreferences(Search, 0);
        items = new ArrayList<Slidemenu_Recent_Search_Item>();
        tinydb = new TinyDB(getActivity());
        recent_search_string = new ArrayList<String>();
        // Pass context and data to the custom adapter
        Slidemenu_Recent_Search_Adapter adapter = new Slidemenu_Recent_Search_Adapter(getActivity(), generateData());
        // Get ListView from activity_main.xml
        ListView listView = (ListView) view.findViewById(R.id.lvRecent_Search);
        // SetListAdapter
        listView.setAdapter(adapter);
        return view;
    }

    private ArrayList<Slidemenu_Recent_Search_Item> generateData(){
        recent_search_jason = sp.getString("RecentSearch", "");
        recent_search_string = tinydb.getListString("RecentSearch");
       // Type type = new TypeToken<ArrayList<Slidemenu_Recent_Search_Item>>(){}.getType();
        Type type = new TypeToken<Slidemenu_Recent_Search_Item>(){}.getType();
        for (int i=0;i<recent_search_string.size();i++){
            temp = gson.fromJson(recent_search_string.get(i), type);
            items.add(new Slidemenu_Recent_Search_Item(temp.getAds_type(),
                    temp.getCategory(),
                    temp.getTime(),
                    temp.getLocation()));
        }
        return items;
    }
}