package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Ads_History;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Ads_History_Adapter;

/**
 * Created by Trong Nghia on 9/10/2015.
 */
public class PageAdapter_View extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PageAdapter_View(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Order_Fragment_View order_view = new Order_Fragment_View();
                //Slidemenu_Ads_History order_view = new Slidemenu_Ads_History();
                return order_view;
            case 1:
                Ship_Fragment_View ship_view = new Ship_Fragment_View();
                //Slidemenu_Ads_History ship_view = new Slidemenu_Ads_History();
                return ship_view;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
