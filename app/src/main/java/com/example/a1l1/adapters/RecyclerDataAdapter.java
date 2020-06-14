package com.example.a1l1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1l1.R;

import java.util.ArrayList;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.ViewHolder> {
    private ArrayList<CityWeatherData> data;
    static OnItemClickListener mItemClickListener;


    public RecyclerDataAdapter(ArrayList<CityWeatherData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setItemText(holder, data.get(position).city);
    }

    private void setItemText(@NonNull ViewHolder holder, String text) {
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(data.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CityWeatherData data);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        RecyclerDataAdapter.mItemClickListener = mItemClickListener;
    }

    public static class CityWeatherData {
        private final String city;

        public CityWeatherData(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }
    }
}
