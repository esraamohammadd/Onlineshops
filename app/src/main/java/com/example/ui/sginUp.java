package com.example.ui;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.online_shop.R;

public class sginUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgin_up);

        getSupportActionBar().hide();
    }
}