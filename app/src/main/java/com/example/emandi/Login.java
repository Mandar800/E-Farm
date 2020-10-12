package com.example.emandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    TextInputEditText username, password, email, number;
    TextView or, hello, welcome, signintext;
    EditText location;
    Button signin, signup, locationButoon;
    TextView alert;
    TextInputLayout email_lay, num_lay,user_lay;
    LinearLayoutCompat form;
    String un, pass,mail,no,add;
    public RequestQueue mQueue;
    FusedLocationProviderClient locationProviderClient;
    LocationRequest locationRequest;
    public static final int REQUEST_CHECK_SETTING = 1001;
    public static final String SHARED_PREFS = "sharedPrefs";
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //Toast.makeText(this,sharedPreferences.getString("un",""),Toast.LENGTH_LONG).show();
        if(sharedPreferences.getString("un","")!=""){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        location = findViewById(R.id.locationtext);
        username = findViewById(R.id.Username);
        alert = findViewById(R.id.alert);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        number = findViewById(R.id.num);
        or = findViewById(R.id.or);
        hello = findViewById(R.id.hello);
        user_lay=findViewById(R.id.Username_layout);
        welcome = findViewById(R.id.welcome);
        signintext = findViewById(R.id.signtext);
        locationButoon = findViewById(R.id.locationButton);
        email_lay = findViewById(R.id.emaillayout);
        num_lay = findViewById(R.id.numberlayout);
        form = findViewById(R.id.form);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.register);
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();

            }
        });
        locationProviderClient = LocationServices.getFusedLocationProviderClient(Login.this);
        locationButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
                                switch (e.getStatusCode()){
                                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                        try{
                                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                            resolvableApiException.startResolutionForResult(Login.this,REQUEST_CHECK_SETTING);
                                        } catch(IntentSender.SendIntentException ex){

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
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });


    }

    @SuppressLint("MissingPermission")
    public void getLocation() {

        locationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location loc) {
                        // Got last known location. In some rare situations this can be null.
                        if (loc != null) {
                            Geocoder geocoder = new Geocoder(Login.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                                StringBuilder add = new StringBuilder();
                                add.append(addresses.get(0).getAddressLine(0));
                                //add.append(addresses.get(0).getSubLocality());
                                //add.append(addresses.get(0).getLocality());
                                //add.append(addresses.get(0).getCountryName());

                                location.setText(add.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void signup() {
        if(num_lay.getVisibility()==View.GONE){
            location.setVisibility(View.VISIBLE);
            locationButoon.setVisibility(View.VISIBLE);
            hello.setText("Hello, Welcome!");
            welcome.setVisibility(View.GONE);
            signintext.setText("Sign up to continue");
            email_lay.setVisibility(View.VISIBLE);
            num_lay.setVisibility(View.VISIBLE);
            user_lay.setVisibility(View.VISIBLE);
            form.removeView(or);
            form.removeView(signin);
            form.addView(or);
            form.addView(signin);
            alert.setVisibility(View.GONE);
        }else{
            un = username.getText().toString().trim();
            pass  = password.getText().toString().trim();
            mail= email.getText().toString().trim();
            no = number.getText().toString().trim();
            add = location.getText().toString().trim();
            if(alert.getVisibility()==View.VISIBLE){
                alert.setVisibility(View.GONE);
            }
            if(un.equals("") || pass.equals("") || mail.equals("") || no.equals("")){

                alert.setText("Please Fill All the Details");
                alert.setVisibility(View.VISIBLE);

            }else
            if(!isEmailValid(mail)){
                alert.setText("Invalid Email");
                alert.setVisibility(View.VISIBLE);

            }else
            if(no.length()>10 || no.length() <10){
                alert.setText("Invalid Mobile Number");
                alert.setVisibility(View.VISIBLE);

            }else
            if(add.length()==0){
                alert.setText("Please enter Address");
                alert.setVisibility(View.VISIBLE);
            }else {

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                String url = "http://biometric-hook.000webhostapp.com/register.php";

                StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonobject = new JSONObject(response);

                            String message = jsonobject.getString("message");


                            if (message.equals("success")) {
                                //Toast.makeText(getApplicationContext(), jsonarray.toString(), Toast.LENGTH_LONG).show();
                                editor.putString("un",un);
                                editor.putString("email",mail);
                                editor.putString("no",no);
                                editor.putString("add",add);
                                editor.apply();
                                //Toast.makeText(Login.this,sharedPreferences.getString("un",""),Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            } else if(message.equals("duplicate email")) {

                                alert.setVisibility(View.VISIBLE);
                                alert.setText("Email Already Exists");
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

                        progressDialog.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("un", un);
                        param.put("password", pass);
                        param.put("email", mail);
                        param.put("no", no);
                        param.put("add", add);

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
        }

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void signin() {
        mail = email.getText().toString().trim();
        pass  = password.getText().toString().trim();

        if(num_lay.getVisibility()==View.VISIBLE){
            user_lay.setVisibility(View.GONE);
            locationButoon.setVisibility(View.GONE);
            num_lay.setVisibility(View.GONE);
            form.removeView(or);
            form.removeView(signup);
            form.addView(or);
            form.addView(signup);
            alert.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
            hello.setText("Hello,");
            welcome.setText("Welcome Back!");
            welcome.setVisibility(View.VISIBLE);
            signintext.setText("Sign in to continue");

        }else{
            if(!isEmailValid(mail)){
                alert.setText("Invalid Email");
                alert.setVisibility(View.VISIBLE);
            }else
            if(mail.equals("") || pass.equals("")){

                alert.setText("Email or Password Cannot be Blank");
                alert.setVisibility(View.VISIBLE);
            }else
            if(alert.getVisibility()==View.VISIBLE){
                alert.setVisibility(View.GONE);

            }else {
                checklogin();
            }
        }


    }

    private void checklogin() {

        mail = email.getText().toString().trim();
        pass  = password.getText().toString().trim();



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://biometric-hook.000webhostapp.com/login.php";

        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);

                    String success = jsonobject.getString("success");
                    JSONArray jsonarray = jsonobject.getJSONArray("login");
                    JSONObject jo = jsonarray.getJSONObject(0);

                    if(success.equals("1")) {
                        //Toast.makeText(getApplicationContext(), jsonarray.toString(), Toast.LENGTH_LONG).show();
                        editor.putString("un",jo.getString("Name"));
                        editor.putString("email",jo.getString("email"));
                        editor.putString("id",jo.getString("id"));
                        editor.putString("no",jo.getString("no"));
                        editor.putString("add",jo.getString("add"));
                        editor.apply();
                        //Toast.makeText(Login.this,sharedPreferences.getString("un",""),Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                    else{

                        alert.setVisibility(View.VISIBLE);
                        alert.setText("Wrong Username or Password");
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
                param.put("email",mail);
                param.put("password",pass);

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
    int backButtonCount=0;
    @Override
    public void onBackPressed()
    {

        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}