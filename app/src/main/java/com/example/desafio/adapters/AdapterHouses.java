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
import com.example.desafio.activity.SpecificHouseActivity;
import com.example.desafio.entities.House;

import java.util.List;

public class AdapterHouses extends RecyclerView.Adapter<AdapterHouses.HouseViewHolder>{
    private final List<House> housesList;
    private final LayoutInflater mInflater;

    public AdapterHouses(Context context, List<House> housesList) {
        mInflater = LayoutInflater.from(context);
        this.housesList = housesList;
    }

    @NonNull
    @Override
    public AdapterHouses.HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.house_item_adapter, parent, false);
        return new HouseViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHouses.HouseViewHolder holder, int position) {
        House mCurrent = housesList.get(position);
        holder.houseItemView.setText(mCurrent.getName());
        holder.houseItemView2.setText(mCurrent.getRegion());
    }

    @Override
    public int getItemCount() {
        return housesList.size();
    }

    public class HouseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView houseItemView;
        public final TextView houseItemView2;
        final AdapterHouses mAdapter;
        public HouseViewHolder(@NonNull View itemView, AdapterHouses adapter) {
            super(itemView);
            houseItemView = itemView.findViewById(R.id.adapter_tv_name);
            houseItemView2 = itemView.findViewById(R.id.adapter_tv_phrase);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            House element = housesList.get(mPosition);

            Intent intent = new Intent(view.getContext(), SpecificHouseActivity.class);
            intent.putExtra("HOUSE", element);
            view.getContext().startActivity(intent);
        }
    }
}
