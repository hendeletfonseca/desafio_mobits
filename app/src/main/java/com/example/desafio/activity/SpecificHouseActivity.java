package com.example.desafio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.desafio.R;
import com.example.desafio.entities.House;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SpecificHouseActivity extends BaseActivity {
    private House house;
    private FloatingActionButton fab;
    private TextView tv_name, tv_phrase, tv_region, tv_titles;
    private Button btn_overlord, btn_heir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_house);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://www.google.com/search?tbm=isch&q=" + house.getName());
                Intent intent = new Intent(SpecificHouseActivity.this, WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(house.getName());

        tv_phrase = findViewById(R.id.tv_phrase);
        tv_phrase.setText(house.getWords());
    }
}