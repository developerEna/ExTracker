package com.example.extracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.example.extracker.Model.BudgetModel;
import com.example.extracker.Model.ExpenseModel;
import com.example.extracker.Model.Users;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;





public class DashboardActivity extends AppCompatActivity {

    CardView all_budget,all_expense;
    TextView logout;
    CardView food_dash,utils_dash, transport_dash,others_dash;
    BottomNavigationView bottomNavigationView;
    TextView display_name,bud_txt, bal_txt,ex_txt;
    AppCompatButton guide;
    TextView status;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference ,ref,databaseReference;


    public static final String SHARED_PREFERS="sharedPrefs";

    private String bd_name;
    int amount,bd_amount,total_ex= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        all_budget = findViewById(R.id.all_budget);
        all_expense = findViewById(R.id.all_expenses);
        food_dash = findViewById(R.id.food_dash);
        utils_dash = findViewById(R.id.utils_dash);
        transport_dash = findViewById(R.id.transport_dash);
        others_dash = findViewById(R.id.others_dash);
        logout = findViewById(R.id.txt_logout);
        display_name = findViewById(R.id.ds_name);
        bud_txt = findViewById(R.id.bud_txt);
        bal_txt = findViewById(R.id.bal_txt);
        ex_txt = findViewById(R.id.ex_txt);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        guide = findViewById(R.id.guide);
        status = findViewById(R.id.status);



        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TapTargetSequence(DashboardActivity.this)
                        .targets(
                                TapTarget.forView(all_budget,"Budget button","View all budgets when you tap on me ")
                                        .outerCircleColor(R.color.green)
                                        .outerCircleAlpha(0.96f)
                                        .targetCircleColor(R.color.white)
                                        .titleTextSize(20)
                                        .titleTextColor(R.color.white)
                                        .descriptionTextSize(12)
                                        .descriptionTextColor(R.color.black)
                                        .textColor(R.color.black)
                                        .textTypeface(Typeface.SANS_SERIF)
                                        .dimColor(R.color.black)
                                        .drawShadow(true)
                                        .cancelable(false)
                                        .tintTarget(true)
                                        .transparentTarget(true)
                                        .targetRadius(60),
                                TapTarget.forView(bal_txt,"Balance","Displays your current account balance ")
                                        .outerCircleColor(R.color.green)
                                        .outerCircleAlpha(0.96f)
                                        .targetCircleColor(R.color.white)
                                        .titleTextSize(20)
                                        .titleTextColor(R.color.white)
                                        .descriptionTextSize(12)
                                        .descriptionTextColor(R.color.black)
                                        .textColor(R.color.black)
                                        .textTypeface(Typeface.SANS_SERIF)
                                        .dimColor(R.color.black)
                                        .drawShadow(true)
                                        .cancelable(false)
                                        .tintTarget(true)
                                        .transparentTarget(true)
                                        .targetRadius(60),
                                TapTarget.forView(all_expense,"Expenses button","View all expenses when you tap on me")
                                        .outerCircleColor(R.color.green)
                                        .outerCircleAlpha(0.96f)
                                        .targetCircleColor(R.color.white)
                                        .titleTextSize(20)
                                        .titleTextColor(R.color.white)
                                        .descriptionTextSize(12)
                                        .descriptionTextColor(R.color.black)
                                        .textColor(R.color.black)
                                        .textTypeface(Typeface.SANS_SERIF)
                                        .dimColor(R.color.black)
                                        .drawShadow(true)
                                        .cancelable(false)
                                        .tintTarget(true)
                                        .transparentTarget(true)
                                        .targetRadius(60),
                                TapTarget.forView(food_dash,"Food expenses","View all food expenses when you tap on me")
                                        .outerCircleColor(R.color.green)
                                        .outerCircleAlpha(0.96f)
                                        .targetCircleColor(R.color.white)
                                        .titleTextSize(20)
                                        .titleTextColor(R.color.white)
                                        .descriptionTextSize(12)
                                        .descriptionTextColor(R.color.black)
                                        .textColor(R.color.black)
                                        .textTypeface(Typeface.SANS_SERIF)
                                        .dimColor(R.color.black)
                                        .drawShadow(true)
                                        .cancelable(false)
                                        .tintTarget(true)
                                        .transparentTarget(true)
                                        .targetRadius(60),
                                TapTarget.forView(utils_dash,"Utility expenses","View all Utility expenses when you tap on me")
                                        .outerCircleColor(R.color.green)
                                        .outerCircleAlpha(0.96f)
                                        .targetCircleColor(R.color.white)
                                        .titleTextSize(20)
                                        .titleTextColor(R.color.white)
                                        .descriptionTextSize(12)
                                        .descriptionTextColor(R.color.black)
                                        .textColor(R.color.black)
                                        .textTypeface(Typeface.SANS_SERIF)
                                        .dimColor(R.color.black)
                                        .drawShadow(true)
                                        .cancelable(false)
                                        .tintTarget(true)
                                        .transparentTarget(true)
                                        .targetRadius(60),
                                TapTarget.forView(transport_dash,"Transport expenses","View all Transport expenses when you tap on me")
                                        .outerCircleColor(R.color.green)
                                        .outerCircleAlpha(0.96f)
                                        .targetCircleColor(R.color.white)
                                        .titleTextSize(20)
                                        .titleTextColor(R.color.white)
                                        .descriptionTextSize(12)
                                        .descriptionTextColor(R.color.black)
                                        .textColor(R.color.black)
                                        .textTypeface(Typeface.SANS_SERIF)
                                        .dimColor(R.color.black)
                                        .drawShadow(true)
                                        .cancelable(false)
                                        .tintTarget(true)
                                        .transparentTarget(true)
                                        .targetRadius(60),
                                TapTarget.forView(others_dash,"Other expenses","View all other expenses when you tap on me")
                                        .outerCircleColor(R.color.green)
                                        .outerCircleAlpha(0.96f)
                                        .targetCircleColor(R.color.white)
                                        .titleTextSize(20)
                                        .titleTextColor(R.color.white)
                                        .descriptionTextSize(12)
                                        .descriptionTextColor(R.color.black)
                                        .textColor(R.color.black)
                                        .textTypeface(Typeface.SANS_SERIF)
                                        .dimColor(R.color.black)
                                        .drawShadow(true)
                                        .cancelable(false)
                                        .tintTarget(true)
                                        .transparentTarget(true)
                                        .targetRadius(60)).listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {

                        Toast.makeText(DashboardActivity.this,"Guide ended",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                        Toast.makeText(DashboardActivity.this,"Next!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                }).start();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);

                display_name.setText("Welcome, " + users.getUsername().toUpperCase());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("Budgets").child(firebaseUser.getUid());
        Query query = ref.orderByKey().limitToLast(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    BudgetModel model =  dataSnapshot.getValue(BudgetModel.class);
                    int budget= model.getAmount();
                    bud_txt.setText("GHS " + Integer.toString(budget));

                    SharedPreferences preferences = getSharedPreferences(SHARED_PREFERS,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("budgetName", model.getBd_name());
                    editor.putInt("budgetAmount", model.getAmount());
                    editor.apply();



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERS,MODE_PRIVATE);
        bd_name = sharedPreferences.getString("budgetName","");
        bd_amount = sharedPreferences.getInt("budgetAmount",0);






        databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseUser.getUid());
        Query query1 = databaseReference.orderByChild("bd_name").equalTo(bd_name);

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int exp_amount=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ExpenseModel model = dataSnapshot.getValue(ExpenseModel.class);
                    exp_amount = exp_amount + model.getAmount();

                    SharedPreferences preferences = getSharedPreferences(SHARED_PREFERS,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("ExpenseAmount", exp_amount);
                    editor.apply();
                }

                ex_txt.setText("GHS " + Integer.toString(exp_amount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        SharedPreferences sharedPreferences1 = getSharedPreferences(SHARED_PREFERS,MODE_PRIVATE);
        total_ex = sharedPreferences1.getInt("ExpenseAmount",0);


        amount = bd_amount - total_ex;

        bal_txt.setText("GHS " + Integer.toString(amount));

        if(total_ex > bd_amount){
            status.setText("Budget Limit Exceeded");
        }







        all_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AllBudgetActivity.class));
            }
        });


        all_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,AllExpensesActivity.class));
            }
        });


        food_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,FoodActivity.class));
            }
        });

        transport_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,TransportActivity.class));
            }
        });

        utils_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,UtilsActivity.class));
            }
        });

        others_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,OthersActivity.class));
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.home);
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
                        return true;

                    case R.id.budget:
                        startActivity(new Intent(getApplicationContext(), AllBudgetActivity.class));
                        finish();
                        overridePendingTransition(0,0);
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


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();

            }
        });



    }

    private void Logout() {
        if(logout.getId() == R.id.txt_logout){

            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));

        }
    }
}