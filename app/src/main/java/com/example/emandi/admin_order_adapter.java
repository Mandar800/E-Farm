package com.example.emandi;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class admin_order_adapter extends RecyclerView.Adapter<admin_order_holder> {

    Context c;
    ArrayList<admin_order> list;
    RequestQueue mQueue;

    public admin_order_adapter(Context c, ArrayList<admin_order> list) {
        this.c = c;
        this.list = list;
        mQueue = VolleySingleton.getInstance(c).getmRequestqueue();
    }

    @NonNull
    @Override
    public admin_order_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_order_card,parent,false);

        return new admin_order_holder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final admin_order_holder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.no.setText(list.get(position).getNo());
        holder.add.setText(list.get(position).getAdd());
        holder.content.setText(list.get(position).getContent());
        holder.total.setText(list.get(position).getTotal());
        holder.timestamp.setText(list.get(position).getTimestamp());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.no.getVisibility()==View.GONE){

                    holder.no.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.VISIBLE);
                    holder.status.setVisibility(View.VISIBLE);
                    holder.content.setVisibility(View.VISIBLE);
                    holder.orderhead.setVisibility(View.VISIBLE);
                    if(list.get(position).getStatus().equals("0")){
                        holder.done.setVisibility(View.VISIBLE);
                    }else{
                        holder.done.setVisibility(View.GONE);
                    }

                    holder.delete.setVisibility(View.VISIBLE);

                }else{

                    holder.no.setVisibility(View.GONE);
                    holder.add.setVisibility(View.GONE);
                    holder.content.setVisibility(View.GONE);
                    holder.orderhead.setVisibility(View.GONE);
                    holder.done.setVisibility(View.GONE);
                    holder.delete.setVisibility(View.GONE);

                }

            }
        });
        if(list.get(position).getStatus().equals("0")){
            holder.card.setBackgroundResource(R.drawable.order_card_not_compleate);
            holder.status.setText("Pending");
            holder.status.setTextColor(ContextCompat.getColor(c,R.color.Pedding));

        }else{
            holder.status.setText("Delivered");
            holder.status.setTextColor(ContextCompat.getColor(c,R.color.newgreen));
            holder.card.setBackgroundResource(R.drawable.order_card_compleate);
            holder.done.setVisibility(View.GONE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(c);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                String url = "http://biometric-hook.000webhostapp.com/deleteorder.php";

                StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("success").equals("1")){
                                Toast.makeText(c,"Updated",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(c,"Failed",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,list.size());
                        notifyDataSetChanged();
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(c, "Error connecting check Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("order_id",list.get(position).getOrder_id());
                        return param;
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };
                mQueue.add(sr);
            }
        });

        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                final ProgressDialog progressDialog = new ProgressDialog(c);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                String url = "http://biometric-hook.000webhostapp.com/updateorder.php";

                StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("success").equals("1")){
                                Toast.makeText(c,"Updated",Toast.LENGTH_LONG).show();
                                holder.status.setText("Delivered");
                                holder.status.setTextColor(ContextCompat.getColor(c,R.color.colorPrimary));
                                holder.card.setBackgroundResource(R.drawable.order_card_compleate);
                                holder.done.setVisibility(View.GONE);
                                list.get(position).setStatus("1");
                                notifyDataSetChanged();

                            }else{
                                Toast.makeText(c,"Failed",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                        notifyDataSetChanged();
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("order_id",list.get(position).getOrder_id());
                        return param;
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };
                mQueue.add(sr);




            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
