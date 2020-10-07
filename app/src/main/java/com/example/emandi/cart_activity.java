package com.example.emandi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class cart_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_activity);
        SharedPreferences sharedPreferences = getSharedPreferences(Login.SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = editor = sharedPreferences.edit();
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

        String address = sharedPreferences.getString("add","");
        TextView add = findViewById(R.id.addresscart);
        add.setText(address);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void Checkout(View view) {

    }
}