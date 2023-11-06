package com.example.desafio.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.desafio.R;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar toolbar = getSupportActionBar();
        assert toolbar != null;
        //toolbar.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.toolbar_gradient));
        toolbar.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.toolbar_color));
    }
}