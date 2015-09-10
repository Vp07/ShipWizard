package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
                return order_view;
            case 1:
                Ship_Fragment_View ship_view = new Ship_Fragment_View();
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
