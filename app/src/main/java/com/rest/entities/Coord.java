package com.rest.entities;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("lon") public float lon;
    @SerializedName("lat") public float lat;
}
