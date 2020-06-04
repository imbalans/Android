package com.example.a1l1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a1l1.CitiesHolder;
import com.example.a1l1.R;

import java.util.Objects;

public class CitiesFragment extends Fragment {
    Button btnMsk, btnPiter, btnSochi, btnKazan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setOnClickListenerBtnMoscow();
        setOnClickListenerBtnPiter();
        setOnClickListenerBtnSochi();
        setOnClickListenerBtnKazan();
    }

    private void initViews() {
        btnMsk = Objects.requireNonNull(getView()).findViewById(R.id.BtnMoscow);
        btnPiter = getView().findViewById(R.id.BtnStPetersburg);
        btnSochi = getView().findViewById(R.id.BtnSochi);
        btnKazan = getView().findViewById(R.id.BtnKazan);
    }

    private void setOnClickListenerBtnMoscow() {
        btnMsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof CitiesHolder) {
                    ((CitiesHolder) getActivity()).onCityClick("Moscow", "25");
                }
            }
        });
    }

    private void setOnClickListenerBtnPiter() {
        btnPiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof CitiesHolder) {
                    ((CitiesHolder) getActivity()).onCityClick("St.Petersburg", "20");
                }
            }
        });
    }

    private void setOnClickListenerBtnSochi() {
        btnSochi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof CitiesHolder) {
                    ((CitiesHolder) getActivity()).onCityClick("Sochi", "30");
                }
            }
        });
    }

    private void setOnClickListenerBtnKazan() {
        btnKazan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof CitiesHolder) {
                    ((CitiesHolder) getActivity()).onCityClick("Kazan", "10");
                }
            }
        });
    }
}
