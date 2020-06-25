package com.example.a1l1;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.a1l1.models.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class ConnectClass {
    private final MainActivity mainActivity;

    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=1bcaf7596191dbaeade6672a4744db9a&units=metric";

    public ConnectClass(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void connectOnServer(String city) {
        try {
            final URL uri = new URL(String.format(WEATHER_URL, city));
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(in);
                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mainActivity.onItemClicked(weatherRequest);
                            }
                        });
                    } catch (Exception e) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                        Looper.prepare();
                        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                        builder.setTitle("Такой город не найден!")
                                .setMessage("Введите название города еще раз.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create().show();
                        Looper.loop();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                private String getLines(BufferedReader in) {
                    return in.lines().collect(Collectors.joining("\n"));
                }
            }).start();

        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
    }
}
