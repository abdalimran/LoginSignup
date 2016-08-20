package com.github.abdalimran.loginsignup.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.abdalimran.loginsignup.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setResult(RESULT_OK, getIntent());
    }
}
