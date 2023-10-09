package com.example.desafio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.desafio.R;

public abstract class SpecificActivity extends AppCompatActivity {
    protected String endPoint;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar toolbar = getSupportActionBar();
        assert toolbar != null;
        toolbar.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.toolbar_color));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.specific_views_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_bar_img_search) openWebView();
        return super.onOptionsItemSelected(item);
    }

    public void openWebView() {
        Bundle bundle = new Bundle();
        bundle.putString("url", "https://www.google.com/search?tbm=isch&q=" + endPoint);
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public abstract void setEndPoint(String endPoint);
}
