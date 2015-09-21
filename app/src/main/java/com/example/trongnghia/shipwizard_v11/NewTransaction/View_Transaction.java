package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.LogIn.DispatchActivity;
import com.example.trongnghia.shipwizard_v11.R;
import com.example.trongnghia.shipwizard_v11.SlideMenu.ColorFragment;
import com.example.trongnghia.shipwizard_v11.SlideMenu.Fragment_order_tabs;
import com.example.trongnghia.shipwizard_v11.SlideMenu.MainActivity;
import com.example.trongnghia.shipwizard_v11.SlideMenu.ManagerTypeface;
import com.example.trongnghia.shipwizard_v11.SlideMenu.OtherActivity;
import com.example.trongnghia.shipwizard_v11.SlideMenu.ScrimInsetsFrameLayout;
import com.example.trongnghia.shipwizard_v11.SlideMenu.UtilsDevice;
import com.example.trongnghia.shipwizard_v11.SlideMenu.UtilsMiscellaneous;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_About;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Ads_History;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Favorite_Ads;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Feedback;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Message;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Profile;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Recent_Search;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Saved_Search;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Search;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Slidemenu_Setting;
import com.example.trongnghia.shipwizard_v11.Slidemenu_Items.Test_fragment;
import com.example.trongnghia.shipwizard_v11.User.UserInfo;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

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

    private de.hdodenhof.circleimageview.CircleImageView mCircleImageView_Avatar;
;

    // Get user name and email of current user from Parse

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view__transaction_activity);
        //setContentView(R.layout.test_fragment);

        // Set up for navigation window
        initialise();
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
        mCircleImageView_Avatar = (de.hdodenhof.circleimageview.CircleImageView ) findViewById(R.id.navigation_drawer_user_account_picture_profile);

       ParseFile avatar = DispatchActivity.current_user.getAvatar();
        if (avatar!=null) {
            avatar.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bmp = BitmapFactory
                            .decodeByteArray(data, 0, data.length);
                    Bitmap bm_for_show = ThumbnailUtils.extractThumbnail(bmp, 500, 500);
                    mCircleImageView_Avatar.setImageBitmap(bm_for_show);
                }
            });
                    Toast.makeText(this,"Avatar available", Toast.LENGTH_SHORT).show();
                       // mCircleImageView_Avatar.setImageBitmap(avatar);
                    }
                    else {
                        mCircleImageView_Avatar.setBackgroundResource(R.drawable.ic_account_circle_white_64dp);
                         Toast.makeText(this,"No avatar", Toast.LENGTH_SHORT).show();
                    }

//        avatar.getDataInBackground() ;
//            @Override
//            public void done(byte[] data, ParseException e) {
//                if (e == null) {

