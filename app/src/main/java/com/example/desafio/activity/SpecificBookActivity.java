package com.example.desafio.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.desafio.R;
import com.example.desafio.entities.Book;
import com.example.desafio.util.UrlUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SpecificBookActivity extends BaseActivity {
    private Book book;
    private TextView tv_title, tv_isbn, tv_pages, tv_release_date;
    private Button bnt_pov, btn_characters;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_book);
        Objects.requireNonNull(super.getSupportActionBar()).setTitle("Book");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.book = (Book) bundle.getSerializable("BOOK");
        }

        tv_title = findViewById(R.id.tv_book_title);
        tv_title.setText(book.getName());

        tv_isbn = findViewById(R.id.tv_book_isbn);
        tv_isbn.setText(getString(R.string.isbn_placeholder, book.getIsbn()));

        tv_pages = findViewById(R.id.tv_book_pages);
        tv_pages.setText(getString(R.string.pages_placeholder, String.valueOf(book.getNumberOfPages())));

        tv_release_date = findViewById(R.id.tv_book_release_date);
        tv_release_date.setText(getString(R.string.release_date_placeholder, book.getReleased()));

        bnt_pov = findViewById(R.id.btn_overlord);
        bnt_pov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> urls = new ArrayList<>();
                ArrayList<String> names = new ArrayList<>();
                Log.d("SPECIFIC_BOOK", "POV SIZE " + book.getPovCharacters().size());
                for (String pov : book.getPovCharacters()) {
                    String name = verificarLinkNoJson(pov);
                    if (name != null) {
                        urls.add(pov);
                        names.add(name);
                    } else {
                        Log.d("SPECIFIC_BOOK", "Name NULL: " + pov);
                    }
                }
                Bundle newBundle = new Bundle();
                newBundle.putStringArrayList("URLS", urls);
                newBundle.putStringArrayList("NAMES", names);
                Intent intent = new Intent(SpecificBookActivity.this, CharactersActivity.class);
                intent.putExtras(newBundle);
                startActivity(intent);
            }
        });

        btn_characters = findViewById(R.id.btn_all_characters);
        btn_characters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpecificBookActivity.this, CharactersActivity.class);
                startActivity(intent);
            }
        });
        fab = findViewById(R.id.fab);
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

    public String verificarLinkNoJson(String link) {
        Log.d("SPECIFICBOOK" , "verificarLinkNoJson: " + link);
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

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has(link)) {
                    String valor = jsonObject.get(link).getAsString();
                    Log.d("SPECIFIC_BOOK", "SIM - " + valor);
                    return valor;
                } else {
                    Log.d("SPECIFIC_BOOK", "NÃO - " + link);
                }
            } else {
                Log.d("SPECIFIC_BOOK", "O conteúdo do arquivo JSON não é um objeto JSON válido.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}