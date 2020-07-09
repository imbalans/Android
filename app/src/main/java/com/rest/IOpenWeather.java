package com.rest;

import com.rest.entities.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String city,
                                     @Query("appid") String keyApi,
                                     @Query("units") String units);

    @GET("data/2.5/weather")
    Call<WeatherRequest> loadLocation(@Query("lat") String lat,
                                      @Query("lon") String lon,
                                      @Query("appid") String keyApi,
                                      @Query("units") String units);
}
