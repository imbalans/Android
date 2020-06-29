package com.example.a1l1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.rest.OpenWeatherRepo;
import com.rest.entities.WeatherRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectClass {
    private final MainActivity mainActivity;

    public ConnectClass(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void connectOnServer(String city) {
        OpenWeatherRepo.getInstance().getAPI().loadWeather(city + ",ru",
                "1bcaf7596191dbaeade6672a4744db9a", "metric")
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequest> call,
                                           @NonNull Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            mainActivity.onItemClicked(response.body());
                        } else {
                            if (response.code() == 404) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                                builder.setTitle("Такой город не найден!")
                                        .setMessage("Введите название города еще раз.")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", (dialog, which) -> {
                                        }).create().show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                        builder.setMessage("Отсутствует интернет соединение!")
                                .setCancelable(false)
                                .setPositiveButton("OK", (dialog, which) -> {
                                }).create().show();
                    }
                });
    }
}
