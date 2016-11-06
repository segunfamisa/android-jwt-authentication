package com.segunfamisa.sample.jwt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class QuotesActivity extends AppCompatActivity {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, QuotesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
    }
}
