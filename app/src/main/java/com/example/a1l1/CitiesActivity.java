package com.example.a1l1;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a1l1.fragments.WeatherFragment;
import com.example.a1l1.models.WeatherRequest;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;


public class CitiesActivity extends AppCompatActivity implements OnItemClick {
    private AppBarConfiguration mAppBarConfiguration;
    public ArrayList<WeatherRequest> historyList = new ArrayList<>();

    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=1bcaf7596191dbaeade6672a4744db9a&units=metric";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.placeholder);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_about, R.id.nav_history)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onItemClicked(WeatherRequest weatherRequest) {
        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.weather);
        if (weatherFragment != null) {
            weatherFragment.updateCity(weatherRequest);
        } else {
            historyList.add(weatherRequest);
            Bundle bundle = new Bundle();
            bundle.putString(WeatherFragment.cityKey, weatherRequest.getName());
            bundle.putString(WeatherFragment.degreesKey, String.valueOf(weatherRequest.getMain().getTemp()));
            bundle.putString(WeatherFragment.pressureKey, String.valueOf(weatherRequest.getMain().getPressure()));
            bundle.putString(WeatherFragment.humidityKey, String.valueOf(weatherRequest.getMain().getHumidity()));
            bundle.putString(WeatherFragment.windSpeedKey, String.valueOf(weatherRequest.getWind().getSpeed()));

            Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_weather, bundle);
        }
    }

    @Override
    public void connect(String cityName) {
        try {
            final URL uri = new URL(String.format(WEATHER_URL, cityName));
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
                                onItemClicked(weatherRequest);
                            }
                        });
                    } catch (Exception e) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                        Looper.prepare();
                        AlertDialog.Builder builder = new AlertDialog.Builder(CitiesActivity.this);
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
