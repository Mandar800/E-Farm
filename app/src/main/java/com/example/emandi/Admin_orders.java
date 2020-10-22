package com.example.emandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin_orders extends AppCompatActivity {
    RecyclerView mRecyclerView;
    admin_order_adapter mAdapter;
    ArrayList<admin_order> models;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        models = new ArrayList<>();
        mRecyclerView = findViewById(R.id.adminorderrecy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new admin_order_adapter(this, models);
        mRecyclerView.setAdapter(mAdapter);
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();

        getData();


    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://biometric-hook.000webhostapp.com/AdminOrders.php";

        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray result = new JSONArray(response);

                    for (int i = 0; i < result.length(); i++) {

                        JSONArray order = result.getJSONArray(i);

                        admin_order o = new admin_order();
                        o.setUser_id(order.getString(0));
                        o.setOrder_id(order.getString(1));
                        o.setStatus(order.getString(2));
                        o.setTimestamp(order.getString(3));
                        o.setName(order.getString(4));
                        o.setNo(order.getString(5));
                        o.setAdd(order.getString(6));

                        StringBuilder content = new StringBuilder();
                        int total = 0;
                        JSONArray items = order.getJSONArray(7);
                        for (int j = 0; j < items.length(); j++) {
                            JSONArray item = items.getJSONArray(j);

                            content.append(item.getString(3));

                            String cost = item.getString(2);
                            int price = Integer.parseInt(cost.substring(0, cost.indexOf("R")).trim());
                            String unit = cost.substring(cost.indexOf("/") + 1).trim();

                            content.append(unit);
                            content.append(" ");
                            content.append(item.getString(1));
                            content.append(" = ");

                            int totalcost = price * Integer.parseInt(item.getString(3));

                            content.append(totalcost);
                            total += totalcost;
                            content.append("Rs");
                            content.append("\n");

                        }
                        o.setContent(content.toString());
                        o.setTotal(Integer.toString(total));
                        models.add(o);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                mAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                //Toast.makeText(getApplicationContext(),Integer.toString(models.size()),Toast.LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }) {
        };

        mQueue.add(sr);
    }

}