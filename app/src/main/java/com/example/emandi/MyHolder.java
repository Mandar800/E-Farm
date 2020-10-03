package com.example.emandi;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int Quant = Integer.parseInt((String) Qty.getText());
                Quant++;
                Qty.setText(Integer.toString(Quant));
                add.setVisibility(View.INVISIBLE);
                plus.setVisibility(View.VISIBLE);
                minus.setVisibility(View.VISIBLE);
                Qty.setVisibility(View.VISIBLE);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.parseInt((String) Qty.getText());
                quant++;
                Qty.setText(Integer.toString(quant));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.parseInt((String) Qty.getText());
                quant--;
                if(quant<1){
                    add.setVisibility(View.VISIBLE);
                    plus.setVisibility(View.INVISIBLE);
                    minus.setVisibility(View.INVISIBLE);
                    Qty.setVisibility(View.INVISIBLE);
                }
                Qty.setText(Integer.toString(quant));
            }
        });


    }
}
