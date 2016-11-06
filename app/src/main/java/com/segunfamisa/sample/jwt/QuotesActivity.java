package com.segunfamisa.sample.jwt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.segunfamisa.sample.jwt.network.NetworkRequest;

public class QuotesActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextWelcome;
    private TextView mTextQuote;
    private Button mButtonRandom;

    private AuthHelper mAuthHelper;

    private ProgressDialog mProgressDialog;

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

        mProgressDialog = new ProgressDialog(this);
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
        mTextQuote.setText(quote);
    }

    @Override
    public void onClick(View view) {
        if (view == mButtonRandom) {
            doGetQuote(mAuthHelper.getIdToken());
        }
    }

    private void doGetQuote(String token) {
        NetworkRequest request = new NetworkRequest();
        mProgressDialog.setMessage(getString(R.string.progress_quote));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        request.doGetProtectedQuote(token, new NetworkRequest.Callback<String>() {
            @Override
            public void onResponse(@NonNull String response) {
                dismissDialog();
                setQuoteText(response);
            }

            @Override
            public void onError(String error) {
                dismissDialog();
                Toast.makeText(QuotesActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public Class<String> type() {
                return String.class;
            }
        });
    }

    /**
     * Dismiss the dialog if it's showing
     */
    private void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
