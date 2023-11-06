package com.example.desafio.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.desafio.R;
import com.example.desafio.adapters.AdapterHouses;
import com.example.desafio.entities.House;
import com.example.desafio.network.ApiModule;
import com.example.desafio.network.IceAndFireService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HousesFragment extends Fragment {
    private static final int LIMIT = 9;
    private static final int PAGE_SIZE = 50;
    private static int page = 1;
    private RecyclerView recyclerView;
    private ArrayList<House> mList;
    private AdapterHouses adapterHouses;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    public HousesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = new ArrayList<>();

        if (savedInstanceState != null) {
            mList = (ArrayList<House>) savedInstanceState.getSerializable("LIST");
        } else {
            loadHouses(page);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_houses, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapterHouses = new AdapterHouses(getContext(), mList);
        recyclerView.setAdapter(adapterHouses);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getActivity()));

        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (page < LIMIT) {
                        page++;
                        progressBar.setVisibility(View.VISIBLE);
                        loadHouses(page);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        ArrayList<House> miniList = new ArrayList<>();
        for (int i = 0; i < PAGE_SIZE; i++) {
            miniList.add(mList.get(i));
        }
        outState.putSerializable("LIST", miniList);
        super.onSaveInstanceState(outState);
    }

    public void loadHouses(int page) {
        IceAndFireService service = ApiModule.service();
        Call<List<House>> call = service.getHouses(page, PAGE_SIZE);

        call.enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(@NonNull Call<List<House>> call, @NonNull Response<List<House>> response) {
                if (!response.isSuccessful()) return;
                List<House> houseList = response.body();
                if (houseList == null) return;

                int positionStart = mList.size();
                mList.addAll(houseList);
                adapterHouses.notifyItemRangeInserted(positionStart, mList.size());
            }

            @Override
            public void onFailure(@NonNull Call<List<House>> call, @NonNull Throwable t) {
            }
        });
    }
}