package com.weatherapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.weatherapp.WeatherApplication;

import io.reactivex.Observable;


/**
 * @author vbhavesh
 * @purpose Utils.java is used for all required utility.
 *
 */

public class Utils
{
    /**
     * @author vbhavesh
     * @purpose device network connection check
     * @return (true or false)
     */

    public static boolean isDeviceNetworkConnected()
    {

        if (Utils.isDeviceConnectedToNetwork(WeatherApplication.getApplicationInstance()))
        {
            return true;
        }
        return false;
    }

    /**
     * @author vbhavesh
     * @purpose check device have network connection or not
     * @return (true or false)
     */

    public static boolean isDeviceConnectedToNetwork(Context context)
    {

        ConnectivityManager mConnectivityMgrdevice = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkinfoDevice = mConnectivityMgrdevice
                .getActiveNetworkInfo();
        if (mNetworkinfoDevice != null && mNetworkinfoDevice.isConnected())
            return true;

        return false;
    }

    /**
     * @author vbhavesh
     * @purpose check device have Internet connection or not by ping to Google.com
     * @return (true or false)
     */
    public static Observable<Boolean> isDeviceConnectedToInternet = Observable.fromCallable(() ->
    {
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    });

}
