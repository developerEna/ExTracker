package com.example.extracker;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextView signup_page, forgot_pass;
    AppCompatButton login_btn;
    EditText edtEmail, edtPassword;
    RelativeLayout progressBar;

    String txt_email,txt_password;

    FirebaseAuth mAuth;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.btn_login);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        signup_page = findViewById(R.id.signup_page);
        progressBar = findViewById(R.id.progess_bar);
        forgot_pass = findViewById(R.id.forgot_pass);

        mAuth = FirebaseAuth.getInstance();

        //
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_email = edtEmail.getText().toString();
                txt_password = edtPassword.getText().toString();


                //Check if input field is empty
                if(TextUtils.isEmpty(txt_email)){
                    edtEmail.setError("Required");
                }
                else if(TextUtils.isEmpty(txt_password)){
                    edtPassword.setError("Required");
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    LoginMeIn(txt_email,txt_password);
                    edtEmail.setText("");
                    edtPassword.setText("");
                }

            }
        });

        signup_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });
    }


    private void LoginMeIn(String email, String password) {

        //Sign the user in if email and password is correct
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(),"Logged In Successfully",Toast.LENGTH_SHORT).show();

                }
                else{
                    try
                    {
                        throw task.getException();
                    }
                    // if user enters wrong email.
                    catch (FirebaseAuthInvalidUserException invalidEmail)
                    {
                        Log.d(TAG, "onComplete: invalid_email");
                        Toast.makeText(getApplicationContext(),"Email does not exist",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        edtEmail.setText("");
                        edtPassword.setText("");
                    }
                    // if user enters wrong password.
                    catch (FirebaseAuthInvalidCredentialsException wrongPassword)
                    {
                        Log.d(TAG, "onComplete: wrong_password");
                        progressBar.setVisibility(View.GONE);
                        edtEmail.setText("");
                        edtPassword.setText("");

                        Toast.makeText(getApplicationContext(),"Please enter correct password",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    {
                        Log.d(TAG, "onComplete: " + e.getMessage());
                    }
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        }
    }

}