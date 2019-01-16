package com.weatherapp.repository;

import com.weatherapp.WeatherApplication;
import com.weatherapp.pojo.WeatherApiResponse;
import com.weatherapp.pojo.WeatherList;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * @author vbhavesh
 * @purpose AppRemoteDataSource.java is Remote data source which is part of Android architecture components used to API using retrofit library.
 */

public class AppRemoteDataSource
{
    @Inject
    Retrofit retrofit;

    @Inject
    public AppRemoteDataSource()
    {
        WeatherApplication.getAppComponent().inject(this);
    }


    public Observable<WeatherApiResponse> getWeatherDetail(String city, String appId)
    {

        Observable<WeatherApiResponse> productDetailResponseObservable = retrofit.create(ApiService.class).getWeatherDetail(city, appId);
        return productDetailResponseObservable;
    }

}
