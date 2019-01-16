package com.weatherapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.weatherapp.adapter.WeatherListAdapter;
import com.weatherapp.enums.ErrorType;
import com.weatherapp.pojo.Main;
import com.weatherapp.pojo.Weather;
import com.weatherapp.factory.WeatherDetailViewModelFactory;
import com.weatherapp.pojo.WeatherApiResponse;
import com.weatherapp.pojo.WeatherDetailResponse;
import com.weatherapp.pojo.WeatherList;
import com.weatherapp.viewmodels.WeatherDetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author vbhavesh
 * @purpose This class handling display weather data to user.
 */
public class MainActivity extends AppCompatActivity
{

    @BindView(R.id.pbLoader)
    ProgressBar pbLoader;

    @BindView(R.id.tvError)
    TextView tvError;

    @BindView(R.id.tvCity)
    TextView tvCity;

    @BindView(R.id.rvWeatherList)
    RecyclerView rvWeatherList;

    WeatherDetailViewModel weatherDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        weatherDetailViewModel = ViewModelProviders.of(this, new WeatherDetailViewModelFactory(getString(R.string.default_search_country), getString(R.string.app_id))).get(WeatherDetailViewModel.class);

        fetchWeatherDetails();
    }

    private void fetchWeatherDetails()
    {

        weatherDetailViewModel.getWeatherDetails().observe(this, weatherDetailResponse ->
        {
            hideLoader();
            if (weatherDetailResponse != null)
            {
                if (weatherDetailResponse.getErrorStatus() == ErrorType.NO_ERROR)
                {
                    setWeatherValues(weatherDetailResponse);

                } else
                {
                    tvError.setVisibility(View.VISIBLE);
                }

            }

        });
    }

    private void setWeatherValues(WeatherDetailResponse weatherData)
    {

        WeatherApiResponse weatherApiResponse = (WeatherApiResponse) weatherData.getData();
        tvCity.setText(weatherApiResponse.getCity().getName() + " , " + weatherApiResponse.getCity().getCountry());
        List<WeatherList> weatherList = weatherApiResponse.getList();
        WeatherListAdapter mAdapter = new WeatherListAdapter(weatherList, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvWeatherList.setLayoutManager(mLayoutManager);
        rvWeatherList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvWeatherList.setItemAnimator(new DefaultItemAnimator());
        rvWeatherList.setHasFixedSize(true);
        rvWeatherList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }

    private void hideLoader()
    {
        pbLoader.setVisibility(View.GONE);
    }

}
