package com.example.desafio.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterBooks;
import com.example.desafio.adapters.AdapterHouses;
import com.example.desafio.entities.Book;
import com.example.desafio.entities.House;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private static final int TOTAL_BOOKS = 14;
    private static final int TOTAL_HOUSES = 50;
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<House> houses = new ArrayList<>();
    private AdapterBooks adapterBooks;
    private AdapterHouses adapterHouses;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = super.getSupportActionBar();
        if (actionBar != null) actionBar.setTitle("Desafio");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapterBooks = new AdapterBooks(this, books);
        adapterHouses = new AdapterHouses(this, houses);
        recyclerView.setAdapter(adapterBooks);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_books) {
                if (bottomNavigationView.getSelectedItemId() == R.id.bottom_books) {
                    return true;
                }
                recyclerView.setAdapter(adapterBooks);
                return true;
            }
            if (item.getItemId() == R.id.bottom_houses) {
                if (bottomNavigationView.getSelectedItemId() == R.id.bottom_houses) {
                    return true;
                }
                recyclerView.setAdapter(adapterHouses);
                return true;
            }
            return false;
        });

        if (savedInstanceState != null) {
            books.addAll((ArrayList<Book>) savedInstanceState.getSerializable("BOOKS"));
            houses.addAll((ArrayList<House>) savedInstanceState.getSerializable("HOUSES"));
            adapterBooks.notifyItemRangeInserted(0, books.size());
            adapterHouses.notifyItemRangeInserted(0, houses.size());
            bottomNavigationView.setSelectedItemId(savedInstanceState.getInt("SELECTED_ITEM_ID"));
        } else {
            loadBooks(1, TOTAL_BOOKS);
            loadHouses(1, TOTAL_HOUSES);
            bottomNavigationView.setSelectedItemId(R.id.bottom_books);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("BOOKS", books);
        outState.putSerializable("HOUSES", houses);
        outState.putInt("SELECTED_ITEM_ID", bottomNavigationView.getSelectedItemId());
        super.onSaveInstanceState(outState);
    }

    private void loadBooks(int page, int pageSize) {
        IceAndFireService service = ApiModule.service();
        Call<List<Book>> call = service.getBooks(page, pageSize);

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                if (!response.isSuccessful()) return;

                List<Book> booksResponse = response.body();
                if (booksResponse == null) return;

                int lastPos = books.size();
                books.addAll(booksResponse);
                adapterBooks.notifyItemRangeInserted(lastPos, books.size());
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {
//                TextView textView = findViewById(R.id.tv_not_found);
//                textView.setVisibility(TextView.VISIBLE);
            }
        });
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
                adapterHouses.notifyItemRangeInserted(0, houseList.size());
            }

            @Override
            public void onFailure(@NonNull Call<List<House>> call, @NonNull Throwable t) {
                TextView textView = findViewById(R.id.tv_houses_not_found);
                textView.setVisibility(TextView.VISIBLE);
            }
        });
    }

}