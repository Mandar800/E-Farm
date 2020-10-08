package com.example.emandi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cart_activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public RequestQueue mQueue;
    public int order_id;
    HashMap<Integer,Integer> cartlist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(Login.SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Bundle extra =  getIntent().getBundleExtra("cart");
        final ArrayList<Model> list = (ArrayList<Model>) extra.getSerializable("objects");
        if(list.isEmpty()){
            setContentView(R.layout.empty_cart);
            Button shop = findViewById(R.id.shopnow);
            shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }else{
            setContentView(R.layout.activity_cart_activity);
            TextView totalPrice = findViewById(R.id.totalprice);
            ListView listcart = findViewById(R.id.cartlist);
            int Price = 0;
            mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
            CartListAdapter adapter = new CartListAdapter(this,R.layout.cart_list,list);
            listcart.setAdapter(adapter);
            cartlist = new HashMap<>();
            for(Model m : list){
                Price+=Integer.parseInt(m.getCost().substring(0,m.getCost().indexOf("R")).trim())*m.getQuant();
                cartlist.put(m.getId(),m.getQuant());
            }
            //Toast.makeText(this,Integer.toString(adapter.totalprice),Toast.LENGTH_LONG).show();
            totalPrice.setText(Integer.toString(Price)+" Rs");
            String address = sharedPreferences.getString("add","");
            String Name = sharedPreferences.getString("un","");
            TextView cartName = findViewById(R.id.cartName);
            TextView add = findViewById(R.id.addresscart);
            cartName.setText(Name);
            add.setText(address);

            Button order = findViewById(R.id.order);
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Order();

                }
            });
        }

    }


    private void Order() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://biometric-hook.000webhostapp.com/getorderid.php";

        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);

                    String success = jsonobject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();


                        //Toast.makeText(Login.this,sharedPreferences.getString("un",""),Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Order Failed ", Toast.LENGTH_LONG).show();

                    }
                    progressDialog.dismiss();

                } catch (JSONException e) {

                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error Logging in check Internet Connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("user_id",sharedPreferences.getString("id",""));
                Gson gson = new Gson();
                String jsonhashmap = gson.toJson(cartlist);

                param.put("data",jsonhashmap);

                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        mQueue.add(sr);


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