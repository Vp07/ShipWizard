package com.example.trongnghia.shipwizard_v11.LogIn;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.R;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class Forget_password extends Activity{

    EditText reset_mail;
    Button bReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_activity);

        reset_mail = (EditText)findViewById(R.id.etRecover_email);
        bReset = (Button)findViewById(R.id.bResetEmail);
    }

    public void onLoginClick(View v) {
        // Check whether the recovery email is similar to log in email
        ParseUser.requestPasswordResetInBackground(reset_mail.getText().toString(), new RequestPasswordResetCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Toast.makeText(Forget_password.this, "A email has been sent to your Email address", Toast.LENGTH_LONG).show();
                } else {
                    // Something went wrong. Look at the ParseException to see what's up.
                }
            }
        });
    }
}
