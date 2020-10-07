package com.example.emandi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class cart_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_activity);
        Bundle extra =  getIntent().getBundleExtra("cart");
        ArrayList<Model> list = (ArrayList<Model>) extra.getSerializable("objects");
        TextView totalPrice = findViewById(R.id.totalprice);
        ListView listcart = findViewById(R.id.cartlist);
        int Price = 0;
        CartListAdapter adapter = new CartListAdapter(this,R.layout.cart_list,list);
        listcart.setAdapter(adapter);
        for(Model m : list){
            Price+=Integer.parseInt(m.getCost().substring(0,m.getCost().indexOf("R")).trim())*m.getQuant();
        }
        //Toast.makeText(this,Integer.toString(adapter.totalprice),Toast.LENGTH_LONG).show();
        totalPrice.setText(Integer.toString(Price)+" Rs");

    }

    @Override
    public void onBackPressed() {

    }
}