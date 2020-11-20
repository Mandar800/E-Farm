package com.example.emandi;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class admin_item_holder extends RecyclerView.ViewHolder {
    ImageView mImgView;
    TextView mTitle,mCost;
    ImageView delete,edit;

    public admin_item_holder(@NonNull View itemView) {
        super(itemView);
        this.mImgView = itemView.findViewById(R.id.image);
        this.mTitle = itemView.findViewById(R.id.title);
        this.mCost = itemView.findViewById(R.id.cost);
        this.delete = itemView.findViewById(R.id.delete_item);
        this.edit = itemView.findViewById(R.id.edit_item);


    }
}
