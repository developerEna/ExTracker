package com.example.extracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.extracker.Model.ExpenseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ExpensesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageButton btn_back;
    private Spinner category;
    private Spinner bd_select;
    private EditText expense,notes;
    private AppCompatButton btn_save,btn_scan;
    private String categories[] = {"Select Category","Food", "Utilities","Transport","Others"};

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private DatabaseReference bdReference;
    private String activeUser ="";

    private ArrayList<String> bdArraylist;
    private ArrayAdapter<String> bdArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        category = findViewById(R.id.category);
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        expense = findViewById(R.id.expense);
        notes = findViewById(R.id.description);
        bd_select = findViewById(R.id.select_bd);
        btn_scan = findViewById(R.id.btn_scan);

        auth = FirebaseAuth.getInstance();
        activeUser = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(activeUser);
        bdReference = FirebaseDatabase.getInstance().getReference().child("Budgets").child(activeUser);



        category.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);


        bdArraylist = new ArrayList<>();
        bdArrayAdapter = new ArrayAdapter<>(ExpensesActivity.this, android.R.layout.simple_spinner_dropdown_item,bdArraylist);
        bd_select.setAdapter(bdArrayAdapter);

        bdReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    bdArraylist.add(item.child("bd_name").getValue().toString());
                }
                bdArrayAdapter.notifyDataSetChanged();
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

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpensesActivity.this,QrScannerActivity.class));
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ex_amount = expense.getText().toString();
                String cate = category.getSelectedItem().toString();
                String bd = bd_select.getSelectedItem().toString();
                String desc = notes.getText().toString();

                if(TextUtils.isEmpty(ex_amount)){
                    expense.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(desc)){
                    notes.setError("Required");
                    return;
                }
                if(cate.equalsIgnoreCase("Select Category")){
                    Toast.makeText(getApplicationContext(),"Please select a category",Toast.LENGTH_LONG).show();
                }
                if(bd.equalsIgnoreCase("Select Budget")){
                    Toast.makeText(getApplicationContext(),"Please select a budget",Toast.LENGTH_LONG).show();
                }
                else{
                    String id = reference.push().getKey();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar calendar = Calendar.getInstance();
                    String date = dateFormat.format(calendar.getTime());

                    ExpenseModel expenseModel = new ExpenseModel(cate,desc,date,id,bd,Integer.parseInt(ex_amount));

                    reference.child(id).setValue(expenseModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Expense added successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ExpensesActivity.this,AllExpensesActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(),"Failed to add expenses",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),categories[position],Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}