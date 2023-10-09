package com.example.desafio.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterCharacters;
import com.example.desafio.entities.Character;

import java.util.ArrayList;
import java.util.LinkedList;


public class CharactersActivity extends BaseActivity {
    private final LinkedList<Character> characters = new LinkedList<>();
    private AdapterCharacters adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        ActionBar actionBar = super.getSupportActionBar();
        if (actionBar != null) actionBar.setTitle("Characters");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new AdapterCharacters(this, characters);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Log.d("CharactersActivity", "Bundle is null");
            return;
        }
        addLoadedsCharacters(bundle.getStringArrayList("URLS"),
                bundle.getStringArrayList("NAMES"));
        addTabsAfterAllCharactersAreAdded();
    }


    private void addLoadedsCharacters(ArrayList<String> urls, ArrayList<String> names) {
        if ((urls == null || names == null) || (urls.size() != names.size())) return;
        int size = urls.size();
        for (int i = 0; i < size; i++) {
            characters.add(new Character(urls.get(i), names.get(i)));
            adapter.notifyItemInserted(characters.size() - 1);
        }
        // Characters are already sorted by specif book activity
    }

    private void addTabsAfterAllCharactersAreAdded() {
        int size = characters.size();
       updateCharactersTv(size);
        if (size == 0) return;
        for (int i = 1; i < size; i++) {
            String firstLetter = String.valueOf(characters.get(i).getName().charAt(0)).toUpperCase();
            String previousFirstLetter = String.valueOf(characters.get(i - 1).getName().charAt(0)).toUpperCase();
            if (!firstLetter.equals(previousFirstLetter) && isARealCharacter(characters.get(i)) && isARealCharacter(characters.get(i - 1))) {
                characters.add(i, new Character("RECYCLER_VIEW_DIV", firstLetter));
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

    private void updateCharactersTv(int listSize) {
        TextView warning = findViewById(R.id.tv_characters_not_found);
        if (listSize == 0) warning.setVisibility(View.VISIBLE);
        else warning.setVisibility(View.INVISIBLE);
    }

    private boolean isARealCharacter(Character character) {
        return !character.getUrl().equals("RECYCLER_VIEW_DIV");
    }
}