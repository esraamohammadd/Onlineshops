package com.example.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.online_shop.R;
import com.example.online_shop.databinding.ActivitySginUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sginUp extends AppCompatActivity {


      Button btn_sginUp;
      EditText et_email, et_password,et_confirm_pass;
      FirebaseAuth mAthu;
    ActivitySginUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding = ActivitySginUpBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_sgin_up);

        getSupportActionBar().hide();
        btn_sginUp = findViewById(R.id.bt_sginUp);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_sginUpemail);
        mAthu = FirebaseAuth.getInstance();


//        et_email = binding.etSginUpemail;
//        et_password = binding.etPassword;
//        et_confirm_pass = binding.etConfirmPass;
//        btn_sginUp = binding.btSginUp;

btn_sginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = et_email.getText().toString();
               String password = et_password.getText().toString();
                sginUpUser(email,password);


            }
        });




    }

    private void sginUpUser ( String email ,  String password )
    {
        mAthu.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                if (task.isComplete())
                {
                    Toast.makeText(getApplicationContext(), "user created", Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getApplicationContext(), "an error occurred", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}