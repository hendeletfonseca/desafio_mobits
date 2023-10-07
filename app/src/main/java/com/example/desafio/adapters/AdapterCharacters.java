package com.example.desafio.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.activity.SpecificCharacterActivity;
import com.example.desafio.entities.Character;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;
import com.example.desafio.util.UrlUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterCharacters extends RecyclerView.Adapter<AdapterCharacters.CharacterViewHolder> {
    private final List<Character> charactersList;
    private final LayoutInflater mInflater;
    private static final int TYPE_DIV = 0;
    private static final int TYPE_ITEM = 1;

    public AdapterCharacters(Context context, List<Character> charactersList) {
        this.mInflater = LayoutInflater.from(context);
        this.charactersList = charactersList;
    }

    @NonNull
    @Override
    public AdapterCharacters.CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView;
        if (viewType == TYPE_ITEM)
            mItemView = mInflater.inflate(R.layout.character_item_adapter, parent, false);
        else
            mItemView = mInflater.inflate(R.layout.div_item_adapter, parent, false);

        return new CharacterViewHolder(mItemView, this, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCharacters.CharacterViewHolder holder, int position) {
        Character mCurrent = charactersList.get(position);
        holder.characterItemView.setText(mCurrent.getName());
    }

    @Override
    public int getItemCount() {
        return charactersList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return charactersList.get(position).getUrl().equals("RECYCLER_VIEW_DIV") ? 0 : 1;
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView characterItemView;
        final AdapterCharacters mAdapter;

        public CharacterViewHolder(@NonNull View itemView, AdapterCharacters adapter, int type) {
            super(itemView);
            characterItemView = itemView.findViewById(R.id.adapter_tv_name);
            this.mAdapter = adapter;
            if(type == TYPE_ITEM) itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();

            Character element = charactersList.get(mPosition);
            if (element.getUrl().equals("RECYCLER_VIEW_DIV")) {
                return;
            }

            IceAndFireService service = ApiModule.service();
            Call<Character> call = service.getCharacter(UrlUtils.getIdFromUrl(element.getUrl()));
            call.enqueue(new Callback<Character>() {
                @Override
                public void onResponse(@NonNull Call<Character> call, @NonNull Response<Character> response) {
                    if (!response.isSuccessful()) return;

                    Character character = response.body();
                    if (character == null) return;
                    charactersList.set(mPosition, character);
                    Intent intent = new Intent(view.getContext(), SpecificCharacterActivity.class);
                    intent.putExtra("CHARACTER", character);
                    view.getContext().startActivity(intent);
                }

                @Override
                public void onFailure(@NonNull Call<Character> call, @NonNull Throwable t) {
                    Log.d("AdapterCharacters", "onFailure: " + t.getMessage());
                }
            });
        }
    }
}
