package com.weatherapp;

import android.app.Application;

import com.weatherapp.dragger.component.AppComponent;
import com.weatherapp.dragger.component.DaggerAppComponent;
import com.weatherapp.dragger.module.AppModule;
import com.weatherapp.dragger.module.DataModule;


/**
 * @author vbhavesh
 * @purpose This class is entry point whenever application is launch.
 *
 */

public class WeatherApplication extends Application
{
    private static AppComponent appComponent;
    private static WeatherApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule("http://api.openweathermap.org/data/2.5/"))
                .build();


    }

    public static WeatherApplication getApplicationInstance()
    {
        return instance;
    }


    public static AppComponent getAppComponent()
    {
        return appComponent;
    }
}
