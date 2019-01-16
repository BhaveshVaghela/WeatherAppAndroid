package com.weatherapp.repository;

import com.weatherapp.pojo.WeatherApiResponse;
import com.weatherapp.pojo.WeatherList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author vbhavesh
 * @purpose ApiService.java is part of retrofit service used to call api.
 */
public interface ApiService
{
    @GET("forecast")
    Observable<WeatherApiResponse> getWeatherDetail(@Query("q") String search, @Query("APPID") String appID);

}