package com.example.desafio.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterBooks;
import com.example.desafio.entities.Book;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksActivity extends BaseActivity {
    private static final int TOTAL_BOOKS = 14;
    private ArrayList<Book> books = new ArrayList<>();
    private AdapterBooks adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        ActionBar actionBar = super.getSupportActionBar();
        if (actionBar != null) actionBar.setTitle("Books");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_books);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_books) {
                //super.onClickBooks();
                return true;
            }
            if (item.getItemId() == R.id.bottom_houses) {
                super.openHousesActivity();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new AdapterBooks(this, books);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        if (savedInstanceState == null) {
            loadBooks(1, TOTAL_BOOKS);
            Log.d("BooksActivity", "Bundle is null");
        }
        else {
            books.addAll((ArrayList<Book>) savedInstanceState.getSerializable("BOOKS"));
            adapter.notifyItemRangeInserted(0, books.size());
            Log.d("BooksActivity", "Bundle is not null: " + books.size());
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("BOOKS", books);
        super.onSaveInstanceState(outState);
    }

    public void loadBooks(int page, int pageSize) {
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
                adapter.notifyItemRangeInserted(lastPos, books.size());
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {
                TextView textView = findViewById(R.id.tv_books_not_found);
                textView.setVisibility(TextView.VISIBLE);
            }
        });
    }
}