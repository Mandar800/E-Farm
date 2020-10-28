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
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable {

    Context c;
    ArrayList<Model> models ;
    ArrayList<Model> list ;
    ArrayList<Model> cart = new ArrayList();

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
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.mTitle.setText(models.get(position).getTitle());
        holder.mCost.setText(models.get(position).getCost());

        holder.Qty.setText(Integer.toString(models.get(position).getQuant()));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.Qty.setText("1");
                holder.add.setVisibility(View.INVISIBLE);
                holder.plus.setVisibility(View.VISIBLE);
                holder.minus.setVisibility(View.VISIBLE);
                holder.Qty.setVisibility(View.VISIBLE);
                models.get(position).setQuant(1);
                cart.add(models.get(position));
                notifyDataSetChanged();

            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.parseInt((String) holder.Qty.getText());
                quant++;
                holder.Qty.setText(Integer.toString(quant));
                models.get(position).setQuant(quant);

                notifyDataSetChanged();
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.parseInt((String) holder.Qty.getText());
                quant--;
                if(quant<1){
                    holder.add.setVisibility(View.VISIBLE);
                    holder.plus.setVisibility(View.INVISIBLE);
                    holder.minus.setVisibility(View.INVISIBLE);
                    holder.Qty.setVisibility(View.INVISIBLE);
                    models.get(position).setQuant(0);
                    cart.remove(models.get(position));
                    notifyDataSetChanged();
                }else{
                    holder.Qty.setText(Integer.toString(quant));
                    models.get(position).setQuant(quant);
                    notifyDataSetChanged();
                }

            }
        });

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


    };
}
