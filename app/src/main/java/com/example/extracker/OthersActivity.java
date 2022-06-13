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

import com.example.extracker.Model.OthersModel;
import com.example.extracker.Model.TransportModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OthersActivity extends AppCompatActivity {
    ImageButton btn_back;
    RecyclerView others_recyclerview;
    AppCompatButton trans_btn_others, add_others_btn;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String activeUser ="";
    private OthersAdapter adapter;
    private ArrayList<OthersModel> othersModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        btn_back = findViewById(R.id.back_btn);
        trans_btn_others = findViewById(R.id.trans_btn_others);
        add_others_btn = findViewById(R.id.add_others_btn);
        others_recyclerview = findViewById(R.id.others_recyclerview);


        others_recyclerview.setHasFixedSize(true);
        others_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();
        activeUser = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(activeUser);

        Query query = reference.orderByChild("category").equalTo("Others");


        othersModels= new ArrayList<>();
        adapter = new OthersAdapter(othersModels,this);
        others_recyclerview.setAdapter(adapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    OthersModel model =  dataSnapshot.getValue(OthersModel.class);
                    othersModels.add(model);
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

        trans_btn_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OthersActivity.this,TransactionsActivity.class));
            }
        });

        add_others_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OthersActivity.this,ExpensesActivity.class));
            }
        });
    }
}