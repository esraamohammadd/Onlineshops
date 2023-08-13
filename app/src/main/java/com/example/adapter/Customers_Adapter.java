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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shop.R;
import com.example.pojo.ChatModel;
import com.example.pojo.Order;
import com.example.pojo.Product;
import com.example.ui.Chat;
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


public class Customers_Adapter extends RecyclerView.Adapter<Customers_Adapter.Chat_ViewHolder> {

    Context context;
    ArrayList<ChatModel>chatModels;



    public Customers_Adapter(Context context, ArrayList<ChatModel> chats) {
        this.context = context;
        this.chatModels = chats;

    }


    @NonNull
    @Override
    public Chat_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.message_adapter_layout,parent,false);
        return new Chat_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Chat_ViewHolder holder, int position)
    {
        ChatModel chatModel = chatModels.get(position);


        holder.email.setText(chatModel.getMobile());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("email","admin@admin.com");
                intent.putExtra("mobile","adminMobile");
                intent.putExtra("customerMobile",holder.email.getText().toString());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class Chat_ViewHolder extends RecyclerView.ViewHolder {


        TextView email,lastMsg;


        public Chat_ViewHolder(@NonNull View itemView) {
            super(itemView);

            lastMsg = itemView.findViewById(R.id.lastMessage);
            email = itemView.findViewById(R.id.email);



        }


    }




}
