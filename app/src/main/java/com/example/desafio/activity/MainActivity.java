package com.example.desafio.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.desafio.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = super.getSupportActionBar();
        if (actionBar != null) actionBar.setTitle("Desafio");

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        frameLayout = findViewById(R.id.frameLayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_books) {
                if (bottomNavigationView.getSelectedItemId() != R.id.bottom_books) {
                    setFragment(new BooksFragment());
                }
                return true;
            }
            if (item.getItemId() == R.id.bottom_houses) {
                if (bottomNavigationView.getSelectedItemId() != R.id.bottom_houses) {
                    setFragment(new HousesFragment());
                }
                return true;
            }
            return false;
        });

        if (savedInstanceState != null) {
            bottomNavigationView.setSelectedItemId(savedInstanceState.getInt("SELECTED_ITEM_ID"));

            if (bottomNavigationView.getSelectedItemId() == R.id.bottom_books) {
                setFragment(new BooksFragment());
            }
            if (bottomNavigationView.getSelectedItemId() == R.id.bottom_houses) {
                setFragment(new HousesFragment());
            }
        } else {
            setFragment(new BooksFragment());
        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("SELECTED_ITEM_ID", bottomNavigationView.getSelectedItemId());
        super.onSaveInstanceState(outState);
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

    }

}