package com.example.desafio.network;

import androidx.annotation.NonNull;

import com.example.desafio.adapters.AdapterBooks;
import com.example.desafio.entities.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiModule {
    Retrofit retrofit;
    IceAndFireService service;

    public ApiModule() {
        this.retrofit = retrofit();
        this.service = service();
    }

    public static Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://www.anapioficeandfire.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static IceAndFireService service() {
        return retrofit().create(IceAndFireService.class);
    }

    public static void getBooks(int page, int pageSize, AdapterBooks adapter, List<Book> list) {
        IceAndFireService service = ApiModule.service();
        Call<List<Book>> call = service.getBooks(page, pageSize);

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Book>> call, @NonNull retrofit2.Response<List<Book>> response) {
                if (!response.isSuccessful()) return;
                List<Book> bookList = response.body();
                if (bookList == null) return;

                list.addAll(bookList);
                adapter.notifyItemRangeInserted(0, bookList.size());
            }

            @Override
            public void onFailure(retrofit2.Call<List<Book>> call, Throwable t) {
                // TODO
            }
        });
    }
}
