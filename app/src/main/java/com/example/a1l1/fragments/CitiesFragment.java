package com.example.a1l1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1l1.onItemClick;
import com.example.a1l1.R;
import com.example.a1l1.adapters.RecyclerDataAdapter;
import com.example.a1l1.adapters.RecyclerDataAdapter.CityWeatherData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CitiesFragment extends Fragment {
    private ArrayList<CityWeatherData> listData
            = new ArrayList<>(Arrays.asList(new CityWeatherData("Moscow", "25"),
            new CityWeatherData("St. Petersburg", "23"),
            new CityWeatherData("Sochi", "30"),
            new CityWeatherData("Kazan", "15")));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.listRecyclerView);
        RecyclerDataAdapter adapter = new RecyclerDataAdapter(listData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        adapter.SetOnItemClickListener(new RecyclerDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CityWeatherData data) {
                ((onItemClick) Objects.requireNonNull(getActivity())).onItemClicked(data.getCity(), data.getDegrees());
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
