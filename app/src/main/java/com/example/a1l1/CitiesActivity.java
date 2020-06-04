package com.example.a1l1;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.a1l1.fragments.CitiesFragment;
import com.example.a1l1.fragments.WeatherFragment;


public class CitiesActivity extends AppCompatActivity implements CitiesHolder {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        if (findViewById(R.id.fragmentCont) != null) {
            if (savedInstanceState != null) {
                return;
            }
            CitiesFragment citiesFragment = new CitiesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentCont, citiesFragment).commit();
        }
    }

    @Override
    public void onCityClick(String city, String degrees) {
        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.weather);
        if (weatherFragment != null) {
            weatherFragment.updateCity(city, degrees);
        } else {
            WeatherFragment fragment1 = new WeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString(WeatherFragment.cityKey, city);
            bundle.putString(WeatherFragment.degreesKey, degrees);
            fragment1.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentCont, fragment1);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}
