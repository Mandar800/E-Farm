package com.example.emandi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class admin_item_adapter extends RecyclerView.Adapter<admin_item_holder> {
    Context c;
    ArrayList<Model> models ;

    public admin_item_adapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public admin_item_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_item,null);
        return new admin_item_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_item_holder holder, int position) {
        holder.mTitle.setText(models.get(position).getTitle());
        holder.mCost.setText(models.get(position).getCost());
        holder.mImgView.setImageResource(models.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
