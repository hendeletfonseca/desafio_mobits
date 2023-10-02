package com.example.desafio.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterCharacters;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;
import com.example.desafio.entities.Character;

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

        int firstEmptyPage = 44;
        for (int i = 1; i < firstEmptyPage; i++) {
            loadCharacters(i, 50);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new AdapterCharacters(this, characters);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
    }

    public void loadCharacters(int page, int pageSize) {
        Retrofit retrofit = ApiModule.retrofit();
        IceAndFireService service = ApiModule.service();
        Call<List<Character>> call = service.getCharacters(page, pageSize);

        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(@NonNull Call<List<Character>> call, @NonNull Response<List<Character>> response) {
                if (!response.isSuccessful()) {
                    Log.d("CHARACTERS_ACTIVITY", "onResponse: " + response.code());
                    return;
                }
                List<Character> charactersResponse = response.body();
                if (charactersResponse == null || charactersResponse.isEmpty()) {
                    Log.d("CHARACTERS_ACTIVITY", "onResponse: charactersResponse is null");
                    return;
                }
                int lastPos = characters.size() - 1;

                for (Character character: charactersResponse) {
                    if (!character.getName().equals("")){
                        characters.add(character);
                        adapter.notifyItemInserted(lastPos++);
                    }
                }

                characters.sort(Comparator.comparing(Character::getName));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Character>> call, @NonNull Throwable t) {
                Log.d("CHARACTERS_ACTIVITY", "onFailure: " + t.getMessage());
            }
        });
    }
}