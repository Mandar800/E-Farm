package com.example.emandi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class CartListAdapter extends ArrayAdapter<Model> {
    Context c;
    int resource;

    public CartListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Model> objects) {
        super(context, resource, objects);
        this.c=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String Name = getItem(position).getTitle();
        String Qty = Integer.toString(getItem(position).getQuant());
        String cost = getItem(position).getCost();
        int costint = Integer.parseInt(cost.substring(0,cost.indexOf("R")).trim());
        String Total = Integer.toString(costint*getItem(position).getQuant()).concat(" Rs");


        LayoutInflater inflater = LayoutInflater.from(c);
        convertView = inflater.inflate(resource,parent,false);

        TextView PName = (TextView) convertView.findViewById(R.id.Pname);
        TextView Pqty = (TextView) convertView.findViewById(R.id.Pqty);
        TextView Pprice = (TextView) convertView.findViewById(R.id.Pprice);
        TextView Ptotal = (TextView) convertView.findViewById(R.id.Ptotal);



        PName.setText(Name);
        Pqty.setText(Qty);
        Pprice.setText(cost);
        Ptotal.setText(Total);


        return convertView;
    }
}
