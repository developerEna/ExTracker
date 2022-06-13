package com.example.extracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    TextView login_page;
    EditText f_name, email,password;
    Button btn_signup;
    RelativeLayout progressBar;

    String first_name,txt_email,txt_password;

    private FirebaseAuth mAuth;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        login_page = findViewById(R.id.login_page);
        f_name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_signup = findViewById(R.id.btn_signup);
        progressBar = findViewById(R.id.progess_bar);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                first_name = f_name.getText().toString();
                txt_email = email.getText().toString();
                txt_password = password.getText().toString();

                if(TextUtils.isEmpty(first_name)){
                    f_name.setError("Required");
                }
                else if(TextUtils.isEmpty(txt_email)){
                    email.setError("Required");
                }
                else if(TextUtils.isEmpty(txt_password)){
                    password.setError("Required");
                }
                else if(txt_password.length() < 6){
                    password.setError("Password length must be 6 and more");
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(first_name,txt_email,txt_password);
                    f_name.setText("");
                    password.setText("");
                    email.setText("");
                }
                progressBar.setVisibility(View.GONE);
            }
        });

        login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void registerUser(String f_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Get current user
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();



                    //Instance of the firebaseDatabase with reference
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());


                    if(firebaseUser != null){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("username", f_name);
                        hashMap.put("email", email);
                        hashMap.put("id", firebaseUser.getUid());

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                                }

                            }
                        });
                    }


                }


            }
        });



    }
}