package com.weatherapp;


import com.weatherapp.pojo.Clouds;
import com.weatherapp.pojo.WeatherApiResponse;
import com.weatherapp.pojo.WeatherList;
import com.weatherapp.pojo.Wind;
import com.weatherapp.repository.ApiService;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author vBhavesh
 */
public class MockApiService implements ApiService
{

    private final BehaviorDelegate<ApiService> delegate;

    public MockApiService(BehaviorDelegate<ApiService> service) {
        this.delegate = service;
    }

    @Override
    public Observable<WeatherApiResponse> getWeatherDetail(String search, String appID)
    {
        WeatherApiResponse weatherApiResponse = new WeatherApiResponse();
        java.util.List<WeatherList> weatherArrayList = new ArrayList<>();
        WeatherList weatherList = new WeatherList();
        Wind wind = new Wind();
        wind.setDeg(20.25);
        wind.setSpeed(25.25);
        weatherList.setWind(wind);
        Clouds clouds = new Clouds();
        clouds.setAll(14);
        weatherList.setClouds(clouds);
        weatherList.setDt(25);
        weatherList.setDtTxt("25.30");
        weatherArrayList.add(weatherList);
        weatherApiResponse.setList(weatherArrayList);
        weatherApiResponse.setCod("200");
        return delegate.returningResponse(weatherApiResponse).getWeatherDetail(search,appID);
    }
}
