package com.example.emandi;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class MyHolder extends RecyclerView.ViewHolder {
    ImageView mImgView;
    TextView mTitle,mCost,Qty;
    Button add,plus,minus;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        this.mImgView = itemView.findViewById(R.id.image);
        this.mTitle = itemView.findViewById(R.id.title);
        this.mCost = itemView.findViewById(R.id.cost);

        this.Qty =itemView.findViewById(R.id.quantity);
        int Quant = Integer.parseInt((String) Qty.getText());
        this.add = itemView.findViewById(R.id.addtocart);
        this.minus = itemView.findViewById(R.id.minus);
        this.plus = itemView.findViewById(R.id.plus);

        if(Quant>0){
            this.add.setVisibility(View.INVISIBLE);
            this.plus.setVisibility(View.VISIBLE);
            this.minus.setVisibility(View.VISIBLE);
            this.Qty.setVisibility(View.VISIBLE);
        }


    }
}
