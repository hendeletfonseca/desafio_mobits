package com.example.desafio.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desafio.R;
import com.example.desafio.entities.House;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;
import com.example.desafio.entities.Character;
import com.example.desafio.util.UrlUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            house = (House) bundle.getSerializable("HOUSE");
        }

        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(house.getName());

        tv_phrase = findViewById(R.id.tv_phrase);
        tv_phrase.setText(house.getWordsString());

        tv_region = findViewById(R.id.tv_region);
        tv_region.setText(house.getRegion());

        tv_titles = findViewById(R.id.tv_titles);
        tv_titles.setText(house.getTitlesString());

        btn_heir = findViewById(R.id.btn_heir);
        btn_heir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (house.getHeir().equals("")) {
                    showToast("This house has no heir");
                    return;
                }
                openCharacterActivity(house.getHeir());
            }
        });
        btn_overlord = findViewById(R.id.btn_overlord);
        btn_overlord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (house.getOverlord().equals("")) {
                    showToast("This house has no overlord");
                    return;
                }
                openCharacterActivity(house.getOverlord());
            }
        });
    }

    private void openCharacterActivity(String character_url) {
        IceAndFireService service = ApiModule.service();
        Call<Character> call = service.getCharacter(UrlUtils.getIdFromUrl(character_url));
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(@NonNull Call<Character> call, @NonNull Response<Character> response) {
                if (!response.isSuccessful()) return;
                Character characterResponse = response.body();
                if (characterResponse == null) return;
                Bundle bundle = new Bundle();
                bundle.putSerializable("CHARACTER", characterResponse);
                Intent intent = new Intent(SpecificHouseActivity.this, SpecificCharacterActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onFailure(@NonNull Call<Character> call, @NonNull Throwable t) {

            }
        });
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}