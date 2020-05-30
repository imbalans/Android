package com.example.a1l1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TemperatureActivity extends AppCompatActivity {
    private TextView cityView, degreesView, windView, pressureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature);
        initView();
        setOnClickListenerBtnReturn();
        showReceivedData();
        wiki();
    }

    private void initView() {
        cityView = findViewById(R.id.CityView);
        degreesView = findViewById(R.id.DegreesView);
        windView = findViewById(R.id.WindView);
        pressureView = findViewById(R.id.PressureView);
    }

    private void setOnClickListenerBtnReturn() {
        findViewById(R.id.BtnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showReceivedData() {
        Intent intent = getIntent();
        String city = intent.getStringExtra(CitiesActivity.textDataKey);
        String degrees = intent.getStringExtra(CitiesActivity.textDegreesKey);
        Boolean speed = intent.getBooleanExtra(CitiesActivity.checkBoxKeySpeed, false);
        Boolean pressure = intent.getBooleanExtra(CitiesActivity.checkBoxKeyPressure, false);
        cityView.setText(city);
        degreesView.setText(degrees);

        if (speed) {
            windView.setVisibility(View.VISIBLE);
        }

        if (pressure) {
            pressureView.setVisibility(View.VISIBLE);
        }
    }

    private void wiki() {
        findViewById(R.id.BtnWiki).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wiki = getIntent();
                String wikiCity = wiki.getStringExtra(CitiesActivity.textWikiKey);
                String uri1 = "https://ru.wikipedia.org/wiki/" + wikiCity;
                Uri uri = Uri.parse(uri1);
                Intent browser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browser);
            }
        });
    }
}