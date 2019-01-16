package com.weatherapp.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.weatherapp.viewmodels.WeatherDetailViewModel;

/**
 * @author vbhavesh
 * @purpose This class is used to provide relevant parameters for view model.
 */
public class WeatherDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private String city, appId;

    public WeatherDetailViewModelFactory(String city, String appId)
    {
        this.city = city;
        this.appId = appId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass)
    {
        return (T) new WeatherDetailViewModel(city, appId);
    }
}