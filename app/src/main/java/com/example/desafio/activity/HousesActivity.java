package com.example.desafio.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterHouses;
import com.example.desafio.entities.House;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HousesActivity extends BaseActivity {
    ArrayList<House> houses = new ArrayList<>();
    RecyclerView recyclerView;
    private AdapterHouses adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses);
        Objects.requireNonNull(super.getSupportActionBar()).setTitle("Houses");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_houses);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_books) {
                super.openBooksActivity();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            if (item.getItemId() == R.id.bottom_houses) {
                return true;
            }
            return false;
        });

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new AdapterHouses(this, houses);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        if (savedInstanceState == null) {
            loadHouses(1, 50);
            Log.d("HousesActivity", "SAVED INSTANCE STATE IS NULL");
        } else {
            houses.addAll((ArrayList<House>) savedInstanceState.getSerializable("HOUSES"));
            adapter.notifyItemRangeInserted(0, houses.size());
            Log.d("HousesActivity", "SAVED INSTANCE STATE SIZE: " + houses.size());
        }

    }

    public void loadHouses(int page, int pageSize) {
        IceAndFireService service = ApiModule.service();
        Call<List<House>> call = service.getHouses(page, pageSize);

        call.enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(@NonNull Call<List<House>> call, @NonNull Response<List<House>> response) {
                if (!response.isSuccessful()) return;
                List<House> houseList = response.body();
                if (houseList == null) return;

                houses.addAll(houseList);
                adapter.notifyItemRangeInserted(0, houseList.size());
            }

            @Override
            public void onFailure(@NonNull Call<List<House>> call, @NonNull Throwable t) {
                TextView textView = findViewById(R.id.tv_houses_not_found);
                textView.setVisibility(TextView.VISIBLE);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("HOUSES", houses);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_bar_houses) return false;
        return super.onOptionsItemSelected(item);
    }
}