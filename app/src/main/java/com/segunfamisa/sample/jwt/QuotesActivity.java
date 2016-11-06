package com.segunfamisa.sample.jwt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuotesActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextWelcome;
    private TextView mTextQuote;
    private Button mButtonRandom;

    private AuthHelper mAuthHelper;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, QuotesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        mTextWelcome = (TextView) findViewById(R.id.text_username);
        mTextQuote = (TextView) findViewById(R.id.text_quote);
        mButtonRandom = (Button) findViewById(R.id.button_random_quote);

        mAuthHelper = AuthHelper.getInstance(this);

        if (mAuthHelper.isLoggedIn()) {
            setupView();
        } else {
            finish();
        }
    }

    private void setupView() {
        setWelcomeText(mAuthHelper.getUsername());
        mButtonRandom.setOnClickListener(this);
    }

    /**
     * Sets the welcome text for the signed in user
     * @param username - username for the user
     */
    private void setWelcomeText(String username) {
        mTextWelcome.setText(String.format(getString(R.string.text_welcome), username));
    }

    /**
     * Sets the quote text for the signed in user
     * @param quote - the quote
     */
    private void setQuoteText(String quote) {

    }

    @Override
    public void onClick(View view) {
        if (view == mButtonRandom) {
            doGetQuote();
        }
    }

    private void doGetQuote() {
        // TODO: 06/11/2016 implement getting quotes
    }
}
