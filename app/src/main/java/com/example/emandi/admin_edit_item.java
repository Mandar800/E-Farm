package com.example.emandi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class admin_edit_item extends AppCompatActivity {
    ImageView img ,retake;
    Button save ;
    TextInputEditText title , rs, unit;
    TextView alert;
    String name , cost , imgstring;
    Bitmap bitmap;
    RequestQueue mQueue;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_item);

        img = findViewById(R.id.editimage);
        retake = findViewById(R.id.editpicicon);
        save = findViewById(R.id.saveitem);
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
        title = findViewById(R.id.edittitle);
        rs = findViewById(R.id.costRs);
        unit = findViewById(R.id.costUnit);
        alert = findViewById(R.id.alertitem);
        i = getIntent();
        DrawableCompat.setTint(
                DrawableCompat.wrap(retake.getDrawable()),
                ContextCompat.getColor(this, R.color.colorAccent)
        );
        if(i.hasExtra("ID")){
            title.setText(i.getStringExtra("title"));
            String cost = i.getStringExtra("cost");
            String Rs = cost.substring(0,cost.indexOf("R")).trim();
            String Unit = cost.substring(cost.indexOf("/")+1).trim();
            byte[] decodedString = Base64.decode(i.getStringExtra("img"), Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img.setImageBitmap(bitmap);
            rs.setText(Rs);
            unit.setText(Unit);
        }else{

            save.setText("Add Item");
        }
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 96);
                intent.putExtra("outputY", 96);
                startActivityForResult(intent,0);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.setVisibility(View.GONE);
                name  = title.getText().toString();
                cost = rs.getText().toString()+"Rs/ "+unit.getText().toString();

                if(name.isEmpty()){
                    alert.setText("Name cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                }else if(rs.getText().toString().isEmpty()){
                    alert.setText("Cost cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                }else if(unit.getText().toString().isEmpty()){
                    alert.setText("Unit cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                }else if(img.getDrawable() == null){
                    alert.setText("Thumbnail Cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                }else{
                    senditem();
                }
            }
        });

    }

    private void senditem() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        imgstring = android.util.Base64.encodeToString(bytes, Base64.DEFAULT);




        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://biometric-hook.000webhostapp.com/additem.php";

        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);

                    String success = jsonobject.getString("success");
                    Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG).show();
                    if(success.equals("1")) {
                        //Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        //Toast.makeText(getApplicationContext(), "item insert Failed ", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    //Toast.makeText(getApplicationContext(), "item Failed ", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error connecting in check Internet Connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                HashMap<String,String> param = new HashMap<String,String>();
                if(i.getStringExtra("ID")==null){
                    param.put("id","-1");
                }else{
                    param.put("id",i.getStringExtra("ID"));
                }
                param.put("title",name);
                param.put("cost",cost);
                param.put("image",imgstring);

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap)data.getExtras().get("data");
        img.setImageBitmap(bitmap);
    }

}