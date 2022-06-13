package com.example.extracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.extracker.Model.ExpenseModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllExpensesActivity extends AppCompatActivity {
    ImageButton btn_back;
    FloatingActionButton add_ex_btn;
    RecyclerView exp_recyclerview;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String activeUser ="";
    private ExpenseAdapter adapter;
    private ArrayList<ExpenseModel> expenseList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_expenses);

        add_ex_btn = findViewById(R.id.add_ex_btn);
        btn_back = findViewById(R.id.btn_back);

        exp_recyclerview = findViewById(R.id.expense_recycler);
        exp_recyclerview.setHasFixedSize(true);
        exp_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();
        activeUser = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(activeUser);

        expenseList = new ArrayList<>();
        adapter = new ExpenseAdapter(expenseList,this);
        exp_recyclerview.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ExpenseModel expenseModel =  dataSnapshot.getValue(ExpenseModel.class);
                    expenseList.add(expenseModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add_ex_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllExpensesActivity.this,ExpensesActivity.class));
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllExpensesActivity.this,DashboardActivity.class));
            }
        });


    }
}