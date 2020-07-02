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
import androidx.room.Room;

import com.example.a1l1.database.HistoryDatabase;
import com.example.a1l1.fragments.WeatherFragment;
import com.example.a1l1.models.History;
import com.rest.entities.WeatherRequest;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements OnItemClick {
    private AppBarConfiguration mAppBarConfiguration;
    public static HistoryDatabase historyDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        historyDatabase = Room.databaseBuilder(getApplicationContext(), HistoryDatabase.class, "infoDB")
                .allowMainThreadQueries().build();

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
        History history = new History();
        history.setCityName(weatherRequest.name);
        history.setDegreesValue(String.valueOf(weatherRequest.main.temp));
        historyDatabase.getHistoryDao().addData(history);

        Bundle bundle = new Bundle();
        bundle.putString(WeatherFragment.cityKey, weatherRequest.name);
        bundle.putString(WeatherFragment.degreesKey, String.valueOf(weatherRequest.main.temp));
        bundle.putString(WeatherFragment.pressureKey, String.valueOf(weatherRequest.main.pressure));
        bundle.putString(WeatherFragment.humidityKey, String.valueOf(weatherRequest.main.humidity));
        bundle.putString(WeatherFragment.windSpeedKey, String.valueOf(weatherRequest.wind.speed));

        Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_weather, bundle);
    }

    @Override
    public void connect(String cityName) {
        ConnectClass connectClass = new ConnectClass(this);
        connectClass.connectOnServer(cityName);
    }
}
