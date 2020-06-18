package com.example.a1l1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
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
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;


public class CitiesActivity extends AppCompatActivity implements OnItemClick {
    private AppBarConfiguration mAppBarConfiguration;

    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=1bcaf7596191dbaeade6672a4744db9a&units=metric";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        // Проблема видимо где то в этом коде :(
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

        /*if (findViewById(R.id.placeholder) != null) {
            if (savedInstanceState != null) {
                return;
            }
            CitiesFragment citiesFragment = new CitiesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.placeholder, citiesFragment).commit();
        }*/
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
            WeatherFragment fragment1 = new WeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString(WeatherFragment.cityKey, weatherRequest.getName());
            bundle.putString(WeatherFragment.degreesKey, String.valueOf(weatherRequest.getMain().getTemp()));
            bundle.putString(WeatherFragment.pressureKey, String.valueOf(weatherRequest.getMain().getPressure()));
            bundle.putString(WeatherFragment.humidityKey, String.valueOf(weatherRequest.getMain().getHumidity()));
            bundle.putString(WeatherFragment.windSpeedKey, String.valueOf(weatherRequest.getWind().getSpeed()));
            fragment1.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder, fragment1);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void onHistoryClicked() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
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
