package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shop.R;
import com.example.pojo.ChatModel;
import com.example.pojo.Order;
import com.example.pojo.Product;
import com.example.ui.Customer;
import com.example.ui.DescriptionFragment;
import com.example.ui.OrderFragment;
import com.example.ui.ProductFragment;
import com.example.ui.Seller;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.product_ViewHolder> {

    Context context;
    ArrayList<Product>products;



    public Product_Adapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;

    }


    @NonNull
    @Override
    public product_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.pruduct_layout,parent,false);
        return new product_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull product_ViewHolder holder, int position)
    {
        Product product = products.get(position);
        // getImg
          getImage(holder.img_product,products.get(position));
       // holder.img_product.setImageURI(Uri.parse(products.get(position).getImg()));

        holder.tv_name.setText(product.getName());
       holder.img_description.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               //open Dialog Fragment to display description ;
               DescriptionFragment dialogue = DescriptionFragment
                       .newInstance(product.getDescription());
               FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
               dialogue.show(fm, null);

           }
       });
        holder.tv_price.setText(product.getPrice()+" "+product.getCoin());

        if (context.equals(new OrderFragment()) )
        {holder.img_addTo_basket.setVisibility(View.GONE);}else {
            holder.img_addTo_basket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("orders").child(product.getCode()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "add to basket", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "can't add to basket", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            });
        }
        }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class product_ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product,img_description, img_addTo_basket;
        TextView tv_name ,tv_price;


        public product_ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.imgProduct);
            img_description = itemView.findViewById(R.id.img_description);
            img_addTo_basket = itemView.findViewById(R.id.img_basket);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);


        }


    }
    public  void  getImage(ImageView img,Product product){

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("images/"+product.getCode());
        try {
            File localFile = File.createTempFile("tempFile",".jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    img.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(context.getApplicationContext(), "failed to get image ", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setOrder(Order order, Product product)
    {
        Order orders = new Order(order.getEmail(),product);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("orders").child(order.getEmail()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context.getApplicationContext(), "add to basket", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context.getApplicationContext(), "failed to add", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
