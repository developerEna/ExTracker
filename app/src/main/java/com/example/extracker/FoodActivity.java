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
import com.example.extracker.Model.OthersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {
    ImageButton btn_back;
    RecyclerView food_recylerview;
    AppCompatButton trans_btn_food,add_food_btn;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String activeUser ="";
    private FoodAdapter adapter;
    private ArrayList<FoodModel> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        btn_back = findViewById(R.id.back_btn);
        trans_btn_food = findViewById(R.id.trans_btn_food);
        add_food_btn = findViewById(R.id.add_food_btn);
        food_recylerview = findViewById(R.id.food_recycler);

        food_recylerview = findViewById(R.id.food_recycler);
        food_recylerview.setHasFixedSize(true);
        food_recylerview.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();
        activeUser = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(activeUser);

        Query query = reference.orderByChild("category").equalTo("Food");


        foodList = new ArrayList<>();
        adapter = new FoodAdapter(foodList,this);
        food_recylerview.setAdapter(adapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    FoodModel foodModel =  dataSnapshot.getValue(FoodModel.class);
                    foodList.add(foodModel);
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

        trans_btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodActivity.this,TransactionsActivity.class));
            }
        });

        add_food_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodActivity.this,ExpensesActivity.class));
            }
        });
    }
}