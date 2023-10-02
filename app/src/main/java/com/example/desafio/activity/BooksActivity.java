package com.example.desafio.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.adapters.AdapterBooks;
import com.example.desafio.R;
import com.example.desafio.entities.Book;
import com.example.desafio.network.IceAndFireService;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BooksActivity extends BaseActivity {
    private LinkedList<Book> books = new LinkedList<>();
    private RecyclerView recyclerView;
    private AdapterBooks adapter;
    //private Adapter<Book> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        Objects.requireNonNull(super.getSupportActionBar()).setTitle("Books");


        loadBooks(1, 14);

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new AdapterBooks(this, books);
        //adapter = new Adapter<Book>(this, books, R.layout.book_item_adapter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
    }


    public void loadBooks(int page, int pageSize) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.anapioficeandfire.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IceAndFireService service = retrofit.create(IceAndFireService.class);
        Call<List<Book>> call = service.getBooks(page, pageSize);

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                if (!response.isSuccessful()) return;

                List<Book> booksResponse = response.body();
                if (booksResponse == null) return;

                int lastPos = books.size() - 1;
                books.addAll(booksResponse);
                adapter.notifyItemRangeInserted(lastPos, books.size() - 1);
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {
                Log.d("BooksActivity", "Error: " + t.getMessage());
            }
        });
    }
}