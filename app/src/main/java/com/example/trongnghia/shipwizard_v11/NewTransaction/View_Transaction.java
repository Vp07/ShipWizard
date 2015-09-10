package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.SlideMenu.ManagerTypeface;
import com.example.trongnghia.shipwizard_v11.SlideMenu.OtherActivity;
import com.example.trongnghia.shipwizard_v11.SlideMenu.ScrimInsetsFrameLayout;
import com.example.trongnghia.shipwizard_v11.SlideMenu.UtilsDevice;
import com.example.trongnghia.shipwizard_v11.SlideMenu.UtilsMiscellaneous;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;

public class View_Transaction extends AppCompatActivity implements View.OnClickListener {

    private final static double sNAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO = 9d/16d;

    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayout_AccountView;
    private LinearLayout mNavDrawerEntriesRootView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;

    private FrameLayout mFrameLayout_Home, mFrameLayout_Post, mFrameLayout_AdsFavorite,
            mFrameLayout_AdsHistory, mFrameLayout_AdsMessage, mFrameLayout_Search,
            mFrameLayout_RecentSearch, mFrameLayout_SaveSearch, mFrameLayout_Setting,
            mFrameLayout_HelpAndFeedback, mFrameLayout_About, mFrameLayout_Logout;

    private TextView mTextView_AccountDisplayName, mTextView_AccountEmail;

    private TextView mTextView_Home, mTextView_Post,mTextView_AdsHistory, mTextView_AdsFavorite,
            mTextView_AdsMessage, mTextView_Search, mTextView_SaveSearch, mTextView_RecentSearch,
            mTextView_Setting, mTextView_HelpAndFeedback, mTextView_About, mTextView_Logout;

    private TextView divide_ads, divide_ads_search, divide_app_setting;
;

    // Get user name and email of current user from Parse

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view__transaction_activity);

        // Set up for navigation window
        initialise();

        // Set up for transaction tabs
        tab_initialise();
    }

    /**
     * Bind, create and set up the resources
     */
    private void initialise()
    {
        // Toolbar
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Divide line text
        divide_ads = (TextView)findViewById(R.id.tvAd_mes);
        divide_ads_search = (TextView)findViewById(R.id.tvAd_search);
        divide_app_setting = (TextView)findViewById(R.id.tvAd_setting);

        // Layout resources
        mFrameLayout_AccountView = (FrameLayout) findViewById(R.id.navigation_drawer_account_view);
        mNavDrawerEntriesRootView = (LinearLayout)findViewById(R.id.navigation_drawer_linearLayout_entries_root_view);

        // Slide Menu Items
        mFrameLayout_Home = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_home);
        mFrameLayout_Post = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_PostAds);
        mFrameLayout_AdsHistory = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_AdsHistory);
        mFrameLayout_AdsFavorite = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_AdsFavorite);
        mFrameLayout_AdsMessage = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_AdsMessage);
        mFrameLayout_Search = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_Search);
        mFrameLayout_RecentSearch = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_Recent_Search);
        mFrameLayout_SaveSearch = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_Save_Search);
        mFrameLayout_Setting = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_setting);
        mFrameLayout_HelpAndFeedback = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_help_and_feedback);
        mFrameLayout_About = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_about);
        mFrameLayout_Logout = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_logout);

        mTextView_AccountDisplayName = (TextView) findViewById(R.id.navigation_drawer_account_information_display_name);
        mTextView_AccountDisplayName.setText(UserInfo.username);
        mTextView_AccountEmail = (TextView) findViewById(R.id.navigation_drawer_account_information_email);
        mTextView_AccountEmail.setText(UserInfo.email);

        mTextView_Home = (TextView) findViewById(R.id.navigation_drawer_items_textView_home);
        mTextView_Post = (TextView) findViewById(R.id.navigation_drawer_items_textView_PostAds);
        mTextView_AdsHistory = (TextView) findViewById(R.id.navigation_drawer_items_textView_AdsHistory);
        mTextView_AdsFavorite = (TextView) findViewById(R.id.navigation_drawer_items_textView_AdsFavorite);
        mTextView_AdsMessage = (TextView) findViewById(R.id.navigation_drawer_items_textView_AdsMessage);
        mTextView_Search = (TextView) findViewById(R.id.navigation_drawer_items_textView_Search);
        mTextView_RecentSearch = (TextView) findViewById(R.id.navigation_drawer_items_textView_Recent_Search);
        mTextView_SaveSearch = (TextView) findViewById(R.id.navigation_drawer_items_textView_Save_Search);
        mTextView_Setting = (TextView) findViewById(R.id.navigation_drawer_items_textView_setting);
        mTextView_HelpAndFeedback = (TextView) findViewById(R.id.navigation_drawer_items_textView_help_and_feedback);
        mTextView_About = (TextView) findViewById(R.id.navigation_drawer_items_textView_about);
        mTextView_Logout = (TextView) findViewById(R.id.navigation_drawer_items_textView_logout);

        // Typefaces
        mTextView_AccountDisplayName.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_AccountEmail.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_regular));

        mTextView_Home.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        divide_ads.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_Post.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_AdsHistory.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_AdsFavorite.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_AdsMessage.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        divide_ads_search.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_Search.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_RecentSearch.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_SaveSearch.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        divide_app_setting.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_Setting.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_HelpAndFeedback.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_About.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_Logout.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));

        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout,
                        mToolbar,
                        R.string.navigation_drawer_opened,
                        R.string.navigation_drawer_closed
                )
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                // Disables the burger/arrow animation by default
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mActionBarDrawerToggle.syncState();

        // Navigation Drawer layout width
        int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);

        // Account section height
        mFrameLayout_AccountView.getLayoutParams().height = (int) (mScrimInsetsFrameLayout.getLayoutParams().width
                * sNAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO);

        // Nav Drawer item click listener
        mFrameLayout_AccountView.setOnClickListener(this);
        mFrameLayout_Home.setOnClickListener(this);
        mFrameLayout_Post.setOnClickListener(this);
        mFrameLayout_AdsHistory.setOnClickListener(this);
        mFrameLayout_AdsFavorite.setOnClickListener(this);
        mFrameLayout_AdsMessage.setOnClickListener(this);
        mFrameLayout_Search.setOnClickListener(this);
        mFrameLayout_RecentSearch.setOnClickListener(this);
        mFrameLayout_SaveSearch.setOnClickListener(this);
        mFrameLayout_Setting.setOnClickListener(this);
        mFrameLayout_HelpAndFeedback.setOnClickListener(this);
        mFrameLayout_About.setOnClickListener(this);
        mFrameLayout_Logout.setOnClickListener(this);

        // Set the first item as selected for the first time

        //getSupportActionBar().setTitle(R.string.toolbar_title_home);
        mFrameLayout_Home.setSelected(true);
        getSupportActionBar().setTitle("Home");

        // Create the first fragment to be shown
