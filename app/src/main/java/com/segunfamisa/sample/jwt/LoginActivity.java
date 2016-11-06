package com.segunfamisa.sample.jwt;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.segunfamisa.sample.jwt.network.NetworkRequest;
import com.segunfamisa.sample.jwt.network.Token;

public class LoginActivity extends AppCompatActivity {

    private TextView mTitleAction;
    private TextView mPromptAction;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private EditText mEditProfileColor;
    private Button mButtonAction;

    private ProgressDialog mProgressDialog;
    private AuthHelper mAuthHelper;

    /**
     * Flag to show whether it is sign up field that's showing
     */
    private boolean mIsSignUpShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthHelper = AuthHelper.getInstance(this);
        mProgressDialog = new ProgressDialog(this);

        mTitleAction = (TextView) findViewById(R.id.text_title);
        mPromptAction = (TextView) findViewById(R.id.prompt_action);
        mEditEmail = (EditText) findViewById(R.id.edit_email);
        mEditPassword = (EditText) findViewById(R.id.edit_password);
        mEditProfileColor = (EditText) findViewById(R.id.edit_profile_color);
        mButtonAction = (Button) findViewById(R.id.button_action);

        setupView(mIsSignUpShowing);

        if (mAuthHelper.isLoggedIn()) {
            startActivity(QuotesActivity.getCallingIntent(this));
        }
    }

    /**
     * Sets up the view based on whether or not the sign up screen is showing
     *
     * @param isSignUpShowing - flag indicating whether the sign up form is showing
     */
    private void setupView(boolean isSignUpShowing) {
        mIsSignUpShowing = isSignUpShowing;
        mTitleAction.setText(isSignUpShowing ? R.string.text_sign_up : R.string.text_login);
        mButtonAction.setText(isSignUpShowing ? R.string.text_sign_up : R.string.text_login);
        mPromptAction.setText(isSignUpShowing ? R.string.text_login: R.string.text_sign_up);

        mEditProfileColor.setVisibility(isSignUpShowing ? View.VISIBLE : View.GONE)
        ;
        mButtonAction.setOnClickListener(isSignUpShowing ? doSignUpClickListener : doLoginClickListener);
        mPromptAction.setOnClickListener(isSignUpShowing ? showLoginFormClickListener :
                showSignUpFormClickListener);
    }

    /**
     * Log the user in and navigate to profile screen when successful
     */
    private void doLogin() {
        String username = getUsernameText();
        String password = getPasswordText();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.toast_no_empty_field, Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressDialog.setMessage(getString(R.string.progress_login));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        NetworkRequest request = new NetworkRequest();
        request.doLogin(username, password, mLoginCallback);
    }

    /**
     * Sign up the user and navigate to profile screen
     */
    private void doSignUp() {
        String username = getUsernameText();
        String password = getPasswordText();
        String profileColor = getProfileColorText();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(profileColor)) {
            Toast.makeText(this, R.string.toast_no_empty_field, Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressDialog.setMessage(getString(R.string.progress_signup));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        NetworkRequest request = new NetworkRequest();
        request.doSignUp(username, password, profileColor, mSignUpCallback);
    }

    private String getUsernameText() {
        return mEditEmail.getText().toString().trim();
    }

    private String getPasswordText() {
        return mEditPassword.getText().toString().trim();
    }

    private String getProfileColorText() {
        return mEditProfileColor.getText().toString().trim();
    }

    /**
     * Save session details and navigates to the quotes activity
     * @param token - {@link Token} received on login or signup
     */
    private void saveSessionDetails(@NonNull Token token) {
        mAuthHelper.setIdToken(token);

        // start profile activity
        startActivity(QuotesActivity.getCallingIntent(this));
    }

    /**
     * Callback for login
     */
    private NetworkRequest.Callback<Token> mLoginCallback = new NetworkRequest.Callback<Token>() {
        @Override
        public void onResponse(@NonNull Token response) {
            dismissDialog();
            // save token and go to profile page
            saveSessionDetails(response);
        }

        @Override
        public void onError(String error) {
            dismissDialog();
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public Class<Token> type() {
            return Token.class;
        }

    };

    /**
     * Callback for sign up
     */
    private NetworkRequest.Callback<Token> mSignUpCallback = new NetworkRequest.Callback<Token>() {
        @Override
        public void onResponse(@NonNull Token response) {
            dismissDialog();
            // save token and go to profile page
            saveSessionDetails(response);
        }

        @Override
        public void onError(String error) {
            dismissDialog();
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public Class<Token> type() {
            return Token.class;
        }
    };

    /**
     * Dismiss the dialog if it's showing
     */
    private void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Click listener to show sign up form
     */
    private final View.OnClickListener showSignUpFormClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setupView(true);
        }
    };

    /**
     * Click listener to show login form
     */
    private final View.OnClickListener showLoginFormClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setupView(false);
        }
    };

    /**
     * Click listener to invoke login
     */
    private final View.OnClickListener doLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            doLogin();
        }
    };

    /**
     * Click listener to invoke sign up
     */
    private final View.OnClickListener doSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            doSignUp();
        }
    };
}
