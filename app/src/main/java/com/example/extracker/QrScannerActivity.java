package com.example.extracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QrScannerActivity extends AppCompatActivity {
    private String categories[] = {"Select Category","Food", "Utilities","Transport","Others"};
    private ArrayList<String> bdArraylist;
    private ArrayAdapter<String> bdArrayAdapter;
    EditText txtbExpenses;
    EditText txtbNote;
    Spinner txtbCategory;
    Spinner select_bd;
    private ImageButton btn_back;
    private AppCompatButton btn_save,btn_scan;

    private FirebaseAuth auth;
    private DatabaseReference bdReference,reference;
    private String activeUser ="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        txtbExpenses = findViewById(R.id.expense);
        txtbCategory = findViewById(R.id.category);
        select_bd = findViewById(R.id.select_bd);
        txtbNote = findViewById(R.id.description);
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);

        auth = FirebaseAuth.getInstance();
        activeUser = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(activeUser);
        bdReference = FirebaseDatabase.getInstance().getReference().child("Budgets").child(activeUser);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtbCategory.setAdapter(adapter);


        bdArraylist = new ArrayList<>();
        bdArrayAdapter = new ArrayAdapter<>(QrScannerActivity.this, android.R.layout.simple_spinner_dropdown_item,bdArraylist);
        select_bd.setAdapter(bdArrayAdapter);

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


        //set prompt text
        IntentIntegrator intentIntegrator = new IntentIntegrator(QrScannerActivity.this);
        intentIntegrator.setPrompt("To turn on flash, press volume up key");

        //set beep
        intentIntegrator.setBeepEnabled(true);

        //lock orientation
        intentIntegrator.setOrientationLocked(true);

        //set capture activity
        intentIntegrator.setCaptureActivity(Capture.class);

        //Initiate Scan
        intentIntegrator.initiateScan();


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ex_amount = txtbExpenses.getText().toString();
                String cate = txtbCategory.getSelectedItem().toString();
                String bd = select_bd.getSelectedItem().toString();
                String desc = txtbNote.getText().toString();

                if(TextUtils.isEmpty(ex_amount)){
                    txtbExpenses.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(desc)){
                    txtbNote.setError("Required");
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
                                startActivity(new Intent(QrScannerActivity.this,AllExpensesActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(),"Failed to add expenses",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );

        //Check if result content is not null
        if(intentResult.getContents()!=null){






            //set scan result content to text box
            String result = intentResult.getContents();
            String[] separate = result.split("\n");


            txtbExpenses.setText(separate[0]);


          //  txtbDate.setText(separate[2]);
            txtbNote.setText(separate[1]);


        }else{
            //when result content is null, display toast
            Toast.makeText(getApplicationContext(), "oops i got nothing", Toast.LENGTH_SHORT).show();
        }
    }



}