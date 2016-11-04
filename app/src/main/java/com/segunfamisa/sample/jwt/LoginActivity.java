package com.segunfamisa.sample.jwt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextView mTitleAction;
    private TextView mPromptAction;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private EditText mEditProfileColor;
    private Button mButtonAction;

    /**
     * Flag to show whether it is sign up field that's showing
     */
    private boolean mIsSignUpShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTitleAction = (TextView) findViewById(R.id.text_title);
        mPromptAction = (TextView) findViewById(R.id.prompt_action);
        mEditEmail = (EditText) findViewById(R.id.edit_email);
        mEditPassword = (EditText) findViewById(R.id.edit_password);
        mEditProfileColor = (EditText) findViewById(R.id.edit_profile_color);
        mButtonAction = (Button) findViewById(R.id.button_action);

        setupView(mIsSignUpShowing);
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

    private void doLogin() {
        Toast.makeText(this, "Login!", Toast.LENGTH_SHORT).show();
    }

    private void doSignUp() {
        Toast.makeText(this, "Sign up!", Toast.LENGTH_SHORT).show();
    }

    private final View.OnClickListener showSignUpFormClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setupView(true);
        }
    };

    private final View.OnClickListener showLoginFormClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setupView(false);
        }
    };

    private final View.OnClickListener doLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            doLogin();
        }
    };

    private final View.OnClickListener doSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            doSignUp();
        }
    };
}
