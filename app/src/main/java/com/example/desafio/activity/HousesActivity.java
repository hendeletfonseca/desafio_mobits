package com.example.desafio.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterBooks;
import com.example.desafio.adapters.AdapterHouses;
import com.example.desafio.entities.House;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HousesActivity extends BaseActivity {
    LinkedList<House> houses = new LinkedList<>();
    RecyclerView recyclerView;
    private AdapterHouses adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses);
        Objects.requireNonNull(super.getSupportActionBar()).setTitle("Houses");

        loadHouses(1,50);

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new AdapterHouses(this, houses);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
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

            }
        });
    }
}