package com.example.emandi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class admin_item_adapter extends RecyclerView.Adapter<admin_item_holder> {
    Context c;
    ArrayList<Model> models ;
    RequestQueue mQueue;
    public admin_item_adapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
        mQueue = VolleySingleton.getInstance(c).getmRequestqueue();
    }

    @NonNull
    @Override
    public admin_item_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_item,parent,false);
        return new admin_item_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_item_holder holder, final int position) {
        holder.mTitle.setText(models.get(position).getTitle());
        holder.mCost.setText(models.get(position).getCost());
        byte[] decodedString = Base64.decode(models.get(position).getImg(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.mImgView.setImageBitmap(decodedByte);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c,admin_edit_item.class);

                i.putExtra("ID",Integer.toString(models.get(position).getId()));
                i.putExtra("title",models.get(position).getTitle());
                i.putExtra("cost",models.get(position).getCost());
                i.putExtra("img",models.get(position).getImg());
                c.startActivity(i);
                admin_Listed_items.FLAG=1;
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(c);
                progressDialog.setMessage("Loading...");
                progressDialog.show();


                String url = "http://biometric-hook.000webhostapp.com/deleteitem.php";

                StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonobject = new JSONObject(response);

                            String success = jsonobject.getString("success");
                            Toast.makeText(c, success, Toast.LENGTH_LONG).show();
                            if(success.equals("1")) {
                                //Toast.makeText(c, "success", Toast.LENGTH_LONG).show();

                            }
                            else{
                                //Toast.makeText(c, "delete Failed ", Toast.LENGTH_LONG).show();

                            }
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            //Toast.makeText(c, "json Failed ", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }
                        models.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,models.size());
                        notifyDataSetChanged();
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(c, "Error connecting check Internet Connection", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }){
                    @Override
                    protected Map<String,String> getParams(){
                        HashMap<String,String> param = new HashMap<String,String>();
                            param.put("id",Integer.toString(models.get(position).getId()));

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
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
