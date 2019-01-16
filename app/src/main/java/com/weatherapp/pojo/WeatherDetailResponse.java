package com.weatherapp.pojo;

public class WeatherDetailResponse<T>
{
    T data;
    T errorStatus;

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public T getErrorStatus()
    {
        return errorStatus;
    }

    public void setErrorStatus(T error)
    {
        this.errorStatus = error;
    }

}
