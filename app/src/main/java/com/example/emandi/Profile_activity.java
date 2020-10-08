package com.example.emandi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
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







    }
    public void onBackPressed() {
        finish();
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}