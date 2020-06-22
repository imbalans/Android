package com.example.a1l1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1l1.R;
import com.example.a1l1.models.WeatherRequest;

import java.util.ArrayList;

public class HistoryListDataAdapter extends RecyclerView.Adapter<HistoryListDataAdapter.ViewHolder> {
    private ArrayList<WeatherRequest> data;

    public HistoryListDataAdapter(ArrayList<WeatherRequest> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public HistoryListDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new HistoryListDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListDataAdapter.ViewHolder holder, int position) {
        setItemText(holder, data.get(position));
    }

    private void setItemText(@NonNull ViewHolder holder, WeatherRequest request) {
        holder.textView.setText(String.format("%s - %s С", request.getName(), request.getMain().getTemp()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemTextView);
        }
    }
}
