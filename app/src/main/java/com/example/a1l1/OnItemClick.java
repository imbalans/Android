package com.example.a1l1;

import com.rest.entities.WeatherRequest;

public interface OnItemClick {
    void onItemClicked(WeatherRequest weatherRequest);
    void connect(String cityName);
    void onCityNotFound();
    void onError();
}
