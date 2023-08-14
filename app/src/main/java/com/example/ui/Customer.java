package com.example.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adapter.Product_Adapter;
import com.example.online_shop.R;
import com.example.pojo.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Customer extends AppCompatActivity {

    final static String ARG_EMAIL = "email";
    final static String ARG_MOBILE = "mobile";

    RecyclerView rec_products;
    ArrayList<Product> products = new ArrayList<>();
    Product_Adapter product_adapter;
    DatabaseReference reference;
    FloatingActionButton btn_chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        getSupportActionBar().hide();

        btn_chat = findViewById(R.id.fbtn_chat);
        rec_products = findViewById(R.id.rec_customer);

        Intent intent = getIntent();
        String email = intent.getStringExtra(MainActivity.EMAIL_ARG);
        String mobile = intent.getStringExtra(MainActivity.MOBILE_ARG);



        product_adapter = new Product_Adapter(Customer.this, products,"NO");


        reference = FirebaseDatabase.getInstance().getReference();

        getPruduct();


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Customer.this);

        rec_products.setLayoutManager(layoutManager);
        rec_products.setAdapter(product_adapter);


        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customer.this,Chat.class);
                intent.putExtra(ARG_EMAIL,email);
                intent.putExtra(ARG_MOBILE,mobile);
                startActivity(intent);
            }
        });






    }

    public  void  getPruduct(){
        reference.child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product product = snapshot.getValue(Product.class);
                products.add(product);
                product_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}