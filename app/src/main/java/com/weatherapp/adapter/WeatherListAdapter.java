package com.weatherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weatherapp.R;
import com.weatherapp.pojo.Weather;
import com.weatherapp.pojo.WeatherList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @author vbhavesh
 * @purpose This class is used to display weather data.
 *
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDateTime)
        TextView tvDateTime;

        @BindView(R.id.tvTemp)
        TextView tvTemp;

        @BindView(R.id.tvDesc)
        TextView tvDesc;

        @BindView(R.id.tvPressure)
        TextView tvPressure;

        @BindView(R.id.tvHumidity)
        TextView tvHumidity;

        @BindView(R.id.tvWindSpeed)
        TextView tvWindSpeed;

        @BindView(R.id.tvWindDeg)
        TextView tvWindDeg;



        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private List<WeatherList> weatherList;
    Context context;
    public WeatherListAdapter(List<WeatherList> weatherList,Context context) {
        this.weatherList = weatherList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_weather_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WeatherList weather = weatherList.get(position);
        holder.tvDateTime.setText(weather.getDtTxt());
        holder.tvDesc.setText(weather.getWeather().get(0).getDescription());
        holder.tvHumidity.setText(context.getString(R.string.txt_humidity) + weather.getMain().getHumidity());
        holder.tvPressure.setText(context.getString(R.string.txt_pressure) + weather.getMain().getPressure() + " hPa");
        holder.tvTemp.setText(context.getString(R.string.txt_temperature) + Math.round((weather.getMain().getTemp() - 273.15)) + " ºC");
        holder.tvWindSpeed.setText(context.getString(R.string.txt_wind_speed) + weather.getWind().getSpeed() + " mps");
        holder.tvWindDeg.setText(context.getString(R.string.txt_wind_degree) +weather.getWind().getDeg() + "º");


    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}