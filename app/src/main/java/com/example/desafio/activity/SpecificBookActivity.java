package com.example.desafio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.desafio.R;
import com.example.desafio.entities.Book;
import com.example.desafio.util.UrlUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

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
                Log.d("SPECIFIC_BOOK_ACTIVITY", "onClick: " + book.getCharacters().size());
                List<String> characters_urls = book.getCharacters();
                int[] ids = new int[characters_urls.size()];
                for (int i = 0; i < characters_urls.size(); i++) {
                    ids[i] = UrlUtils.getIdFromUrl(characters_urls.get(i));
                }
                Bundle bundle = new Bundle();
                bundle.putIntArray("IDS", ids);
                Intent intent = new Intent(SpecificBookActivity.this, CharactersActivity.class);
                intent.putExtras(bundle);
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


}