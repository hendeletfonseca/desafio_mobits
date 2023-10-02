package com.example.desafio.network;

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
}
