package com.example.a1l1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class CitiesActivity extends AppCompatActivity {
    public final static String textDataKey = "textDataKey";
    public final static String textDegreesKey = "textDegreesKey";
    public final static String checkBoxKeySpeed = "checkBoxKeySpeed";
    public final static String checkBoxKeyPressure = "checkBoxKeyPressure";
    public final static String textWikiKey = "textWikiKey";
    private EditText enterCity;
    private CheckBox checkBoxSpeed, checkBoxPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities);
        initView();
        setOnClickListenerForStart();
        setOnCLickListenerForMoscow();
        setOnCLickListenerForStPetersburg();
        setOnCLickListenerForSochi();
        setOnCLickListenerForKazan();
    }

    private void initView() {
        enterCity = findViewById(R.id.enterCity);
        checkBoxSpeed = findViewById(R.id.checkboxSpeed);
        checkBoxPressure = findViewById(R.id.checkboxPressure);
    }


    private void setOnClickListenerForStart() {
        findViewById(R.id.BtnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = enterCity.getText().toString();
                String degrees = "5";
                Intent intent = new Intent(CitiesActivity.this, TemperatureActivity.class);
                intent.putExtra(textWikiKey, city);
                intent.putExtra(checkBoxKeySpeed, checkBoxSpeed.isChecked());
                intent.putExtra(checkBoxKeyPressure, checkBoxPressure.isChecked());
                intent.putExtra(textDataKey, city);
                intent.putExtra(textDegreesKey, degrees);
                startActivity(intent);
            }
        });
    }

    private void setOnCLickListenerForMoscow() {
        findViewById(R.id.BtnMoscow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moscow = "Moscow";
                String degrees = "20";
                Intent intent = new Intent(CitiesActivity.this, TemperatureActivity.class);
                intent.putExtra(textWikiKey, "Москва");
                intent.putExtra(checkBoxKeySpeed, checkBoxSpeed.isChecked());
                intent.putExtra(checkBoxKeyPressure, checkBoxPressure.isChecked());
                intent.putExtra(textDataKey, moscow);
                intent.putExtra(textDegreesKey, degrees);
                startActivity(intent);
            }
        });
    }

    private void setOnCLickListenerForStPetersburg() {
        findViewById(R.id.BtnStPetersburg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stPetersburg = "St. Petersburg";
                String degrees = "17";
                Intent intent = new Intent(CitiesActivity.this, TemperatureActivity.class);
                intent.putExtra(textWikiKey, "Санкт-Петербург");
                intent.putExtra(checkBoxKeySpeed, checkBoxSpeed.isChecked());
                intent.putExtra(checkBoxKeyPressure, checkBoxPressure.isChecked());
                intent.putExtra(textDataKey, stPetersburg);
                intent.putExtra(textDegreesKey, degrees);
                startActivity(intent);
            }
        });
    }

    private void setOnCLickListenerForSochi() {
        findViewById(R.id.BtnSochi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sochi = "Sochi";
                String degrees = "25";
                Intent intent = new Intent(CitiesActivity.this, TemperatureActivity.class);
                intent.putExtra(textWikiKey, "Сочи");
                intent.putExtra(checkBoxKeySpeed, checkBoxSpeed.isChecked());
                intent.putExtra(checkBoxKeyPressure, checkBoxPressure.isChecked());
                intent.putExtra(textDataKey, sochi);
                intent.putExtra(textDegreesKey, degrees);
                startActivity(intent);
            }
        });
    }

    private void setOnCLickListenerForKazan() {
        findViewById(R.id.BtnKazan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kazan = "Kazan";
                String degrees = "14";
                Intent intent = new Intent(CitiesActivity.this, TemperatureActivity.class);
                intent.putExtra(textWikiKey, "Казань");
                intent.putExtra(checkBoxKeySpeed, checkBoxSpeed.isChecked());
                intent.putExtra(checkBoxKeyPressure, checkBoxPressure.isChecked());
                intent.putExtra(textDataKey, kazan);
                intent.putExtra(textDegreesKey, degrees);
                startActivity(intent);
            }
        });
    }
}