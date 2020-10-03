package com.example.emandi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable {

    Context c;
    ArrayList<Model> models ;
    ArrayList<Model> list ;


    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
        this.list = new ArrayList<>(models);

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mTitle.setText(models.get(position).getTitle());
        holder.mCost.setText(models.get(position).getCost());
        holder.mImgView.setImageResource(models.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public Filter getFilter() {

        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            //list=new ArrayList<>(models);
            ArrayList<Model> filterlist = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filterlist.addAll(list);

            }else{
                for(Model item : list){
                    if(item.getTitle().toLowerCase().startsWith(charSequence.toString().toLowerCase())){
                        filterlist.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterlist;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            models.clear();
            models=(ArrayList<Model>) filterResults.values;
            notifyDataSetChanged();
        }

        public void Addtocart(){

        }
    };
}
