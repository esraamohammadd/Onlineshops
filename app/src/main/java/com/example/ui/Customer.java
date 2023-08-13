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

    RecyclerView rec_products;
    ArrayList<Product> products = new ArrayList<>();
    Product_Adapter product_adapter;
    DatabaseReference reference;
    FloatingActionButton btn_chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Intent intent = getIntent();
        String email = intent.getStringExtra(MainActivity.EMAIL_ARG);
        String mobile = intent.getStringExtra(MainActivity.MOBILE_ARG);
        btn_chat = findViewById(R.id.fbtn_chat);
        getSupportActionBar().hide();


        rec_products = findViewById(R.id.rec_customer);
//adapter
        product_adapter = new Product_Adapter(Customer.this, products);


        reference = FirebaseDatabase.getInstance().getReference();

        getPruduct();

//        Product product1 = new Product();
//        Product product2 = new Product();
//        Product product3 = new Product();
//        Product product4 = new Product();
//        Product product5 = new Product(R.drawable._store,"mobile","65fer","50 ",
//                "description oppo phone A93 ytrgtdf camera hd socket hfyg try gmail","LE");
//
//
//        products.add(product5);
//        products.add(product2);
//        products.add(product3);
//        products.add(product4);

        //display products in recyclerView
        RecyclerView.LayoutManager lm = new LinearLayoutManager(Customer.this);

        rec_products.setLayoutManager(lm);
        rec_products.setAdapter(product_adapter);


        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customer.this,Chat.class);
                intent.putExtra("email",email);
                intent.putExtra("mobile",mobile);
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