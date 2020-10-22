package com.example.emandi;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class orderHolder extends RecyclerView.ViewHolder {
    TextView date,total,content,status;
    LinearLayout parent;
    public orderHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(content.getVisibility()==View.GONE){
                    content.setVisibility(View.VISIBLE);
                }else
                if(content.getVisibility()==View.VISIBLE){
                    content.setVisibility(View.GONE);
                }
            }
        });
        this.parent= itemView.findViewById(R.id.ordercardlayout);
        this.date = itemView.findViewById(R.id.date);
        this.total= itemView.findViewById(R.id.total);
        this.content =  itemView.findViewById(R.id.ordercontent);
        this.status = itemView.findViewById(R.id.status);
    }
}
