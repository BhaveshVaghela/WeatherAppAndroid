package com.weatherapp;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weatherapp.dragger.component.AppComponent;
import com.weatherapp.dragger.component.DaggerAppComponent;
import com.weatherapp.dragger.module.AppModule;
import com.weatherapp.dragger.module.DataModule;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author vbhavesh
 * @purpose This class is entry point whenever application is launch.
 *
 */

public class WeatherApplication extends Application
{
    private static AppComponent appComponent;
    private static WeatherApplication instance;
    static OkHttpClient.Builder okHttpClient = null;
    static GsonBuilder gsonBuilder = null;
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
    public static Retrofit getRetrofitInstance(String baseUrl)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(provideOkhttpClient())
                .build();
        return retrofit;
    }



    static OkHttpClient provideOkhttpClient()
    {
        if (okHttpClient == null)
        {
            okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(logging);
        }
        //client.cache(cache);
        return okHttpClient.build();
    }

    static Gson provideGson()
    {
        if (gsonBuilder == null)
        {
            gsonBuilder = new GsonBuilder();
            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        }
        return gsonBuilder.create();
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
