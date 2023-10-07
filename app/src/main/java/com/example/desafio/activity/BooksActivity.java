package com.example.desafio.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterBooks;
import com.example.desafio.entities.Book;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksActivity extends BaseActivity {
    private static final int TOTAL_BOOKS = 14;
    private LinkedList<Book> books = new LinkedList<>();
    private AdapterBooks adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCurrentActivity("BOOKS_ACTIVITY");
        setContentView(R.layout.activity_books);
        ActionBar actionBar = super.getSupportActionBar();
        if (actionBar != null) actionBar.setTitle("Books");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new AdapterBooks(this, books);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) loadBooks(1, TOTAL_BOOKS);
        else books = (LinkedList<Book>) bundle.getSerializable("BOOKS");

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("BOOKS", books);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_bar_books) return false;
        return super.onOptionsItemSelected(item);
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
                Log.d("BooksActivity", "Error: " + t.getMessage());
            }
        });
    }
}