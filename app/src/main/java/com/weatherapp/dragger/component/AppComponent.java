package com.weatherapp.dragger.component;


import com.weatherapp.dragger.module.DataModule;
import com.weatherapp.repository.AppRemoteDataSource;
import com.weatherapp.viewmodels.WeatherDetailViewModel;
import com.weatherapp.dragger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author vbhavesh
 * @purpose This class is mainly used for dependency injection via Dragger.
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {

    void inject(WeatherDetailViewModel weatherDetailViewModel);
    void inject(AppRemoteDataSource appRemoteDataSource);

}