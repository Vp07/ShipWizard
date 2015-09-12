package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trongnghia.shipwizard_v11.R;

/**
 * Created by Trong Nghia on 9/11/2015.
 */
public class Post_Tabs_Fragment extends Fragment {

    // TODO: Rename and change types and number of parameters
    public static Post_Tabs_Fragment newInstance() {
        Post_Tabs_Fragment fragment = new Post_Tabs_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.transaction_tabs, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Order"));
        tabLayout.addTab(tabLayout.newTab().setText("Ship"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        //final PagerAdapter adapter = new PagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        final PageAdapter_View adapter = new PageAdapter_View(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}
