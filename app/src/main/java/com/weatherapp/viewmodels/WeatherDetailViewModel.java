package com.weatherapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.weatherapp.pojo.WeatherDetailResponse;
import com.weatherapp.WeatherApplication;
import com.weatherapp.repository.WeatherRepository;

import javax.inject.Inject;

/**
 * @author vbhavesh
 * @purpose This class is view model which is part of Android architecture components used to get weather data from Repository
 */
public class WeatherDetailViewModel extends ViewModel
{
    @Inject
    WeatherRepository weatherRepository;

    private LiveData<WeatherDetailResponse> productDetailsMutableLiveData;
    private String city, appId;
    public WeatherDetailViewModel(String city, String appId){
        WeatherApplication.getAppComponent().inject(this);
        this.city = city;
        this.appId = appId;
    }

    public LiveData<WeatherDetailResponse> getWeatherDetails() {
        productDetailsMutableLiveData = weatherRepository.getWeatherDetails(city,appId);
        return productDetailsMutableLiveData;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
