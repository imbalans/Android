package com.example.a1l1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rest.OpenWeatherRepo;
import com.rest.entities.WeatherRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectClass {

    @Nullable
    private OnItemClick listener;

    public void setListener(@Nullable OnItemClick listener) {
        this.listener = listener;
    }

    public void connectOnServer(String city) {
        OpenWeatherRepo.getInstance().getAPI().loadWeather(city + ", ru",
                "1bcaf7596191dbaeade6672a4744db9a", "metric")
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequest> call,
                                           @NonNull Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            if (listener != null) {
                                listener.onItemClicked(response.body());
                            }
                        } else {
                            if (response.code() == 404 && listener != null) {
                                listener.onCityNotFound();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        if (listener != null) {
                            listener.onError();
                        }
                    }
                });
    }

    public void connectByLocation(String lat, String lon) {
        OpenWeatherRepo.getInstance().getAPI().loadLocation(lat, lon,
                "1bcaf7596191dbaeade6672a4744db9a", "metric")
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequest> call,
                                           @NonNull Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            if (listener != null) {
                                listener.onItemClicked(response.body());
                            }
                        } else {
                            if (response.code() == 404 && listener != null) {
                                listener.onCityNotFound();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        if (listener != null) {
                            listener.onError();
                        }
                    }
                });
    }
}
