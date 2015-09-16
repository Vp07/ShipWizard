package com.example.trongnghia.shipwizard_v11.Library;

import android.app.Application;

import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Recent_Search_Item;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Trong Nghia on 9/16/2015.
 */
public class Recent_search_saver extends Application {
    public static ArrayList<Slidemenu_Recent_Search_Item> search_array;
    public static String search_string;
   // Gson gson;

    public Recent_search_saver(){
        search_array = new ArrayList<Slidemenu_Recent_Search_Item>();
       // search_array.add(item);
       // gson = new Gson();
    }

    public void set_items(Slidemenu_Recent_Search_Item item){
        search_array.add(item);

    }

    public ArrayList<Slidemenu_Recent_Search_Item> get_items(){
        return search_array;
    }
}
