package com.example.desafio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterCharacters;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;
import com.example.desafio.entities.Character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CharactersActivity extends BaseActivity {
    private final LinkedList<Character> characters = new LinkedList<>();
    private AdapterCharacters adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        Objects.requireNonNull(super.getSupportActionBar()).setTitle("Characters");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new AdapterCharacters(this, characters);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            int firstEmptyPage = 44;
            for (int i = 1; i <= firstEmptyPage; i++) {
                loadCharacters(i, 50);
            }
        }
        if (bundle != null) {
            ArrayList<String> urls = bundle.getStringArrayList("URLS");
            ArrayList<String> names = bundle.getStringArrayList("NAMES");
            if (urls == null || names == null) return;
            for (int i = 0; i < urls.size(); i++) {
                characters.add(new Character(urls.get(i), names.get(i)));
                adapter.notifyItemInserted(i);
            }
            characters.sort(Comparator.comparing(Character::getName));
            adapter.notifyDataSetChanged();
        }

    }

    public void loadCharacters(int page, int pageSize) {
        Retrofit retrofit = ApiModule.retrofit();
        IceAndFireService service = ApiModule.service();
        Call<List<Character>> call = service.getCharacters(page, pageSize);

        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(@NonNull Call<List<Character>> call, @NonNull Response<List<Character>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<Character> charactersResponse = response.body();
                if (charactersResponse == null || charactersResponse.isEmpty()) {
                    characters.sort(Comparator.comparing(Character::getName));
                    adapter.notifyDataSetChanged();
                    return;
                }
                int lastPos = characters.size() - 1;

                for (Character character : charactersResponse) {
                    if (!character.getName().equals("")) {
                        characters.add(character);
                    }
                }

                adapter.notifyItemRangeInserted(lastPos, characters.size() - 1);
            }

            @Override
            public void onFailure(@NonNull Call<List<Character>> call, @NonNull Throwable t) {
            }
        });
    }

    public void loadCharacter(int id) {
        IceAndFireService service = ApiModule.service();
        Call<Character> call = service.getCharacter(id);
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(@NonNull Call<Character> call, @NonNull Response<Character> response) {
                if (!response.isSuccessful()) return;

                Character character = response.body();
                if (character == null) return;

                characters.add(character);
                characters.sort(Comparator.comparing(Character::getName));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<Character> call, @NonNull Throwable t) {
            }
        });
    }
}