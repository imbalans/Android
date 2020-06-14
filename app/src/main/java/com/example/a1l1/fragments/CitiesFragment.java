package com.example.a1l1.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1l1.OnItemClick;
import com.example.a1l1.R;
import com.example.a1l1.adapters.RecyclerDataAdapter;
import com.example.a1l1.adapters.RecyclerDataAdapter.CityWeatherData;
import com.example.a1l1.models.WeatherRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class CitiesFragment extends Fragment {
    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=1bcaf7596191dbaeade6672a4744db9a&units=metric";

    private ArrayList<CityWeatherData> listData
            = new ArrayList<>(Arrays.asList(new CityWeatherData("Moscow"),
            new CityWeatherData("Saint Petersburg"),
            new CityWeatherData("Kazan"),
            new CityWeatherData("Sochi")));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cities_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.listRecyclerView);
        RecyclerDataAdapter adapter = new RecyclerDataAdapter(listData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        adapter.SetOnItemClickListener(new RecyclerDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final CityWeatherData data) {
                try {
                    final URL uri = new URL(String.format(WEATHER_URL, data.getCity()));
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
                                        ((OnItemClick) Objects.requireNonNull(getActivity())).onItemClicked(weatherRequest);
                                        Snackbar.make(view, "Вы выбрали город - " + data.getCity(), Snackbar.LENGTH_LONG).show();
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
                    }).start();
                } catch (MalformedURLException e) {
                    Log.e(TAG, "Fail URI", e);
                    e.printStackTrace();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            private String getLines(BufferedReader in) {
                return in.lines().collect(Collectors.joining("\n"));
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
