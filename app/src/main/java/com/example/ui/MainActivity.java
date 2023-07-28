package com.example.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView tv_sginUp;
    EditText et_email, et_password;
    Button btn_sginIn ;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();



        et_email = findViewById(R.id.et_sginemail);
        et_password = findViewById(R.id.et_sginpassword);
        btn_sginIn = findViewById(R.id.bt_sginIn);
        tv_sginUp = findViewById(R.id.tv_sginUp);

        mAuth = FirebaseAuth.getInstance();

        tv_sginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  Intent intent = new Intent(MainActivity.this,sginUp.class);
                  startActivity(intent);

            }
        });

        btn_sginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                if (email.equals("admin@admin.com")&& password.equals("admin@123")){
                    Intent intent = new Intent(MainActivity.this, Seller.class);
                    startActivity(intent);
                }else {
                    sginInUser(email, password);
                }




            }
        });




    }

    private void sginInUser(String email, String password)
    {


      mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {

              if (task.isSuccessful())
              {
                  Intent intent = new Intent(MainActivity.this, Customer.class);
                  startActivity(intent);
              }else{
                  Toast.makeText(MainActivity.this, "email or password is incorrect", Toast.LENGTH_SHORT).show();
              }
          }
      });



    }

//    private void switchUser(String adminEmail)
//    {
//        if (et_email.getText().toString().equals(adminEmail))
//        {
//            Intent intent = new Intent(MainActivity.this, Seller.class);
//            startActivity(intent);
//        }else
//        {
//            Intent intent = new Intent(MainActivity.this, Customer.class);
//            startActivity(intent);
//        }
//
//    }
}