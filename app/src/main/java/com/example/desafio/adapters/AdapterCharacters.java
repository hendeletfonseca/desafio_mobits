package com.example.desafio.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio.R;
import com.example.desafio.activity.SpecificCharacterActivity;
import com.example.desafio.entities.Character;

import java.util.List;

public class AdapterCharacters extends RecyclerView.Adapter<AdapterCharacters.CharacterViewHolder>{
    private final List<Character> charactersList;
    private final LayoutInflater mInflater;

    public AdapterCharacters(Context context, List<Character> charactersList) {
        this.mInflater = LayoutInflater.from(context);
        this.charactersList = charactersList;
    }

    @NonNull
    @Override
    public AdapterCharacters.CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.character_item_adapter, parent, false);
        return new CharacterViewHolder(mItemView, this);
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

    public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView characterItemView;
        final AdapterCharacters mAdapter;
        public CharacterViewHolder(@NonNull View itemView, AdapterCharacters adapter) {
            super(itemView);
            characterItemView = itemView.findViewById(R.id.adapter_tv_name);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();

            Character element = charactersList.get(mPosition);

            Intent intent = new Intent(view.getContext(), SpecificCharacterActivity.class);
            intent.putExtra("CHARACTER", element);
            view.getContext().startActivity(intent);
        }
    }
}
