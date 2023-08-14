package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shop.R;
import com.example.pojo.ChatModel;
import com.example.ui.Chat;

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


        holder.mobile.setText(chatModel.getMobile());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("admin_email","admin@admin.com");
                intent.putExtra("admin_mobile","adminMobile");
                intent.putExtra("customerMobile",holder.mobile.getText().toString());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class Chat_ViewHolder extends RecyclerView.ViewHolder {


        TextView mobile;


        public Chat_ViewHolder(@NonNull View itemView) {
            super(itemView);


            mobile = itemView.findViewById(R.id.mobile);



        }


    }




}
