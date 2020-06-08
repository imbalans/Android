package com.example.a1l1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.a1l1.fragments.CitiesFragment;
import com.example.a1l1.fragments.WeatherFragment;


public class CitiesActivity extends AppCompatActivity implements onItemClick {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        if (findViewById(R.id.placeholder) != null) {
            if (savedInstanceState != null) {
                return;
            }
            CitiesFragment citiesFragment = new CitiesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.placeholder, citiesFragment).commit();
        }
    }

    @Override
    public void onItemClicked(String itemText, String degrees) {
        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.weather);
        if (weatherFragment != null) {
            weatherFragment.updateCity(itemText, degrees);
        } else {
            WeatherFragment fragment1 = new WeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString(WeatherFragment.cityKey, itemText);
            bundle.putString(WeatherFragment.degreesKey, degrees);
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
}
