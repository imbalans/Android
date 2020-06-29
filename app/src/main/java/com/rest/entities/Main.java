package com.rest.entities;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp") public float temp;
    @SerializedName("feels_like") public float feelsLike;
    @SerializedName("pressure") public float pressure;
    @SerializedName("humidity") public float humidity;
    @SerializedName("temp_min") public float tempMin;
    @SerializedName("temp_max") public float tempMax;
}
