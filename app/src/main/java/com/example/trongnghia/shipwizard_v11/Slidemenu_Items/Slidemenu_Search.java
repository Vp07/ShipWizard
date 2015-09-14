package com.example.trongnghia.shipwizard_v11.Slidemenu_Items;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.R;

import org.w3c.dom.Text;

public class Slidemenu_Search extends Fragment implements View.OnClickListener{

    public FrameLayout ads_type, buyer_location, carrier_location, category, price_type, sort_by;

    public TextView tvAds_type, tvCategory, tvPrice_type, tv_Price_range, tvSort;
    public  View view;

    SharedPreferences sp;
    SharedPreferences.Editor preferencesEditor;

    public static final String Search= "Search";

    public RadioGroup rg_Ads_Type, rg_Category, rg_Price_Type, rg_Sort;

    private int Ads_type_index, Category_index, Price_type_index, Sort_index;

    public AlertDialog CustomDialog;

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
        }
    }

    void initialize(){

        // Restore preferences
        sp = this.getActivity().getSharedPreferences(Search, 0);
        preferencesEditor = sp.edit();

        // Ads_type
        ads_type = (FrameLayout)view.findViewById(R.id.flAds_Type);
        tvAds_type = (TextView)view.findViewById(R.id.tvAds_Type);
        ads_type.setOnClickListener(this);

        // Buyer Location

        // Carrier Location

        // Category
        category = (FrameLayout)view.findViewById(R.id.flCategory);
        tvCategory = (TextView)view.findViewById(R.id.tvCategory);
        category.setOnClickListener(this);

        // Price Type
        price_type = (FrameLayout)view.findViewById(R.id.flPrice_Type);
        tvPrice_type = (TextView)view.findViewById(R.id.tvPrice_Type);
        price_type.setOnClickListener(this);

        // Price Range

        // Sort By
        sort_by = (FrameLayout)view.findViewById(R.id.flSort_By);
        tvSort = (TextView)view.findViewById(R.id.tvSort_By);
        sort_by.setOnClickListener(this);

        // Condition
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
        Sort_index = sp.getInt("ABC",0);
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
}
