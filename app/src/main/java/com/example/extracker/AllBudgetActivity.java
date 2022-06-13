package com.example.extracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.extracker.Model.BudgetModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllBudgetActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageButton btn_back;
    FloatingActionButton fb_add_budget;
    RecyclerView budgetRecycler;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String activeUser ="";
    private BudgetAdapter adapter;
    private ArrayList<BudgetModel> budgetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_budget);

        btn_back = findViewById(R.id.btn_back);
        fb_add_budget = findViewById(R.id.add_budget);


        budgetRecycler = findViewById(R.id.budget_recycler);
        budgetRecycler.setHasFixedSize(true);
        budgetRecycler.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();
        activeUser = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Budgets").child(activeUser);


        budgetList = new ArrayList<>();
        adapter = new BudgetAdapter(budgetList,this);
        budgetRecycler.setAdapter(adapter);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    BudgetModel budgetModel =  dataSnapshot.getValue(BudgetModel.class);
                    budgetList.add(budgetModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.budget);

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

        fb_add_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllBudgetActivity.this,BudgetActivity.class));
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllBudgetActivity.this,DashboardActivity.class));
            }
        });
    }
}