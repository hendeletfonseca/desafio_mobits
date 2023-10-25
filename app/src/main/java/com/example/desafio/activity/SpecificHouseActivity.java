package com.example.desafio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.desafio.R;
import com.example.desafio.entities.Character;
import com.example.desafio.entities.House;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;
import com.example.desafio.util.UrlUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecificHouseActivity extends SpecificActivity {
    private House house;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_house);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) finish();
        house = (House) bundle.getSerializable("HOUSE");

        setEndPoint(house.getName());

        TextView tv_name = findViewById(R.id.tv_name);
        tv_name.setText(house.getName());
        if (house.getName().isEmpty()) tv_name.setVisibility(View.GONE);

        TextView tv_phrase = findViewById(R.id.tv_phrase);
        tv_phrase.setText(house.getWordsString());
        if (house.getWords().isEmpty()) tv_phrase.setVisibility(View.GONE);

        TextView tv_region = findViewById(R.id.tv_region);
        tv_region.setText(house.getRegion());
        if (house.getRegion().isEmpty()) tv_region.setVisibility(View.GONE);

        TextView tv_titles = findViewById(R.id.tv_titles);
        tv_titles.setText(house.getTitlesString());
        if (house.getTitlesString().equals("Sem títulos!")) tv_titles.setVisibility(View.GONE);

        Button btn_heir = findViewById(R.id.btn_heir);
        btn_heir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (house.getHeir().equals("")) {
                    showToast("A casa " + house.getName() + " não possui Herdeiro!");
                    return;
                }
                openCharacterActivity(house.getHeir());
            }
        });
        Button btn_currentLord = findViewById(R.id.btn_overlord);
        btn_currentLord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (house.getCurrentLord().equals("")) {
                    showToast("A casa " + house.getName() + " não possui Lorde!");
                    return;
                }
                openCharacterActivity(house.getOverlord());
            }
        });
    }

    @Override
    public void setEndPoint(String endPoint) {
        super.endPoint = endPoint;
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
                showToast("Erro ao carregar personagem!");
            }
        });
    }

    private void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            CountDownTimer toastCountDown = new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long l) {
                    toast.show();
                }

                @Override
                public void onFinish() {
                    toast.cancel();
                    toast = null;
                }
            };
            toastCountDown.start();
        }
    }
}