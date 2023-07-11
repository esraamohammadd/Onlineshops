package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.online_shop.R;

public class MainActivity extends AppCompatActivity {
        TextView tv_sginUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        tv_sginUp = findViewById(R.id.tv_sginUp);

        tv_sginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  Intent intent = new Intent(MainActivity.this,sginUp.class);
                  startActivity(intent);

            }
        });
    }
}