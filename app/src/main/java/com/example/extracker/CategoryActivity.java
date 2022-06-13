package com.example.extracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CategoryActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageButton btn_back;
    CardView food_dash, utils_dash, trans_dash, others_dash;
    Button all_trans_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        food_dash = findViewById(R.id.food_card);
        utils_dash = findViewById(R.id.utils_card);
        trans_dash = findViewById(R.id.transport_card);
        others_dash = findViewById(R.id.others_card);
        all_trans_btn = findViewById(R.id.all_trans_btn);


        btn_back = findViewById(R.id.btn_back);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.category);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.category:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.budget:
                        startActivity(new Intent(getApplicationContext(), AllBudgetActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.transactions:
                        startActivity(new Intent(getApplicationContext(),TransactionsActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        food_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this,FoodActivity.class));
            }
        });


        utils_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, UtilsActivity.class));
            }
        });


        trans_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, TransportActivity.class));
            }
        });

        others_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, OthersActivity.class));
            }
        });


        all_trans_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, TransactionsActivity.class));
            }
        });


    }
}