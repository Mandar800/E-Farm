package com.example.emandi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class orderAdapter extends RecyclerView.Adapter<orderHolder> {
    @NonNull
    Context c;
    ArrayList<order> models;

    public orderAdapter(@NonNull Context c, ArrayList<order> models) {
        this.c = c;
        this.models = models;
    }

    @Override
    public orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card,parent,false);
        return new orderHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull orderHolder holder, int position) {
        holder.date.setText(models.get(position).getDate());
        holder.total.setText(models.get(position).getTotal());
        holder.content.setText(models.get(position).getContent());
        if(models.get(position).getStatus().equals("1")){
            holder.status.setTextColor(R.color.colorPrimary);
            holder.status.setText("Delivered");
            holder.status.setTextColor(ContextCompat.getColor(c,R.color.colorPrimary));
            holder.parent.setBackgroundResource( R.drawable.order_card_compleate);
        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
