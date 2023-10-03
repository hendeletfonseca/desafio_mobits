package com.example.desafio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.desafio.R;

import java.util.Objects;

import com.example.desafio.entities.Character;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SpecificCharacterActivity extends BaseActivity {
    private Character character;
    private TextView tv_name, tv_gender, tv_birthday, tv_titles;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_character);
        Objects.requireNonNull(super.getSupportActionBar()).setTitle("Character");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.character = (Character) bundle.getSerializable("CHARACTER");
        }

        tv_name = findViewById(R.id.tv_character_name);
        if (character.getName().equals("")) {
            tv_name.setText(R.string.name_not_found);
        } else {
            tv_name.setText(character.getName());
        }

        tv_gender = findViewById(R.id.tv_character_gender);
        tv_gender.setText(character.getGender());

        tv_birthday = findViewById(R.id.tv_character_birthday);
        if (character.getBorn().equals("")) {
            tv_birthday.setText(R.string.birth_not_found);
        } else {
            tv_birthday.setText(character.getBorn());
        }

        tv_titles = findViewById(R.id.tv_character_titles);
        if (character.getTitles().size() == 0) {
            tv_titles.setText(R.string.titles_not_found);
        } else {
            tv_titles.setText(character.getStringTitles());
        }

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://www.google.com/search?tbm=isch&q=" + character.getName());
                Intent intent = new Intent(SpecificCharacterActivity.this, WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}