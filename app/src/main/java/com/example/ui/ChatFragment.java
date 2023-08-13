package com.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    RecyclerView messagesRecycleView;
  ArrayList<ChatModel> chats = new ArrayList<>();
  Customers_Adapter customers_adapter;

    DatabaseReference databaseReference ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        messagesRecycleView = view.findViewById(R.id.messagesRecyclerView);
        customers_adapter = new Customers_Adapter(getActivity(),chats);
         databaseReference = FirebaseDatabase.getInstance().getReference();


          getCustomers();
        messagesRecycleView.setHasFixedSize(true);
        messagesRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        messagesRecycleView.setAdapter(customers_adapter);
        return view;


    }
    public void getCustomers(){

      Intent intent =getActivity().getIntent();
     String mobile = intent.getStringExtra("mobile");
        databaseReference.child("customers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for (DataSnapshot snap : snapshot.getChildren())
                {
                    ChatModel chatModel =snap.getValue(ChatModel.class);


                    if (!chatModel.getEmailUser().equals("admin@admin.com")) {
                        chats.add(chatModel);

                    }customers_adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void getCustomer(){

        Intent intent =getActivity().getIntent();
        String mobile = intent.getStringExtra("mobile");
        databaseReference.child("customers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ChatModel chatModel = snapshot.getValue(ChatModel.class);

                if (!chatModel.getEmailUser().equals("admin@admin.com")) {
                    chats.add(chatModel);
                    customers_adapter.notifyDataSetChanged();
                }

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

