package com.example.a1l1.fragments;

import android.content.DialogInterface;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1l1.R;
import com.example.a1l1.adapters.RecyclerDataAdapter;
import com.example.a1l1.OnItemClick;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;


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
            default: {
                return false;
            }
        }
    }
}
