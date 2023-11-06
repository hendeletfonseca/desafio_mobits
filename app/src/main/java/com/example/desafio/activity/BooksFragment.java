package com.example.desafio.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterBooks;
import com.example.desafio.entities.Book;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragment extends Fragment {
    private static final int PAGE = 1;
    private static final int PAGE_SIZE = 50;
    RecyclerView recyclerView;
    ArrayList<Book> mList;
    AdapterBooks adapterBooks;
    TextView textView;
    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = new ArrayList<>();

        if (savedInstanceState != null) {
            mList = (ArrayList<Book>) savedInstanceState.getSerializable("LIST");
        } else {
            loadBooks();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapterBooks = new AdapterBooks(getContext(), mList);
        recyclerView.setAdapter(adapterBooks);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getActivity()));
        textView = view.findViewById(R.id.tv_books_not_found);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("LIST", mList);
        super.onSaveInstanceState(outState);
    }

    private void loadBooks() {
        IceAndFireService service = ApiModule.service();
        Call<List<Book>> call = service.getBooks(PAGE, PAGE_SIZE);

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                if (!response.isSuccessful()) return;

                List<Book> booksResponse = response.body();
                if (booksResponse == null) return;

                mList.addAll(booksResponse);
                adapterBooks.notifyItemRangeInserted(0, mList.size());
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {
                textView.setVisibility(View.VISIBLE);
            }
        });
    }

}