package com.example.emandi;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class admin_order_holder extends RecyclerView.ViewHolder {

    TextView name,no,add,status,total,orderhead,content,timestamp;
    Button done,delete;
    LinearLayout card ;
    public admin_order_holder(@NonNull View itemView) {
        super(itemView);

        this.card = itemView.findViewById(R.id.ordercard);
        this.name= itemView.findViewById(R.id.ordername);
        this.no = itemView.findViewById(R.id.ordernum);
        this.add = itemView.findViewById(R.id.orderadd);
        this.timestamp = itemView.findViewById(R.id.orderdate);
        this.orderhead = itemView.findViewById(R.id.orderhead);
        this.status = itemView.findViewById(R.id.orderstatus);
        this.total = itemView.findViewById(R.id.ordertottal);
        this.content = itemView.findViewById(R.id.ordercontent);
        this.done = itemView.findViewById(R.id.orderdone);
        this.delete = itemView.findViewById(R.id.orderdelete);

    }
}
