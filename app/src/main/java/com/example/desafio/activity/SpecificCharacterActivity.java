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

        TextView tv_gender = findViewById(R.id.tv_character_gender);
        tv_gender.setText(character.getGender());

        TextView tv_birthday = findViewById(R.id.tv_character_birthday);
        if (character.getBorn().equals("")) {
            tv_birthday.setText(R.string.birth_not_found);
        } else {
            tv_birthday.setText(character.getBorn());
        }

        TextView tv_titles = findViewById(R.id.tv_character_titles);
        if (character.getTitles().size() == 0) {
            tv_titles.setText(R.string.titles_not_found);
        } else {
            tv_titles.setText(character.getStringTitles());
        }

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                bundle.putString("url", "https://www.google.com/search?tbm=isch&q=" + character.getName());
//                Intent intent = new Intent(SpecificCharacterActivity.this, WebViewActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void setEndPoint(String endPoint) {
        super.endPoint = endPoint;
    }
}