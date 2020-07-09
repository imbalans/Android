package com.example.a1l1.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1l1.OnItemClick;
import com.example.a1l1.R;
import com.example.a1l1.adapters.RecyclerDataAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.LOCATION_SERVICE;


public class CitiesFragment extends Fragment {
    RecyclerDataAdapter adapter;

    private ArrayList<String> listData
            = new ArrayList<>(Arrays.asList("Moscow", "Saint Petersburg", "Sochi", "Kazan"));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cities_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.listRecyclerView);
        adapter = new RecyclerDataAdapter(listData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        setHasOptionsMenu(true);

        adapter.SetOnItemClickListener(new RecyclerDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                ((OnItemClick) requireActivity()).connect(data);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        switch (item.getItemId()) {
            case R.id.add: {
                builder.setTitle(R.string.add_city);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.addCity(input.getText().toString());
                        Snackbar.make(requireView(), "Вы добавили город - " + input.getText().toString(), Snackbar.LENGTH_LONG).show();
                    }
                });
                builder.show();
                return true;
            }
            case R.id.remove: {
                adapter.remove();
                Snackbar.make(requireView(), "Последний город из списка удален!", Snackbar.LENGTH_LONG).show();
                return true;
            }
            case R.id.change_city: {
                builder.setTitle(R.string.change_city);
                builder.setPositiveButton("GO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        ((OnItemClick) requireActivity()).connect(input.getText().toString());
                        Snackbar.make(requireView(), "Вы ввели город - " + input.getText().toString(), Snackbar.LENGTH_LONG).show();
                    }
                });
                builder.show();
                return true;
            }
            case R.id.myLocation: {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                } else {
                    LocationManager mLocManager;
                    mLocManager = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);
                    Location loc;
                    loc = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    assert loc != null;
                    String lat = String.valueOf(loc.getLatitude());
                    String lon = String.valueOf(loc.getLongitude());
                    ((OnItemClick) requireActivity()).connectByLocation(lat, lon);
                }
            }
            default: {
                return false;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == 100) {
            boolean permissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    permissionsGranted = false;
                    break;
                }
            }
            if(permissionsGranted) onAttach(requireContext());
        }
    }
}