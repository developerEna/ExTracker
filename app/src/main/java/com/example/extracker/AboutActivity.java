package com.example.extracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        btn_back = findViewById(R.id.btn_back);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.about);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.category:
                        startActivity(new Intent(getApplicationContext(),CategoryActivity.class));
                        finish();
                        overridePendingTransition(0,0);
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

                        return true;
                }
                return false;
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}