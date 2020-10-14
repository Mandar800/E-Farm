package com.example.emandi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Profile_activity extends AppCompatActivity {
    SharedPreferences sharedPreferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);
        sharedPreferences = getSharedPreferences(Login.SHARED_PREFS,MODE_PRIVATE);
        TextView name = findViewById(R.id.cartName);
        TextView mail = findViewById(R.id.cartmail);
        TextView num = findViewById(R.id.cartmobile);
        TextView add = findViewById(R.id.addresscart);
        name.setText(sharedPreferences.getString("un",""));
        mail.setText(sharedPreferences.getString("email",""));
        num.setText(sharedPreferences.getString("no",""));
        add.setText(sharedPreferences.getString("add",""));

        CardView logout = findViewById(R.id.logout);
        CardView myorders = findViewById(R.id.myorders);
        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),my_orders.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences =getSharedPreferences(Login.SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finishAffinity();
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });

    }
    public void onBackPressed() {
        finish();
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}