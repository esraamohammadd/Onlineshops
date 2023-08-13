package com.example.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.online_shop.R;
//import com.example.online_shop.databinding.ActivityBottomNavigationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class Seller extends AppCompatActivity {

   // ActivityBottomNavigationBinding binding ;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_seller);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        replaceFragment(new OrderFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.orders:
                        replaceFragment(new OrderFragment());
                        break;
                    case R.id.products:
                        replaceFragment(new ProductFragment());
                        break;
                    case R.id.chat:
                        replaceFragment(new ChatFragment());
                        break;
                    case R.id.add_product:
                        replaceFragment(new AddFragment());
                        break;
                }

                return true;
            }
        });




    }

     private  void replaceFragment(Fragment fragment)
     {
         FragmentManager fragmentManager = getSupportFragmentManager();
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
         fragmentTransaction.replace(R.id.framLayout,fragment);
         fragmentTransaction.commit();
     }
}