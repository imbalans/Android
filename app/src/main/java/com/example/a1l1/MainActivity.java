package com.example.a1l1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView weatherForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setOnClickListener();
    }

    private void initView(){
        button = findViewById(R.id.button3);
        weatherForecast = findViewById(R.id.textView2);
    }

    private void setOnClickListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherForecast.setText(R.string.to_be_continued);
            }
        });
    }
}
