package com.example.desafio.network;

import com.example.desafio.entities.Book;
import com.example.desafio.entities.Character;
import com.example.desafio.entities.House;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IceAndFireService {
    public static final String BASE_URL = "https://www.anapioficeandfire.com/api/";
    @GET("books")
    Call<List<Book>> getBooks(@Query("page") int page, @Query("pageSize") int pageSize);
    @GET("books/{id}")
    Call<Book> getBook(@Path("id") int id);

    @GET("characters")
    Call<List<Character>> getCharacters(@Query("page") int page, @Query("pageSize") int pageSize);
    @GET("characters/{id}")
    Call<Character> getCharacter(@Path("id") int id);

    @GET("houses")
    Call<List<House>> getHouses(@Query("page") int page, @Query("pageSize") int pageSize);
    @GET("houses/{id}")
    Call<House> getHouse(@Path("id") int id);
    @GET("{path}")
    Call<List<Serializable>> getList(@Path("path") String path, @Query("page") int page, @Query("pageSize") int pageSize);
    @GET("{path}/{id}")
    Call<Serializable> get(@Path("path") String path, @Path("id") int id);
}
