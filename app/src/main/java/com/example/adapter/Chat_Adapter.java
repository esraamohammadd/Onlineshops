package com.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.Chat_ViewHolder> {

    Context context;
    ArrayList<ChatModel>chatModels;



    public Chat_Adapter(Context context,ArrayList<ChatModel> chatModels) {
        this.context = context;
        this.chatModels = chatModels;

    }


    @NonNull
    @Override
    public Chat_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_layout,parent,false);
        return new Chat_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Chat_ViewHolder holder, int position)
    {
       ChatModel chatModel = chatModels.get(position);

        if(chatModel.getEmailUser().equals("admin@admin.com")){

            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);

           holder.myMessage.setText(chatModel.getMessage());

        }else {
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            holder.oppoMessage.setText(chatModel.getMessage());

        }

         }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class Chat_ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout oppoLayout, myLayout;
        private TextView oppoMessage, myMessage;




        public Chat_ViewHolder(@NonNull View itemView) {
            super(itemView);

            oppoLayout = itemView.findViewById(R.id.oppoLayout);
            myLayout = itemView.findViewById(R.id.myLayout);
            oppoMessage = itemView.findViewById(R.id.oppoMessage);
            myMessage = itemView.findViewById(R.id.myMessage);




        }


    }
   public  void  addChat(ChatModel chatModel){
        chatModels.add(chatModel);



   }

    public  void  clear(){
        chatModels.clear();}


}
