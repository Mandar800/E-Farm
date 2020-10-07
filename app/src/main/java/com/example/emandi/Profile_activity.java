package com.example.emandi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Profile_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);
    }
    public void onBackPressed() {
        finish();
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}