package com.weatherapp;


import com.weatherapp.pojo.WeatherApiResponse;
import com.weatherapp.repository.ApiService;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author vBhavesh
 */
public class MockFailedApiService implements ApiService
{
    private static final String TAG = "MockFailedApiService";
    private final BehaviorDelegate<ApiService> delegate;

    public MockFailedApiService(BehaviorDelegate<ApiService> restServiceBehaviorDelegate) {
        this.delegate = restServiceBehaviorDelegate;

    }
    @Override
    public Observable<WeatherApiResponse> getWeatherDetail(String search, String appID)
    {
        WeatherApiResponse weatherApiResponse = new WeatherApiResponse();
        weatherApiResponse.setCod("401");
        return delegate.returningResponse(weatherApiResponse).getWeatherDetail(search,appID);
    }
}
