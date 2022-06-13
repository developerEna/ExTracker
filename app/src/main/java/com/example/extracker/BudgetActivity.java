package com.example.extracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.extracker.Model.BudgetModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class BudgetActivity extends AppCompatActivity {
    ImageButton btn_back;
    AppCompatButton btn_from,btn_to,btn_save;
    private Calendar calendar;
    private EditText amount,name;
    DatePickerDialog dialog;
    private int year, month, day;
    private String fromDate="";
    private String toDate="";

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String activeUser ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        btn_back = findViewById(R.id.btn_back);
        btn_from = findViewById(R.id.btn_from);
        btn_to = findViewById(R.id.btn_to);
        btn_save = findViewById(R.id.btn_save);

        auth = FirebaseAuth.getInstance();
        activeUser = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Budgets").child(activeUser);

        btn_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                amount = findViewById(R.id.amount);
                name = findViewById(R.id.bd_name);

                dialog = new DatePickerDialog(BudgetActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        fromDate = day + "/"+ (month+1) + "/" + year;
                    }
                },year,month,day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

            }
        });

        btn_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                dialog = new DatePickerDialog(BudgetActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        toDate = day + "/"+ (month+1) + "/" + year;
                    }
                },year,month,day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bd_amount = amount.getText().toString();
                String bd_name = name.getText().toString();

                if(TextUtils.isEmpty(bd_amount)){
                    amount.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(bd_name)){
                    name.setError("Required");
                    return;
                }
                if (fromDate.isEmpty()){
                    btn_from.setError("Required");
                }
                if (toDate.isEmpty()){
                    btn_to.setError("Required");
                }
                else {
                    String id = reference.push().getKey();
                    BudgetModel model = new BudgetModel(bd_name,fromDate,toDate,id,Integer.parseInt(bd_amount));

                    reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Budget added successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(BudgetActivity.this, AllBudgetActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(),"Failed to add budget,Try again",Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }





            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}