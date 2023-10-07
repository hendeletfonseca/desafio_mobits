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
import com.example.desafio.entities.Book;

import java.util.List;

public class AdapterBooks extends RecyclerView.Adapter<AdapterBooks.BookViewHolder> {
    private final List<Book> booksList;
    private final LayoutInflater mInflater;

    public AdapterBooks(Context context, List<Book> booksList) {
        mInflater = LayoutInflater.from(context);
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public AdapterBooks.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.book_item_adapter, parent, false);
        return new BookViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBooks.BookViewHolder holder, int position) {
        Book mCurrent = booksList.get(position);
        holder.bookItemView.setText(mCurrent.getName());
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView bookItemView;
        final AdapterBooks mAdapter;

        public BookViewHolder(@NonNull View itemView, AdapterBooks adapter) {
            super(itemView);
            bookItemView = itemView.findViewById(R.id.adapter_tv_name);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();

            Book element = booksList.get(mPosition);

            Intent intent = new Intent(view.getContext(), SpecificBookActivity.class);
            intent.putExtra("BOOK", element);
            view.getContext().startActivity(intent);
        }


    }
}
