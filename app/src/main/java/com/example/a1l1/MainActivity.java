package com.example.a1l1;

import android.os.Bundle;

import androidx.annotation.Nullable;
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

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnItemClick {
    private AppBarConfiguration mAppBarConfiguration;
    public ArrayList<WeatherRequest> historyList = new ArrayList<>();

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
        historyList.add(weatherRequest);
        Bundle bundle = new Bundle();
        bundle.putString(WeatherFragment.cityKey, weatherRequest.getName());
        bundle.putString(WeatherFragment.degreesKey, String.valueOf(weatherRequest.getMain().getTemp()));
        bundle.putString(WeatherFragment.pressureKey, String.valueOf(weatherRequest.getMain().getPressure()));
        bundle.putString(WeatherFragment.humidityKey, String.valueOf(weatherRequest.getMain().getHumidity()));
        bundle.putString(WeatherFragment.windSpeedKey, String.valueOf(weatherRequest.getWind().getSpeed()));

        Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_weather, bundle);
    }

    @Override
    public void connect(String cityName) {
        ConnectClass connectClass = new ConnectClass(this);
        connectClass.connectOnServer(cityName);
    }
}
