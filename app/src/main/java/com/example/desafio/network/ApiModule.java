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
    public static Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://www.anapioficeandfire.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static IceAndFireService service() {
        return retrofit().create(IceAndFireService.class);
    }
}
