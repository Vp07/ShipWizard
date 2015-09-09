package com.example.trongnghia.shipwizard_v11.SlideMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.LogIn.DispatchActivity;
import com.example.trongnghia.shipwizard_v11.R;
import com.parse.ParseUser;

/**
 * Represents other activity different from the main activity
 *
 * @author Sotti https://plus.google.com/+PabloCostaTirado/about
 */
public class OtherActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidemenu_other_activity);

        initialise();
    }

    /**
     * Create, bind and set up the resources
     */
    private void initialise()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
