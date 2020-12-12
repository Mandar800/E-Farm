package com.example.emandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.internal.$Gson$Preconditions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.emandi.Login.REQUEST_CHECK_SETTING;

public class edit_profile extends AppCompatActivity {
    LocationRequest locationRequest;
    FusedLocationProviderClient locationProviderClient;
    EditText loca;

    SharedPreferences sharedPreferences;
    TextInputEditText username,email,pass, num;
    TextView alert;
    String un,mail,pas,no,add;
    public RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        username = findViewById(R.id.Username);
        email = findViewById(R.id.email);
        alert = findViewById(R.id.alert2);
        pass = findViewById(R.id.password);
        num = findViewById(R.id.num);
        loca = findViewById(R.id.locationtext);
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
        sharedPreferences = getSharedPreferences(Login.SHARED_PREFS,MODE_PRIVATE);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(edit_profile.this);
        Button getlocation = findViewById(R.id.locationButton);
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(edit_profile.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationRequest = LocationRequest.create();
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    locationRequest.setInterval(5000);
                    locationRequest.setFastestInterval(1000);


                    LocationCallback mLocationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            if (locationResult == null) {
                                return;
                            }
                            for (Location location : locationResult.getLocations()) {
                                if (location != null) {
                                    //TODO: UI updates.
                                }
                            }
                        }
                    };
                    LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(locationRequest, mLocationCallback, null);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());
                    result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                            try {
                                LocationSettingsResponse response = task.getResult(ApiException.class);
                            } catch (ApiException e) {
                                switch (e.getStatusCode()) {
                                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                        try {
                                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                            resolvableApiException.startResolutionForResult(edit_profile.this, REQUEST_CHECK_SETTING);
                                        } catch (IntentSender.SendIntentException ex) {

                                        }
                                        break;
                                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                        break;

                                }
                            }
                        }
                    });

                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(edit_profile.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        username.setText(sharedPreferences.getString("un",""));
        email.setText(sharedPreferences.getString("email",""));
        pass.setText(sharedPreferences.getString("pass",""));
        num.setText(sharedPreferences.getString("no",""));
        loca.setText(sharedPreferences.getString("add",""));

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.setVisibility(View.GONE);

                un = username.getText().toString().trim();
                mail = email.getText().toString().trim();
                pas = pass.getText().toString().trim();
                no = num.getText().toString().trim();
                add = loca.getText().toString().trim();

                if(un.equals("") || mail.equals("") || pas.equals("") || no.equals("") || add.equals("")){
                    alert.setText("Please Fill all Details!");
                    alert.setVisibility(View.VISIBLE);
                }else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    alert.setText("Please Enter Correct Email Address!");
                    alert.setVisibility(View.VISIBLE);
                }
                else if(no.length()!=10){
                    alert.setText("Please enter Correct Mobile Number");
                    alert.setVisibility(View.VISIBLE);
                }
                else{
                    saveData();
                }

            }
        });

    }

    private void saveData() {
        final String user_id = sharedPreferences.getString("id","");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "paste your link here";

        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    String success = jsonobject.getString("success");
                    if(success.equals("1")){
                        Toast.makeText(getApplicationContext(),"Data Changed",Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("un",un);
                        editor.putString("email",mail);
                        editor.putString("no",no);
                        editor.putString("pass",pas);
                        editor.putString("add",add);
                        editor.apply();
                        finish();


                    }else{
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "Error Updating, check Internet Connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("user_id", user_id);
                param.put("un",un);
                param.put("email",mail);
                param.put("password",pas);
                param.put("no",no);
                param.put("add",add);

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

    @SuppressLint("MissingPermission")
    public void getLocation() {

        locationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location loc) {
                        // Got last known location. In some rare situations this can be null.
                        if (loc != null) {
                            Geocoder geocoder = new Geocoder(edit_profile.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                                StringBuilder add = new StringBuilder();
                                add.append(addresses.get(0).getAddressLine(0));
                                //add.append(addresses.get(0).getSubLocality());
                                //add.append(addresses.get(0).getLocality());
                                //add.append(addresses.get(0).getCountryName());
                                loca.setText(add.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}