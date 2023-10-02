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
import com.example.desafio.activity.SpecificBookActivity;

import java.io.Serializable;
import java.util.List;

public class Adapter<T extends Serializable> extends RecyclerView.Adapter<Adapter<T>.ViewHolder> {
    public int resource;
    private final List<T> list;
    private final LayoutInflater mInflater;

    public Adapter(Context context, List<T> list, int resource) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.resource = resource;
    }

    @NonNull
    @Override
    public Adapter<T>.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(resource, parent, false);
        return new ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        T mCurrent = list.get(position);
        holder.textView.setText(mCurrent.toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView textView;
        final Adapter<T> mAdapter;
        public ViewHolder(@NonNull View itemView, Adapter<T> adapter) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();

            T element = list.get(mPosition);

            Intent intent = new Intent(view.getContext(), SpecificBookActivity.class);
            intent.putExtra("DATA", element);
            view.getContext().startActivity(intent);
        }
    }
}
