// 8/9/2015
package com.example.trongnghia.shipwizard_v11.LogIn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trongnghia.shipwizard_v11.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rufflez on 7/8/14.
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    EditText usernameView;
    EditText passwordView;
    Button login;
    Button facebooLogin;
    TextView forgetPass;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Set up the login form.
        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);
        login = (Button)findViewById(R.id.action_button);
        facebooLogin = (Button)findViewById(R.id.bFacebookLogin);
        forgetPass = (TextView)findViewById(R.id.tvForgetPass);

        login.setOnClickListener(this);
        forgetPass.setOnClickListener(this);
        facebooLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_button:
                // Validate the log in data
                boolean validationError = false;
                StringBuilder validationErrorMessage = new StringBuilder(getResources().getString(R.string.error_intro));
                if (isEmpty(usernameView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_username));
                }
                if (isEmpty(passwordView)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_password));
                }
                validationErrorMessage.append(getResources().getString(R.string.error_end));

                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(LoginActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(LoginActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Logging in.  Please wait.");
                dlg.show();
                // Call the Parse login method
                ParseUser.logInInBackground(usernameView.getText().toString(),
                        passwordView.getText().toString(), new LogInCallback() {

                            @Override
                            public void done(ParseUser user, ParseException e) {
                                dlg.dismiss();
                                if (e != null) {
                                    // Show the error message
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    // Start an intent for the dispatch activity
                                    Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);

                                    // FLAG_ACTIVITY_CLEAR_TASK -> this flag will cause any existing task that would
                                    // be associated with the activity to be cleared before the activity is started.
                                    // FLAG_ACTIVITY_NEW_TASK -> If set, this activity will become the start of a new
                                    // task on this history stack
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
                break;

            case R.id.bFacebookLogin:
                final ProgressDialog dlg1 = new ProgressDialog(LoginActivity.this);
                dlg1.setTitle("Please wait.");
                dlg1.setMessage("Logging in.  Please wait.");
                dlg1.show();

                List<String> permissions = Arrays.asList("public_profile", "email");
                // NOTE: for extended permissions, like "user_about_me", your app must be reviewed by the Facebook team
                // (https://developers.facebook.com/docs/facebook-login/permissions/)

                ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        dlg1.dismiss();
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                        } else {
                            Log.d("MyApp", "User logged in through Facebook!");
                            Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
                break;

            // Reset Password
            case R.id.tvForgetPass:
                Intent intent = new Intent(LoginActivity.this, Forget_password.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}
