package com.example.trongnghia.shipwizard_v11.NewTransaction;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.trongnghia.shipwizard_v11.LogIn.DispatchActivity;
import com.example.trongnghia.shipwizard_v11.R;
import com.parse.ParseUser;

public class NewTrans_Option extends Activity implements View.OnClickListener {

    Button post;
    Button view;
    // testing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transaction_activity);

        post = (Button)findViewById(R.id.bPost);
        view = (Button)findViewById(R.id.bView);

        post.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPost:
                Intent intent = new Intent(NewTrans_Option.this, Post_Transaction.class);
                startActivity(intent);
                break;
            case R.id.bView:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            ParseUser.getCurrentUser().logOut();
            startActivity(new Intent(NewTrans_Option.this, DispatchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
