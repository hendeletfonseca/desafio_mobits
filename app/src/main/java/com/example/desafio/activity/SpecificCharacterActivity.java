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

public class SpecificCharacterActivity extends SpecificActivity {
    private Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_character);
        Objects.requireNonNull(super.getSupportActionBar()).setTitle("Character");

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) finish();
        this.character = (Character) bundle.getSerializable("CHARACTER");

        setEndPoint(character.getName());

        TextView tv_name = findViewById(R.id.tv_character_name);
        tv_name.setText(character.getName());
        if (character.getName().isEmpty()) tv_name.setVisibility(View.GONE);

        TextView tv_gender = findViewById(R.id.tv_character_gender);
        tv_gender.setText(character.getGender());
        if (character.getGender().isEmpty()) tv_gender.setVisibility(View.GONE);

        TextView tv_birthday = findViewById(R.id.tv_character_birthday);
        if (character.getBorn().equals("") || character.getBorn().isEmpty()) {
            tv_birthday.setVisibility(View.GONE);
        } else {
            tv_birthday.setText(character.getBorn());
        }

        TextView tv_titles = findViewById(R.id.tv_character_titles);
        if (character.getTitles().size() == 0 || character.getTitles().isEmpty()) {
            tv_titles.setVisibility(View.GONE);
        } else {
            tv_titles.setText(character.getStringTitles());
        }

    }

    @Override
    public void setEndPoint(String endPoint) {
        super.endPoint = endPoint;
    }
}