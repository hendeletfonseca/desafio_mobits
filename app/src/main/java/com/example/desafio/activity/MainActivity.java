package com.example.desafio.activity;

import android.os.Bundle;

import com.example.desafio.R;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCurrentActivity("MAIN_ACTIVITY");
        setContentView(R.layout.activity_main);
    }

}