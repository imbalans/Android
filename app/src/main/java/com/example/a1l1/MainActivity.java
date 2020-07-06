package com.example.a1l1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.example.a1l1.Receivers.BatteryReceiver;
import com.example.a1l1.Receivers.InternetReceiver;
import com.example.a1l1.database.HistoryDatabase;
import com.example.a1l1.fragments.WeatherFragment;
import com.example.a1l1.models.History;
import com.rest.entities.WeatherRequest;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements OnItemClick {
    private AppBarConfiguration mAppBarConfiguration;
    public static HistoryDatabase historyDatabase;
    private ConnectClass connectClass = new ConnectClass();
    SharedPreferences sharedPreferences;
    private BroadcastReceiver batteryReceiver = new BatteryReceiver();
    private BroadcastReceiver internetReceiver = new InternetReceiver();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        historyDatabase = Room.databaseBuilder(getApplicationContext(), HistoryDatabase.class, "infoDB")
                .allowMainThreadQueries().build();

        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
        registerReceiver(internetReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        initNotificationChannel();

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

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if(sharedPreferences.getString(WeatherFragment.cityKey, null) != null){
            openWeatherFragment(getData(WeatherFragment.cityKey),
                    getData(WeatherFragment.degreesKey),
                    getData(WeatherFragment.humidityKey),
                    getData(WeatherFragment.pressureKey),
                    getData(WeatherFragment.windSpeedKey));
        }
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("2", "name", importance);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private String getData(String key){
        return sharedPreferences.getString(key, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        connectClass.setListener(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectClass.setListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onItemClicked(WeatherRequest weatherRequest) {
        History history = new History();
        history.setCityName(weatherRequest.name);
        history.setDegreesValue(String.valueOf(weatherRequest.main.temp));
        historyDatabase.getHistoryDao().addData(history);

        openWeatherFragment(String.valueOf(weatherRequest.name),
                String.valueOf((int)weatherRequest.main.temp),
                String.valueOf((int)weatherRequest.main.pressure),
                String.valueOf((int)weatherRequest.main.humidity),
                String.valueOf((int)weatherRequest.wind.speed));
    }

    private void openWeatherFragment(String cityName, String degrees, String pressure, String humidity, String windSpeed) {
        Bundle bundle = new Bundle();
        bundle.putString(WeatherFragment.cityKey, cityName);
        bundle.putString(WeatherFragment.degreesKey, degrees);
        bundle.putString(WeatherFragment.pressureKey, pressure);
        bundle.putString(WeatherFragment.humidityKey, humidity);
        bundle.putString(WeatherFragment.windSpeedKey, windSpeed);

        Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_weather, bundle);
    }

    @Override
    public void connect(String cityName) {
        connectClass.connectOnServer(cityName);
    }

    @Override
    public void onCityNotFound() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Такой город не найден!")
                .setMessage("Введите название города еще раз.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                }).create().show();
    }

    @Override
    public void onError() {
        Toast.makeText(getBaseContext(), getString(R.string.network_error),Toast.LENGTH_SHORT).show();
    }
}
