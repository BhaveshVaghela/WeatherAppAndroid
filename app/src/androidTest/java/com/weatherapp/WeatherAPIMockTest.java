package com.weatherapp;


import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.weatherapp.pojo.WeatherApiResponse;
import com.weatherapp.repository.ApiService;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import junit.framework.Assert;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class WeatherAPIMockTest extends InstrumentationTestCase
{
    private MockRetrofit mockRetrofit;

    private Retrofit retrofit;

    WeatherApiResponse weatherApiResponse;


    @Before
    public void setUp() throws Exception
    {
        retrofit = WeatherApplication.getRetrofitInstance("http://api.openweathermap.org/data/2.5/");
        NetworkBehavior behavior = NetworkBehavior.create();
        mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @SmallTest
    public void testWeatherList_SuccessCase()
    {
        BehaviorDelegate<ApiService> delegate = mockRetrofit.create(ApiService.class);
        ApiService apiService = new MockApiService(delegate);

        Observable<WeatherApiResponse> searchData = apiService.getWeatherDetail("Ahmedabad,India","8de8c02d65d78297eaedf49d535ae4f8");
        searchData.subscribe(response -> {
            weatherApiResponse = response;

        });
        //Actual Test
        assertNotNull(weatherApiResponse);
        Assert.assertEquals("200",weatherApiResponse.getCod());
        Assert.assertEquals(1,weatherApiResponse.getList().size());
        Assert.assertEquals(14, (int)weatherApiResponse.getList().get(0).getClouds().getAll());
        Assert.assertEquals("25.30",weatherApiResponse.getList().get(0).getDtTxt());
        Assert.assertEquals(25,(int)weatherApiResponse.getList().get(0).getDt());
        Assert.assertEquals(20.25,weatherApiResponse.getList().get(0).getWind().getDeg());
        Assert.assertEquals(25.25,weatherApiResponse.getList().get(0).getWind().getSpeed());

    }

    @SmallTest
    public void testWeatherList_ErrorCase()
    {
        BehaviorDelegate<ApiService> delegate = mockRetrofit.create(ApiService.class);
        ApiService apiService = new MockFailedApiService(delegate);
        Observable<WeatherApiResponse> searchData = apiService.getWeatherDetail("Ahmedabad,India","asdads");
        searchData.subscribe(response -> {
            weatherApiResponse = response;

        });
        //Actual Test
        assertNotNull(weatherApiResponse);
        Assert.assertEquals("401",weatherApiResponse.getCod());
    }


}