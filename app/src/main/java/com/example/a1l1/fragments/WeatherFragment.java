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
import com.example.a1l1.models.WeatherRequest;

import java.util.Locale;

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

        if (getArguments() != null) {
            String city = getArguments().getString(cityKey);
            String degrees = getArguments().getString(degreesKey);
            String pressure = getArguments().getString(pressureKey);
            String humidity = getArguments().getString(humidityKey);
            String windSpeed = getArguments().getString(windSpeedKey);
            cityNameView.setText(city);
            degreesView.setText(degrees);
            pressureView.setText(pressure);
            humidityView.setText(humidity);
            windSpeedView.setText(windSpeed);
        }
    }

    private void initViews() {
        cityNameView = requireView().findViewById(R.id.CityView);
        degreesView = requireView().findViewById(R.id.DegreesView);
        pressureView = requireView().findViewById(R.id.PressureView);
        humidityView = requireView().findViewById(R.id.HumidityView);
        windSpeedView = requireView().findViewById(R.id.WindSpeedView);
    }

    public void updateCity(WeatherRequest weatherRequest) {
        cityNameView.setText(weatherRequest.getName());

        String temperatureValue = String.format(Locale.getDefault(), "%.2f", weatherRequest.getMain().getTemp());
        degreesView.setText(temperatureValue);

        String pressureText = String.format(Locale.getDefault(), "%d", weatherRequest.getMain().getPressure());
        pressureView.setText(pressureText);

        String waterValue = String.format(Locale.getDefault(), "%d", weatherRequest.getMain().getHumidity());
        humidityView.setText(waterValue);

        String windSpeed = String.format(Locale.getDefault(), " %.2f", weatherRequest.getWind().getSpeed());
        windSpeedView.setText(windSpeed);
    }
}
