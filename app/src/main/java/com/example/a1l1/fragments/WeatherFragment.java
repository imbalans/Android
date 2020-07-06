package com.example.a1l1.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a1l1.R;


public class WeatherFragment extends Fragment {
    public final static String cityKey = "cityKey";
    public final static String degreesKey = "degreesKey";
    public final static String pressureKey = "pressureKey";
    public final static String humidityKey = "humidityKey";
    public final static String windSpeedKey = "windSpeedKey";
    TextView cityNameView, degreesView, pressureView, humidityView, windSpeedView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        if (getArguments() != null) {
            String city = getArguments().getString(cityKey);
            cityNameView.setText(city);

            String temperatureValue = getArguments().getString(degreesKey);
            degreesView.setText(temperatureValue);

            String pressureText = getArguments().getString(pressureKey);
            pressureView.setText(pressureText);

            String humidityValue = getArguments().getString(humidityKey);
            humidityView.setText(humidityValue);

            String windSpeed = getArguments().getString(windSpeedKey);
            windSpeedView.setText(windSpeed);

            sharedPreferences.edit().putString(cityKey, city).apply();
            sharedPreferences.edit().putString(degreesKey, temperatureValue).apply();
            sharedPreferences.edit().putString(pressureKey, pressureText).apply();
            sharedPreferences.edit().putString(humidityKey, humidityValue).apply();
            sharedPreferences.edit().putString(windSpeedKey, windSpeed).apply();
        }
    }

    private void initViews() {
        cityNameView = requireView().findViewById(R.id.CityView);
        degreesView = requireView().findViewById(R.id.DegreesView);
        pressureView = requireView().findViewById(R.id.PressureView);
        humidityView = requireView().findViewById(R.id.HumidityView);
        windSpeedView = requireView().findViewById(R.id.WindSpeedView);
    }
}
