package com.weatherapp.repository;

import android.arch.lifecycle.MutableLiveData;


import com.weatherapp.pojo.WeatherApiResponse;
import com.weatherapp.util.Utils;
import com.weatherapp.pojo.WeatherDetailResponse;
import com.weatherapp.enums.ErrorType;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author vbhavesh
 * @purpose This class is used to provides the data from Remote source
 * API from Remote Data source
 */

public class WeatherRepository
{

    private AppRemoteDataSource appRemoteDataSource;

    MutableLiveData<WeatherDetailResponse> weatherDetailResponseMutableLiveData = new MutableLiveData<>();

    private String city, appId;

    @Inject
    public WeatherRepository(AppRemoteDataSource appRemoteDataSource)
    {
        this.appRemoteDataSource = appRemoteDataSource;
    }

    public MutableLiveData<WeatherDetailResponse> getWeatherDetails(String city, String appId)
    {

        this.city = city;
        this.appId = appId;

        if (Utils.isDeviceNetworkConnected())
        {
            Utils.isDeviceConnectedToInternet.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleInternetCheckResponse);
        } else
        {
            handleNetworkAvailabilityError();
        }
        return weatherDetailResponseMutableLiveData;
    }


    private void handleInternetAvailabilityError()
    {
        WeatherDetailResponse weatherDetailResponse = new WeatherDetailResponse();
        weatherDetailResponse.setErrorStatus(ErrorType.INTERNET_NOT_AVAILABLE);
        weatherDetailResponse.setData(null);

        weatherDetailResponseMutableLiveData.postValue(weatherDetailResponse);
    }

    private void handleNetworkAvailabilityError()
    {
        WeatherDetailResponse weatherDetailResponse = new WeatherDetailResponse();
        weatherDetailResponse.setErrorStatus(ErrorType.NETWORK_NOT_AVAILABLE);
        weatherDetailResponse.setData(null);
        weatherDetailResponseMutableLiveData.postValue(weatherDetailResponse);
    }

    private void handleInternetCheckResponse(Boolean isInternetAvailable)
    {
        if (isInternetAvailable)
        {
            Observable<WeatherApiResponse> weatherDetailApiResponseObserver = appRemoteDataSource.getWeatherDetail(city, appId);
            weatherDetailApiResponseObserver.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(weatherApiResponse -> weatherApiResponse).subscribe(this::handleWeatherSuccessResults, this::handleWeatherDetailError);

        } else
        {
            handleInternetAvailabilityError();
        }
    }

    private void handleWeatherSuccessResults(WeatherApiResponse weatherApiResponse)
    {

        if (weatherApiResponse.getCod().equalsIgnoreCase("200"))
        {
            WeatherDetailResponse weatherDetailResponse = new WeatherDetailResponse();
            weatherDetailResponse.setErrorStatus(ErrorType.NO_ERROR);
            weatherDetailResponse.setData(weatherApiResponse);
            weatherDetailResponseMutableLiveData.postValue(weatherDetailResponse);
        } else
        {
            handleAPIErrors();
        }
    }

    private void handleAPIErrors()
    {
        WeatherDetailResponse weatherDetailResponse = new WeatherDetailResponse();
        weatherDetailResponse.setErrorStatus(ErrorType.API_RESPOND_WITH_ERROR);
        weatherDetailResponse.setData(null);
        weatherDetailResponseMutableLiveData.postValue(weatherDetailResponse);

    }

    private void handleWeatherDetailError(Throwable t)
    {
        WeatherDetailResponse weatherDetailResponse = new WeatherDetailResponse();
        weatherDetailResponse.setErrorStatus(ErrorType.API_REQUEST_TIMEOUT_ERROR);
        weatherDetailResponse.setData(null);
        weatherDetailResponseMutableLiveData.postValue(weatherDetailResponse);

        t.printStackTrace();
    }

}
