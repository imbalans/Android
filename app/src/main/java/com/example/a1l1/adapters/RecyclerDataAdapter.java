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
    private ArrayList<String> data;
    static OnItemClickListener mItemClickListener;


    public RecyclerDataAdapter(ArrayList<String> data) {
        this.data = data;
    }

    public void addCity(String newCity) {
        data.add(newCity);
        notifyItemInserted(data.size() - 1);
    }

    public void remove() {
        data.remove(data.size() - 1);
        notifyItemRemoved(data.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setItemText(holder, data.get(position));
    }

    private void setItemText(@NonNull ViewHolder holder, String text) {
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return data.size();
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
        void onItemClick(String data);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        RecyclerDataAdapter.mItemClickListener = mItemClickListener;
    }
}
