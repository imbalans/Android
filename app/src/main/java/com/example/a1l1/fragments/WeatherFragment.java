package com.example.a1l1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a1l1.OnItemClick;
import com.example.a1l1.R;
import com.example.a1l1.models.WeatherRequest;

import java.util.Locale;
import java.util.Objects;

public class WeatherFragment extends Fragment {
    public final static String cityKey = "cityKey";
    public final static String degreesKey = "degreesKey";
    public final static String pressureKey = "pressureKey";
    public final static String humidityKey = "humidityKey";
    public final static String windSpeedKey = "windSpeedKey";
    TextView cityNameView, degreesView, pressureView, humidityView, windSpeedView;
    Button btnHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnItemClick) Objects.requireNonNull(getActivity())).onHistoryClicked();
            }
        });

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
        cityNameView = Objects.requireNonNull(getView()).findViewById(R.id.CityView);
        degreesView = getView().findViewById(R.id.DegreesView);
        pressureView = getView().findViewById(R.id.PressureView);
        humidityView = getView().findViewById(R.id.HumidityView);
        windSpeedView = getView().findViewById(R.id.WindSpeedView);
        btnHistory = getView().findViewById(R.id.btnHistory);
    }

    public void updateCity(WeatherRequest weatherRequest) {
        cityNameView.setText(weatherRequest.getName());

        String temperatureValue = String.format(Locale.getDefault(), "%.2f", weatherRequest.getMain().getTemp());
        degreesView.setText(temperatureValue);

        String pressureText = String.format(Locale.getDefault(), "%d", weatherRequest.getMain().getPressure());
        pressureView.setText(pressureText);

        String waterValue = String.format(Locale.getDefault(), "%d", weatherRequest.getMain().getHumidity());
        humidityView.setText(waterValue);

        String windSpeed = String.format(Locale.getDefault(), " %d", weatherRequest.getWind().getSpeed());
        windSpeedView.setText(windSpeed);
    }
}
