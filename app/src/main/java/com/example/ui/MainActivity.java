package com.example.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    final static String EMAIL_ARG = "email";
    final static String MOBILE_ARG = "mobile";
    TextView tv_sginUp;
    EditText et_email, et_password, et_mobile;
    Button btn_sginIn ;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();



        et_email = findViewById(R.id.et_sginemail);
        et_password = findViewById(R.id.et_sginpassword);
        btn_sginIn = findViewById(R.id.bt_sginIn);
        tv_sginUp = findViewById(R.id.tv_sginUp);
        et_mobile = findViewById(R.id.et_sginMobile);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-shop-b919f-default-rtdb.firebaseio.com/");

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
                if (email.equals("admin@admin.com")&& password.equals("123456")){


                    Intent intent = new Intent(MainActivity.this, Seller.class);
                    intent.putExtra(EMAIL_ARG, et_email.getText().toString());
                    intent.putExtra(MOBILE_ARG,et_mobile.getText().toString());
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
                  intent.putExtra(EMAIL_ARG, et_email.getText().toString());
                  intent.putExtra(MOBILE_ARG, et_mobile.getText().toString());
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


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (FirebaseAuth.getInstance().getCurrentUser()!= null)
//        {
//            startActivity(new Intent( MainActivity.this,Customer.class));
//            finish();
//        }
//    }
}