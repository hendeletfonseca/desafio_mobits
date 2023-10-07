package com.example.desafio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.desafio.R;


public abstract class BaseActivity extends AppCompatActivity {
    private String currentActivity = "BASE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar toolbar = getSupportActionBar();
        assert toolbar != null;
        toolbar.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.toolbar_gradient));
        //toolbar.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.toolbar_color));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem mi_book = menu.findItem(R.id.action_bar_books);
        MenuItem mi_house = menu.findItem(R.id.action_bar_houses);

        if (currentActivity.equals("BOOKS_ACTIVITY")) {
            mi_book.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_book_light));
            mi_house.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_house_light));
        } else if (currentActivity.equals("HOUSES_ACTIVITY")) {
            mi_book.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_book_dark));
            mi_house.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_house_dark));
        } else {
            mi_book.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_book_dark));
            mi_house.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_house_light));
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_bar_books) onClickBooks();
        if (itemId == R.id.action_bar_houses) onClickHouses();
        return super.onOptionsItemSelected(item);
    }

    private void onClickBooks() {
        Intent intent = new Intent(this, BooksActivity.class);
        startActivity(intent);
        if (!currentActivity.equals("MAIN_ACTIVITY")) finish();
    }

    private void onClickHouses() {
        Intent intent = new Intent(this, HousesActivity.class);
        startActivity(intent);
        if (!currentActivity.equals("MAIN_ACTIVITY")) finish();
    }

    public void setCurrentActivity(String currentActivity) {
        this.currentActivity = currentActivity;
    }
}