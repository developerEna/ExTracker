package com.example.extracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.extracker.Model.FoodModel;
import com.example.extracker.Model.UtilitiesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UtilsActivity extends AppCompatActivity {
    ImageButton btn_back;
    RecyclerView util_recyclerview;
    AppCompatButton trans_btn_utils, add_utils_btn;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String activeUser ="";
    private UtilsAdapter adapter;
    private ArrayList<UtilitiesModel> utilsList;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils);

        btn_back = findViewById(R.id.back_btn);
        trans_btn_utils = findViewById(R.id.trans_btn_utils);
        add_utils_btn = findViewById(R.id.add_utils_btn);
        util_recyclerview = findViewById(R.id.util_recycler);

        util_recyclerview = findViewById(R.id.util_recycler);
        util_recyclerview.setHasFixedSize(true);
        util_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();
        activeUser = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(activeUser);

        Query query = reference.orderByChild("category").equalTo("Utilities");


        utilsList= new ArrayList<>();
        adapter = new UtilsAdapter(utilsList,this);
        util_recyclerview.setAdapter(adapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    UtilitiesModel utilitiesModel =  dataSnapshot.getValue(UtilitiesModel.class);
                    utilsList.add(utilitiesModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        trans_btn_utils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UtilsActivity.this,TransactionsActivity.class));
            }
        });

        add_utils_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UtilsActivity.this,ExpensesActivity.class));
            }
        });
    }
}