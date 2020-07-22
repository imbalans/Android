package com.example.a1l1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1l1.R;
import com.example.a1l1.models.History;

import java.util.List;

public class HistoryListDataAdapter extends RecyclerView.Adapter<HistoryListDataAdapter.ViewHolder> {
    List<History> histories;

    public HistoryListDataAdapter(List<History> histories) {
        this.histories = histories;
    }

    @NonNull
    @Override
    public HistoryListDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new HistoryListDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListDataAdapter.ViewHolder holder, int position) {
        History hs = histories.get(position);
        holder.textView.setText(String.format("%s - %s " + "\u2103", hs.getCityName(), hs.getDegreesValue()));
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemTextView);
        }
    }
}
