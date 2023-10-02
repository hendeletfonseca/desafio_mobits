package com.example.desafio.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.desafio.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar toolbar = getSupportActionBar();
        assert toolbar != null;
        toolbar.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.toolbar_gradient));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_bar_books) {
            onClickBooks();
        } else if (itemId == R.id.action_bar_characters) {
            onClickCharacters();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickBooks() {
        Intent intent = new Intent(this, BooksActivity.class);
        startActivity(intent);
    }
    public void onClickCharacters() {
        Intent intent = new Intent(this, CharactersActivity.class);
        startActivity(intent);
    }
}