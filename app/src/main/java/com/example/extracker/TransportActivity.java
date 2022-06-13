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

import com.example.extracker.Model.TransportModel;
import com.example.extracker.Model.UtilitiesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransportActivity extends AppCompatActivity {
    ImageButton btn_back;
    RecyclerView transport_recyclerview;
    AppCompatButton trans_btn_transport, add_transport_btn;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String activeUser ="";
    private TransportAdapter adapter;
    private ArrayList<TransportModel> transportModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        btn_back = findViewById(R.id.back_btn);
        trans_btn_transport = findViewById(R.id.trans_btn_transport);
        add_transport_btn = findViewById(R.id.add_transport_btn);
        transport_recyclerview = findViewById(R.id.transport_recycler);

        transport_recyclerview = findViewById(R.id.transport_recycler);
        transport_recyclerview.setHasFixedSize(true);
        transport_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();
        activeUser = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(activeUser);

        Query query = reference.orderByChild("category").equalTo("Transport");


        transportModels= new ArrayList<>();
        adapter = new TransportAdapter(transportModels,this);
        transport_recyclerview.setAdapter(adapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    TransportModel model =  dataSnapshot.getValue(TransportModel.class);
                    transportModels.add(model);
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

        trans_btn_transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransportActivity.this,TransactionsActivity.class));
            }
        });

        add_transport_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransportActivity.this,ExpensesActivity.class));
            }
        });
    }
}