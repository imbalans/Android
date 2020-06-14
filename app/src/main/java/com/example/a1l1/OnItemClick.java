package com.example.a1l1;

import com.example.a1l1.models.WeatherRequest;

public interface OnItemClick {
    void onItemClicked(WeatherRequest weatherRequest);
    void onHistoryClicked();
}