//        Bundle bundle = new Bundle();
//        bundle.putInt(ColorFragment.sARGUMENT_COLOR, R.color.blue_500);
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.main_activity_content_frame, ColorFragment.newInstance(bundle))
//                .commit();
    }

    // Set up for transaction tabs
    private void tab_initialise() {
        // Order and Ship tabs setting
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Order"));
        tabLayout.addTab(tabLayout.newTab().setText("Ship"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        final PageAdapter_View adapter = new PageAdapter_View(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.navigation_drawer_account_view)
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            // If the user is signed in, go to the profile, otherwise show sign up / sign in
        }
        else
        {
            if (!view.isSelected())
            {
                onRowPressed((FrameLayout) view);
                switch (view.getId())
                {
                    // Slide Menu - Home
                    case R.id.navigation_drawer_items_list_linearLayout_home:
                    {
                        mFrameLayout_Home.setSelected(false);
                        startActivity(new Intent(this, View_Transaction.class));
                        break;
                    }

                    // Slide Menu - Post an Ads
                    case R.id.navigation_drawer_items_list_linearLayout_PostAds:
                    {
                        //mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                        mFrameLayout_Post.setSelected(false);
                        startActivity(new Intent(this, Post_Transaction.class));
                        break;
                    }

                    // Slide Menu - Ads History
                    case R.id.navigation_drawer_items_list_linearLayout_AdsHistory:
                    {

                    }

                    // Slide Menu - Favorite Ads
                    case R.id.navigation_drawer_items_list_linearLayout_AdsFavorite:
                    {

                    }

                    // Slide Menu - History Messages
                    case R.id.navigation_drawer_items_list_linearLayout_AdsMessage:
                    {

                    }

                    // Slide Menu - Search
                    case R.id.navigation_drawer_items_list_linearLayout_Search:
                    {

                    }

                    // Slide Menu - Recent Search
                    case R.id.navigation_drawer_items_list_linearLayout_Recent_Search:
                    {

                    }

                    // Slide Menu - Saved Search
                    case R.id.navigation_drawer_items_list_linearLayout_Save_Search:
                    {

                    }

                    // Slide Menu - Settings
                    case R.id.navigation_drawer_items_list_linearLayout_setting:
                    {

                    }

                    // Slide menu - Help and Feedback
                    case R.id.navigation_drawer_items_list_linearLayout_help_and_feedback:
                        // Start intent to send an email
                        startActivity(new Intent(view.getContext(), OtherActivity.class));
                        break;

                    // Slide Menu - About
                    case R.id.navigation_drawer_items_list_linearLayout_about:
                        // Show about activity
                        startActivity(new Intent(view.getContext(), OtherActivity.class));
                        break;

                    // Slide Menu - Log out
                    case R.id.navigation_drawer_items_list_linearLayout_logout:
                        break;

                    default:
                        break;
                }
            }
            else
            {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        }
    }

    /**
     * Set up the rows when any is pressed
     *
     * @param pressedRow is the pressed row in the drawer
     */
    private void onRowPressed(FrameLayout pressedRow) {
        if (pressedRow.getTag() != getResources().getString(R.string.tag_nav_drawer_special_entry))
        {
            for (int i = 0; i < mNavDrawerEntriesRootView.getChildCount(); i++)
            {
                View currentView = mNavDrawerEntriesRootView.getChildAt(i);

                boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry)
                {
                    if (currentView == pressedRow)
                    {
                        currentView.setSelected(true);
                    }
                    else
                    {
                        currentView.setSelected(false);
                    }
                }
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void display_info(){

    }
}
