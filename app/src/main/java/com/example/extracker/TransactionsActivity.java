package com.example.extracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TransactionsActivity extends AppCompatActivity {
    ImageButton btn_back;
    BottomNavigationView bottomNavigationView;
    Button expenses_btn, income_btn, btn_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        btn_back = findViewById(R.id.btn_back);
        expenses_btn= findViewById(R.id.btn_expenses);
        income_btn = findViewById(R.id. btn_income);
        btn_report = findViewById(R.id. btn_report);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.transactions);
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

        expenses_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionsActivity.this, AllExpensesActivity.class));
            }
        });

        income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionsActivity.this, AllBudgetActivity.class));
            }
        });

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TransactionsActivity.this, ReportActivity.class));
            }
        });
    }
}