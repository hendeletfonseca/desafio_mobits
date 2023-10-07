package com.example.desafio.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.desafio.R;
import com.example.desafio.entities.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class SpecificBookActivity extends BaseActivity {
    private Book book;
    private JsonObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_book);
        Objects.requireNonNull(super.getSupportActionBar()).setTitle("Book");

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) finish();
        this.book = (Book) bundle.getSerializable("BOOK");

        TextView tv_title = findViewById(R.id.tv_book_title);
        tv_title.setText(book.getName());

        TextView tv_isbn = findViewById(R.id.tv_book_isbn);
        tv_isbn.setText(getString(R.string.isbn_placeholder, book.getIsbn()));

        TextView tv_pages = findViewById(R.id.tv_book_pages);
        tv_pages.setText(getString(R.string.pages_placeholder, String.valueOf(book.getNumberOfPages())));

        TextView tv_release_date = findViewById(R.id.tv_book_release_date);
        tv_release_date.setText(getString(R.string.release_date_placeholder, book.getReleased()));

        readJson();

        Button bnt_pov = findViewById(R.id.btn_overlord);
        bnt_pov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCharacters(book.getPovCharacters());
            }
        });

        Button btn_characters = findViewById(R.id.btn_all_characters);
        btn_characters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCharacters(book.getCharacters());
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://www.google.com/search?tbm=isch&q=" + book.getName());
                Intent intent = new Intent(SpecificBookActivity.this, WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public final String verificarLinkNoJson(String link) {
        if (jsonObject.has(link)) return jsonObject.get(link).getAsString();
        return null;
    }

    public void openCharacters(List<String> characters) {
        Map<String, String> map = new HashMap<>();
        for (String pov : characters) {
            String name = verificarLinkNoJson(pov);
            if (name != null) {
                map.put(pov, name);
            }
        }
        List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (Map.Entry<String, String> entry : list) {
            urls.add(entry.getKey());
            names.add(entry.getValue());
        }

        Bundle newBundle = new Bundle();
        newBundle.putStringArrayList("URLS", urls);
        newBundle.putStringArrayList("NAMES", names);
        Intent intent = new Intent(SpecificBookActivity.this, CharactersActivity.class);
        intent.putExtras(newBundle);
        startActivity(intent);
    }

    private void readJson() {
        try {
            int resourceId = R.raw.personagens_key_value;
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(resourceId);

            Scanner scanner = new Scanner(inputStream, "UTF-8");
            StringBuilder jsonString = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonString.append(scanner.nextLine());
            }
            scanner.close();

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(jsonString.toString());
            if (jsonElement.isJsonObject()) jsonObject = jsonElement.getAsJsonObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}