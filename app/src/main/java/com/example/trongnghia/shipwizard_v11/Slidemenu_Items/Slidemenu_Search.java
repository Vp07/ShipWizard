package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.Library.Recent_search_saver;
import com.example.trongnghia.shipwizard_v11.Library.TinyDB;
import com.example.trongnghia.shipwizard_v11.LogIn.SampleApplication;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.google.gson.Gson;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Slidemenu_Search extends Fragment implements View.OnClickListener{

    public FrameLayout ads_type, buyer_location, carrier_location, category, price_type, sort_by,
            condition, price_range;
    public TextView tvAds_type, tvCategory, tvPrice_type, tv_Price_range, tvSort, tvCondition,
                    tvOrder_place, tvShip_place;
    public EditText etMinimum, etMaximum;
    public Spinner currency;
    public Button price_range_done, search_reset, search_search;
    public View view;

    // Used to save parameter settings
    public SharedPreferences sp;
    public SharedPreferences.Editor preferencesEditor;

    public static final String Search= "Search";
    public String save_ads_type, save_category, save_time, save_location, temp ;
    public String formattedDate;

    public RadioGroup rg_Ads_Type, rg_Category, rg_Price_Type, rg_Sort, rg_Condition;
    private int Ads_type_index, Category_index, Price_type_index, Sort_index, Condition_index,
            Currency_index;
    public String price_min, price_max, currency_text;
    String post_message = "Your message has been successfully posted on the DashBoard";

    // Appear when user click search options
    public AlertDialog CustomDialog;

    // Send search data to Parse
    public ParseObject search = new ParseObject("UserSearch");

    public static TinyDB tinydb;
    public ArrayList<Slidemenu_Recent_Search_Item> array_items;
    public ArrayList<String> recent_search_string;
    public Slidemenu_Recent_Search_Item items;
    public Gson gson;
    public Calendar time;
    public int day;
    public int month;
    public int year;

    // TODO: Rename and change types and number of parameters
    public static Slidemenu_Search newInstance() {
        Slidemenu_Search fragment = new Slidemenu_Search();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.slidemenu_search_fragment, container, false);
        initialize();
        return view;
    }

    // Items click handler
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.flAds_Type:
                Ads_type_handler();
                break;

            case R.id.flCategory:
                Category_hadler();
                break;

            case R.id.flPrice_Type:
                PriceType_Handler();
                break;

            case R.id.flSort_By:
                Sort_handler();
                break;

            case R.id.flCondition:
                Condition_handler();
                break;

            case R.id.flPrice_Range:
                Price_range_handler();
                break;

            case R.id.bSearch_Reset:
                Reset_handler();
                break;

            case R.id.bSearch:
                Search_handler();
                break;
        }
    }

    void initialize(){
        // Save the search data for recent search fragment
        tinydb = new TinyDB(getActivity());
        array_items = new ArrayList<Slidemenu_Recent_Search_Item>();
        gson = new Gson();
        recent_search_string = new ArrayList<String>();
        time = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(time.getTime());
//        day = time.get(Calendar.DATE);
//        month = time.get(Calendar.MONTH);
//        year = time.get(Calendar.YEAR);

        // Restore preferences
        sp = this.getActivity().getSharedPreferences(Search, 0);
        preferencesEditor = sp.edit();

        // Ads_type
        ads_type = (FrameLayout)view.findViewById(R.id.flAds_Type);
        tvAds_type = (TextView)view.findViewById(R.id.tvAds_Type);
        ads_type.setOnClickListener(this);

        // Buyer Location
        tvOrder_place = (TextView)view.findViewById(R.id.tvBuyer_Location);

        // Carrier Location
        tvShip_place = (TextView)view.findViewById(R.id.tvCarrier_Location);

        // Category
        category = (FrameLayout)view.findViewById(R.id.flCategory);
        tvCategory = (TextView)view.findViewById(R.id.tvCategory);
        category.setOnClickListener(this);

        // Price Type
        price_type = (FrameLayout)view.findViewById(R.id.flPrice_Type);
        tvPrice_type = (TextView)view.findViewById(R.id.tvPrice_Type);
        price_type.setOnClickListener(this);

        // Price Range
        price_range = (FrameLayout)view.findViewById(R.id.flPrice_Range);
        tv_Price_range = (TextView)view.findViewById(R.id.tvPrice_Range);
        price_range.setOnClickListener(this);

        // Sort By
        sort_by = (FrameLayout)view.findViewById(R.id.flSort_By);
        tvSort = (TextView)view.findViewById(R.id.tvSort_By);
        sort_by.setOnClickListener(this);

        // Condition
        condition = (FrameLayout)view.findViewById(R.id.flCondition);
        tvCondition = (TextView)view.findViewById(R.id.tvCondition);
        condition.setOnClickListener(this);

        // Reset and Search buttons
        search_reset = (Button)view.findViewById(R.id.bSearch_Reset);
        search_search = (Button)view.findViewById(R.id.bSearch);
        search_reset.setOnClickListener(this);
        search_search.setOnClickListener(this);

    }

    // Ads_type option event handler
    void Ads_type_handler(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_search_ads_type, null, false);
        builder.setView(view1);
        builder.setTitle("Ads Type");
        rg_Ads_Type = (RadioGroup)view1.findViewById(R.id.rgAds_Type);
        Ads_type_index = sp.getInt("Ads_type",0);
        ((RadioButton)rg_Ads_Type.getChildAt(Ads_type_index)).setChecked(true);

        rg_Ads_Type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) rg_Ads_Type.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    tvAds_type.setText(rb.getText());
                    // Get the index of pressed radio button
                    int radioButtonID = rg_Ads_Type.getCheckedRadioButtonId();
                    View radioButton = rg_Ads_Type.findViewById(radioButtonID);
                    Ads_type_index = rg_Ads_Type.indexOfChild(radioButton);
                    preferencesEditor.putInt("Ads_type", Ads_type_index);
                    preferencesEditor.commit();
                }
                CustomDialog.dismiss();
            }
        });
        CustomDialog = builder.create();
        CustomDialog.show();
    }

    // Category option event handler
    void Category_hadler(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_search_category, null, false);
        builder.setView(view1);
        builder.setTitle("Category");
        rg_Category = (RadioGroup)view1.findViewById(R.id.rgCategory);
        Category_index = sp.getInt("Category",0);
        ((RadioButton)rg_Category.getChildAt(Category_index)).setChecked(true);

        rg_Category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                RadioButton rb = (RadioButton) rg_Category.findViewById(checkedId);
                if(null!=rb && checkedId > -1){
                    tvCategory.setText(rb.getText());
                    // Get the index of pressed radio button
                    int radioButtonID = rg_Category.getCheckedRadioButtonId();
                    View radioButton = rg_Category.findViewById(radioButtonID);
                    Category_index = rg_Category.indexOfChild(radioButton);
                    preferencesEditor.putInt("Category", Category_index);
                    preferencesEditor.commit();
                }
                CustomDialog.dismiss();
            }
        });
        CustomDialog = builder.create();
        CustomDialog.show();
    }

    // Price type option event handler
    void PriceType_Handler(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_search_price_type, null, false);
        builder.setView(view1);
        builder.setTitle("Price Type");
        rg_Price_Type = (RadioGroup)view1.findViewById(R.id.rgPrice_Type);
        Price_type_index = sp.getInt("ABC",0);
        ((RadioButton)rg_Price_Type.getChildAt(Price_type_index)).setChecked(true);

        rg_Price_Type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                RadioButton rb = (RadioButton) rg_Price_Type.findViewById(checkedId);
                if(null!=rb && checkedId > -1){
                    tvPrice_type.setText(rb.getText());
                    // Get the index of pressed radio button
                    int radioButtonID = rg_Price_Type.getCheckedRadioButtonId();
                    View radioButton = rg_Price_Type.findViewById(radioButtonID);
                    Price_type_index = rg_Price_Type.indexOfChild(radioButton);
                    preferencesEditor.putInt("ABC", Price_type_index);
                    preferencesEditor.commit();
                }
                CustomDialog.dismiss();
            }
        });
        CustomDialog = builder.create();
        CustomDialog.show();
    }

    // Sort options handler
    void Sort_handler(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_search_sort_by, null, false);
        builder.setView(view1);
        builder.setTitle("Sort By");
        rg_Sort = (RadioGroup)view1.findViewById(R.id.rgSortBy);
        Sort_index = sp.getInt("Sort",0);
        ((RadioButton)rg_Sort.getChildAt(Sort_index)).setChecked(true);

        rg_Sort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                RadioButton rb = (RadioButton) rg_Sort.findViewById(checkedId);
                if(null!=rb && checkedId > -1){
                    tvSort.setText(rb.getText());
                    // Get the index of pressed radio button
                    int radioButtonID = rg_Sort.getCheckedRadioButtonId();
                    View radioButton = rg_Sort.findViewById(radioButtonID);
                    Sort_index = rg_Sort.indexOfChild(radioButton);
                    preferencesEditor.putInt("Sort", Sort_index);
                    preferencesEditor.commit();
                }
                CustomDialog.dismiss();
            }
        });
        CustomDialog = builder.create();
        CustomDialog.show();
    }

    // Condition option handler
    void Condition_handler(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_search_condition, null, false);
        builder.setView(view1);
        builder.setTitle("Condition");
        rg_Condition = (RadioGroup)view1.findViewById(R.id.rgCondition);
        Condition_index = sp.getInt("Condition",0);
        ((RadioButton)rg_Condition.getChildAt(Condition_index)).setChecked(true);

        rg_Condition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                RadioButton rb = (RadioButton) rg_Condition.findViewById(checkedId);
                if(null!=rb && checkedId > -1){
                    tvCondition.setText(rb.getText());
                    // Get the index of pressed radio button
                    int radioButtonID = rg_Condition.getCheckedRadioButtonId();
                    View radioButton = rg_Condition.findViewById(radioButtonID);
                    Condition_index = rg_Condition.indexOfChild(radioButton);
                    preferencesEditor.putInt("Condition", Condition_index);
                    preferencesEditor.commit();
                }
                CustomDialog.dismiss();
            }
        });
        CustomDialog = builder.create();
        CustomDialog.show();
    }

    // Price Range handler
    void Price_range_handler(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_search_price_range, null, false);
        builder.setView(view1);
        builder.setTitle("Price Range");
        etMinimum = (EditText)view1.findViewById(R.id.etMinimum);
        etMaximum = (EditText)view1.findViewById(R.id.etMaximum);
        currency = (Spinner)view1.findViewById(R.id.sCurrency);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.currency, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        currency.setAdapter(adapter);
        price_range_done = (Button)view1.findViewById(R.id.bDone);
        Currency_index = sp.getInt("Currency_index", 0);
        currency.setSelection(Currency_index);
        price_min = sp.getString("Price_min","");
        price_max = sp.getString("Price_max","");
        etMinimum.setText(price_min);
        etMaximum.setText(price_max);

        price_range_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                currency_text = String.valueOf(currency.getSelectedItem());
                price_min = etMinimum.getText().toString();
                price_max = etMaximum.getText().toString();
                String temp = price_min + " - " + price_max + " " + currency_text;
                Currency_index = currency.getSelectedItemPosition();
                preferencesEditor.putString("Price_min", price_min);
                preferencesEditor.putString("Price_max", price_max);
                preferencesEditor.putInt("Currency_index", Currency_index);
                preferencesEditor.commit();
                tv_Price_range.setText(temp);
                CustomDialog.dismiss();
            }
        });
        CustomDialog = builder.create();
        CustomDialog.show();
    }

    // Button Reset handler
    void Reset_handler(){
        tvAds_type.setText("Order");
        tvCategory.setText("Fashion/Shoes");
        tvPrice_type.setText("Any");
        tv_Price_range.setText("Any");
        tvSort.setText("Most Recent");
        tvCondition.setText("New");
        // Clear all data in shared preferenced
        preferencesEditor.clear();
        preferencesEditor.commit();
    }

    // Button Search Handler
    void Search_handler(){
        search.put("UserID", UserInfo.userID);
        search.put("AdsType", tvAds_type.getText().toString());
        search.put("Order_location", tvOrder_place.getText().toString());
        search.put("Ship_location", tvShip_place.getText().toString());
        search.put("Category", tvCategory.getText().toString());
        search.put("PriceType", tvPrice_type.getText().toString());
        search.put("PriceRange", tv_Price_range.getText().toString());
        search.put("SortBy", tvSort.getText().toString());
        search.put("Condition", tvCondition.getText().toString());
        search.saveInBackground();

        // Get data for recent search
        save_ads_type = tvAds_type.getText().toString();
        save_category = tvCategory.getText().toString();
        //save_time = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);
        save_time = formattedDate;
        save_location = tvOrder_place.getText().toString();

        recent_search_string = tinydb.getListString("RecentSearch");
        //Toast.makeText(getActivity(),Integer.toString(recent_search_string.size()),Toast.LENGTH_SHORT).show();

        items = new Slidemenu_Recent_Search_Item(save_ads_type, save_category, save_time, save_location);
        temp = gson.toJson(items);
        recent_search_string.add(temp);
        tinydb.putListString("RecentSearch", recent_search_string);
        //preferencesEditor.putString("RecentSearch", temp);
        //preferencesEditor.commit();

        Intent intent = new Intent(getActivity(), Slidemenu_search_result.class);
        startActivity(intent);
    }
}
