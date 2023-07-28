package com.example.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adapter.Product_Adapter;
import com.example.online_shop.R;
import com.example.pojo.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

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








    }

    public  void  getPruduct(){
        reference.child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Product product = snap.getValue(Product.class);
                    products.add(product);
                    product_adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}