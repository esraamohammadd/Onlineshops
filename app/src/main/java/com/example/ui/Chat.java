package com.example.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapter.Chat_Adapter;
import com.example.adapter.Customers_Adapter;
import com.example.online_shop.R;
import com.example.pojo.ChatModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class Chat extends AppCompatActivity {

    TextView tv_AdminName;
    ImageView img_send;
    EditText et_msg;
    RecyclerView rec_chat;
    Chat_Adapter chat_adapter;
    ArrayList<ChatModel > chatModels;

    String email;
    String mobile ;
    String admin_email;
    String admin_mobile ;
    String customerMobile;



    DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        tv_AdminName = findViewById(R.id.name);
        et_msg = findViewById( R.id.messageEditTxt);
        img_send = findViewById(R.id.sendBtn);
        rec_chat = findViewById(R.id.chattingRecyclerView);

        Intent intent = getIntent();

        email = intent.getStringExtra(Customer.ARG_EMAIL);
        mobile = intent.getStringExtra(Customer.ARG_MOBILE);
        admin_email = intent.getStringExtra("admin_email");
        admin_mobile = intent.getStringExtra("admin_mobile");

        customerMobile = intent.getStringExtra("customerMobile");



        tv_AdminName.setText(mobile);
        chatModels = new ArrayList<>();
        chat_adapter = new Chat_Adapter(this,chatModels);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        getChats();

        rec_chat.hasFixedSize();
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rec_chat.setLayoutManager(lm);
        rec_chat.setAdapter(chat_adapter);





        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMsg();
            }
        });

    }

    public  void sendMsg()
    {

        String id = UUID.randomUUID().toString();
        ChatModel chatModel;
             if (admin_email != null)
             {
                 chatModel = new ChatModel(et_msg.getText().toString(), id, admin_email, customerMobile);

                 databaseReference.child("chat").child(chatModel.getMobile()).child(id).setValue(chatModel);
                 databaseReference.child("customers").child(customerMobile).setValue(chatModel);
             }
          else {
                 chatModel = new ChatModel(et_msg.getText().toString(), id, email, mobile);
                 databaseReference.child("chat").child(chatModel.getMobile()).child(id).setValue(chatModel);
                 databaseReference.child("customers").child(mobile).setValue(chatModel);

             }


           et_msg.setText("");

    }

    public void getChat(){
        String phone;
        if (email.equals("admin@admin.com")) {
            phone = customerMobile;
        }else {
            phone=mobile;
        }


        databaseReference.child("chat").child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for (DataSnapshot snap : snapshot.getChildren())
                {

                    ChatModel chatModel =snap.getValue(ChatModel.class);

                        chatModels.add(chatModel);
                        chat_adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getChats(){
        String phone;

        if (admin_mobile!=null) {
            phone = customerMobile;
        }else if (mobile != null){
            phone=mobile;
        }else{phone = "000";}
        databaseReference.child("chat").child(phone).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ChatModel chatModel = snapshot.getValue(ChatModel.class);
                chat_adapter.addChat(chatModel);
                chat_adapter.notifyDataSetChanged();
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