//                    Bitmap bmp = BitmapFactory
//                            .decodeByteArray(data, 0, data.length);
//                    Bitmap bm_for_show = ThumbnailUtils.extractThumbnail(bmp, 500, 500);
//                    if (bm_for_show!=null) {
//                        mCircleImageView_Avatar.setImageBitmap(bm_for_show);
//                    }
//                    else {
//                        mCircleImageView_Avatar.setBackgroundResource(R.drawable.ic_account_circle_white_64dp);
//                    }
//                }
//            }
//        });

       // String tesst = UserInfo.avatar.getName();
        //Toast.makeText(this," ParseFile = " + UserInfo.email, Toast.LENGTH_SHORT).show();





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
       getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_activity_content_frame, Post_Tabs_Fragment.newInstance())
                .commit();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.navigation_drawer_account_view)
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            // If the user is signed in, go to the profile, otherwise show sign up / sign in
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setTitle("Profile");

            }
            view.setSelected(false);
            // Insert the fragment by replacing any existing fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_activity_content_frame, Slidemenu_Profile.newInstance())
                    .commit();

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
                        startActivity(new Intent(this, Post_Transaction.class));
                        break;
                    }

                    // Slide Menu - Ads History -> Fragment
                    case R.id.navigation_drawer_items_list_linearLayout_AdsHistory:
                    {
                        if (getSupportActionBar() != null)
                        {
                            getSupportActionBar().setTitle("Ads History");

                        }
                        view.setSelected(true);
                        // Insert the fragment by replacing any existing fragment
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, Slidemenu_Ads_History.newInstance())
                                //.addToBackStack("AdsHistoryList")
                                .commit();
                        break;
                    }

                    // Slide Menu - Favorite Ads
                    case R.id.navigation_drawer_items_list_linearLayout_AdsFavorite:
                    {
                        if (getSupportActionBar() != null)
                        {
                            getSupportActionBar().setTitle("Favorite Ads");

                        }
                        view.setSelected(true);
                        // Insert the fragment by replacing any existing fragment
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, Slidemenu_Favorite_Ads.newInstance())
                                .commit();
                        break;
                    }

                    // Slide Menu - History Messages
                    case R.id.navigation_drawer_items_list_linearLayout_AdsMessage:
                    {
                        if (getSupportActionBar() != null)
                        {
                            getSupportActionBar().setTitle("Message");

                        }
                        view.setSelected(true);
                        // Insert the fragment by replacing any existing fragment
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, Slidemenu_Message.newInstance())
                                .commit();
                        break;
                    }

                    // Slide Menu - Search -> Fragment
                    case R.id.navigation_drawer_items_list_linearLayout_Search:
                    {
                        // Set Fragment title
                        if (getSupportActionBar() != null)
                        {
                            getSupportActionBar().setTitle("Search");
                        }
                        view.setSelected(true);

                        // Insert the fragment by replacing any existing fragment
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, Slidemenu_Search.newInstance())
                                .commit();
                        break;
                    }

                    // Slide Menu - Recent Search -> Fragment
                    case R.id.navigation_drawer_items_list_linearLayout_Recent_Search:
                    {
                        // Set Fragment title
                        if (getSupportActionBar() != null)
                        {
                            getSupportActionBar().setTitle("Recent Search");
                        }
                        view.setSelected(true);

                        // Insert the fragment by replacing any existing fragment
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, Slidemenu_Recent_Search.newInstance())
                                .commit();
                        break;
                    }

                    // Slide Menu - Saved Search
                    case R.id.navigation_drawer_items_list_linearLayout_Save_Search:
                    {
                        // Set Fragment title
                        if (getSupportActionBar() != null)
                        {
                            getSupportActionBar().setTitle("Saved Search");
                        }
                        view.setSelected(true);
                        // Insert the fragment by replacing any existing fragment
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, Slidemenu_Saved_Search.newInstance())
                                .commit();
                        break;
                    }

                    // Slide Menu - Settings
                    case R.id.navigation_drawer_items_list_linearLayout_setting:
                    {
                        startActivity(new Intent(this, Slidemenu_Setting.class));
                        break;
                    }

                    // Slide menu - Help and Feedback
                    case R.id.navigation_drawer_items_list_linearLayout_help_and_feedback:
                    {
                        startActivity(new Intent(this, Slidemenu_Feedback.class));
                        break;
                    }

                    // Slide Menu - About
                    case R.id.navigation_drawer_items_list_linearLayout_about:
                    {
                        startActivity(new Intent(this, Slidemenu_About.class));
                        break;
                    }

                    // Slide Menu - Log out
                    case R.id.navigation_drawer_items_list_linearLayout_logout:
                    {
                        new AlertDialog.Builder(this)
                                .setTitle("Log Out")
                                .setMessage("Log Out now?")
                                .setCancelable(false)
                                .setPositiveButton("LOG OUT", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ParseUser.getCurrentUser().logOut();
                                        startActivity(new Intent(View_Transaction.this, DispatchActivity.class));
                                    }
                                })
                                .setNegativeButton("CANCEL", null)
                                .show();
                        break;
                    }

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
}
