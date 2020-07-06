package com.example.a1l1.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1l1.MainActivity;
import com.example.a1l1.R;
import com.example.a1l1.adapters.HistoryListDataAdapter;
import com.example.a1l1.models.History;

import java.util.ArrayList;
import java.util.List;


public class HistoryListFragment extends Fragment {
    RecyclerView recyclerView;
    Button btnClear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);
        recyclerView = view.findViewById(R.id.history_list_data);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        getData();

        btnClear = view.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.historyDatabase.getHistoryDao().delete();
                recyclerView.setAdapter(new HistoryListDataAdapter(new ArrayList<>()));
            }
        });


        return view;
    }

    private void getData(){
        @SuppressLint("StaticFieldLeak")
        class GetData extends AsyncTask<Void, Void, List<History>>{

            @Override
            protected List<History> doInBackground(Void... voids) {
                List<History> histories = MainActivity.historyDatabase.getHistoryDao().getMyData();
                return histories;
            }

            @Override
            protected void onPostExecute(List<History> histories) {
                HistoryListDataAdapter historyListDataAdapter = new HistoryListDataAdapter(histories);
                recyclerView.setAdapter(historyListDataAdapter);
                super.onPostExecute(histories);
            }
        }

        GetData gd = new GetData();
        gd.execute();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
