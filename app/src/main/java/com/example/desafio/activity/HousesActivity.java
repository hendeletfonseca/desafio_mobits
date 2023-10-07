package com.example.desafio.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterHouses;
import com.example.desafio.entities.House;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;

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
        setCurrentActivity("HOUSES_ACTIVITY");
        setContentView(R.layout.activity_houses);
        Objects.requireNonNull(super.getSupportActionBar()).setTitle("Houses");

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new AdapterHouses(this, houses);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) loadHouses(1, 50);
        else houses = (ArrayList<House>) bundle.getSerializable("HOUSES");

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
                Log.d("HOUSES_ACTIVITY", "loadHouses ON FAILURE Throwable: " + t.getMessage());
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