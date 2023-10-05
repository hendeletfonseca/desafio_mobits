package com.example.desafio.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterCharacters;
import com.example.desafio.entities.Book;
import com.example.desafio.entities.Character;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;
import com.example.desafio.util.UrlUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        if (bundle == null) return;

        addLoadedsCharacters(bundle.getStringArrayList("URLS"),
                bundle.getStringArrayList("NAMES"));
        addTabsAfterAllCharactersAreAdded();
        addCharactersFromUrl(bundle.getStringArrayList("URLS_WITHOUT_NAME"));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addLoadedsCharacters(ArrayList<String> urls, ArrayList<String> names) {
        if ((urls == null || names == null)) return;
        int size = urls.size();
        for (int i = 0; i < size; i++) {
            characters.add(new Character(urls.get(i), names.get(i)));
            adapter.notifyItemInserted(characters.size() - 1);
        }
        characters.sort(Comparator.comparing(Character::getName));
        adapter.notifyDataSetChanged();
    }

    private void addCharactersFromUrl(ArrayList<String> urls) {
        IceAndFireService service = ApiModule.service();
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            final int step = i;
            Call<Character> call = service.getCharacter(UrlUtils.getIdFromUrl(url));
            call.enqueue(new Callback<Character>() {
                @Override
                public void onResponse(@NonNull Call<Character> call, @NonNull Response<Character> response) {
                    if (!response.isSuccessful()) return;

                    Character characterResponse = response.body();
                    if (characterResponse == null) return;

                    characters.add(characterResponse);
                    adapter.notifyItemInserted(characters.size());
                    if (step == urls.size() - 1) {
                        characters.sort(Comparator.comparing(Character::getName));
                        adapter.notifyDataSetChanged();
                        addTabsAfterAllCharactersAreAdded();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Character> call, @NonNull Throwable t) {
                    Log.d("BooksActivity", "Error: " + t.getMessage());
                }
            });
        }

    }

    private void addTabsAfterAllCharactersAreAdded() {
        int size = characters.size();
        for (int i = 1; i < size; i++) {
            String firstLetter = String.valueOf(characters.get(i).getName().charAt(0)).toUpperCase();
            String previousFirstLetter = String.valueOf(characters.get(i - 1).getName().charAt(0)).toUpperCase();
            if (!firstLetter.equals(previousFirstLetter) && isARealCharacter(characters.get(i)) && isARealCharacter(characters.get(i - 1))) {
                characters.add(i, new Character("RECYCLER_VIEW_DIV", String.valueOf(firstLetter)));
                adapter.notifyItemInserted(i);
                i++;
                size++;
            }
        }
        Character firstCharacter = characters.getFirst();
        if (isARealCharacter(firstCharacter)) {
            Character firstDiv = new Character("RECYCLER_VIEW_DIV", String.valueOf(firstCharacter.getName().charAt(0)).toUpperCase());
            characters.addFirst(firstDiv);
            adapter.notifyItemInserted(0);
        }
    }

    private boolean isARealCharacter(Character character) {
        return !character.getUrl().equals("RECYCLER_VIEW_DIV");
    }
}