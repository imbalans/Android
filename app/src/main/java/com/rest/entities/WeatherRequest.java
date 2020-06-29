package com.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherRequest {
    @SerializedName("coord") public Coord coordinates;
    @SerializedName("weather") public Weather[] weather;
    @SerializedName("base") public String base;
    @SerializedName("main") public Main main;
    @SerializedName("visibility") public int visibility;
    @SerializedName("wind") public Wind wind;
    @SerializedName("clouds") public  Clouds clouds;
    @SerializedName("dt") public long dt;
    @SerializedName("sys") public  SysRest sys;
    @SerializedName("id") public  long id;
    @SerializedName("timezone") public int timezone;
    @SerializedName("name") public String name;
    @SerializedName("cod") public int cod;
}
