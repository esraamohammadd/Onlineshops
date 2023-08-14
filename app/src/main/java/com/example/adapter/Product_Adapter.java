package com.example.adapter;

import android.content.Context;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shop.R;
import com.example.pojo.Product;
import com.example.ui.DescriptionFragment;
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
    String isAdmin;

    DatabaseReference databaseReference;


    public Product_Adapter(Context context, ArrayList<Product> products,String isAdmin) {
        this.context = context;
        this.products = products;
        this.isAdmin = isAdmin;



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

        databaseReference = FirebaseDatabase.getInstance().getReference();
          getImage(holder.img_product,products.get(position));

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


        if (isAdmin.equals("yes|product")) {
            holder.img_addTo_basket.setVisibility(View.GONE);
            holder.img_remove.setVisibility(View.VISIBLE);
            holder.img_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.child("products").child(product.getCode()).removeValue();
                }
            });

        }else if (isAdmin.equals("yes|orders"))
        {
            holder.img_addTo_basket.setVisibility(View.GONE);
            holder.img_remove.setVisibility(View.GONE);
        }else
        {
            holder.img_addTo_basket.setVisibility(View.VISIBLE);
            holder.img_remove.setVisibility(View.GONE);
            holder.img_addTo_basket.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {



                    {



                        databaseReference.child("orders").child(product.getCode()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "add to basket", Toast.LENGTH_SHORT).show();
                                holder.img_remove.setVisibility(View.VISIBLE);
                                holder.img_addTo_basket.setVisibility(View.GONE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "can't add to basket", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }


                }
            });

            holder.img_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.child("orders").child(product.getCode()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            holder.img_addTo_basket.setVisibility(View.VISIBLE);
                            holder.img_remove.setVisibility(View.GONE);
                            Toast.makeText(context, "delete from basket", Toast.LENGTH_SHORT).show();
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

        ImageView img_product,img_description, img_addTo_basket,img_remove;
        TextView tv_name ,tv_price;


        public product_ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.imgProduct);
            img_description = itemView.findViewById(R.id.img_description);
            img_addTo_basket = itemView.findViewById(R.id.img_basket);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            img_remove = itemView.findViewById(R.id.img_remove);


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



}
