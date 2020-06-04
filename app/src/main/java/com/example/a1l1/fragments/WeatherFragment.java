package com.example.a1l1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a1l1.R;

import java.util.Objects;

public class WeatherFragment extends Fragment {
    public final static String cityKey = "cityKey";
    public final static String degreesKey = "degreesKey";
    TextView cityNameView, degreesView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

        if (getArguments() != null) {
            String city = getArguments().getString(cityKey);
            String degrees = getArguments().getString(degreesKey);
            cityNameView.setText(city);
            degreesView.setText(degrees);
        }
    }

    private void initViews() {
        cityNameView = Objects.requireNonNull(getView()).findViewById(R.id.CityView);
        degreesView = getView().findViewById(R.id.DegreesView);
    }

    public void updateCity(String city, String degrees) {
        cityNameView.setText(city);
        degreesView.setText(degrees);
    }
}
