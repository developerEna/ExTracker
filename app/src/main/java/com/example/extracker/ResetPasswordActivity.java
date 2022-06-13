package com.example.extracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText edt_email;
    AppCompatButton btn_email;
    RelativeLayout progress_bar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edt_email = findViewById(R.id.email);
        btn_email = findViewById(R.id.btn_email);
        progress_bar = findViewById(R.id.progess_bar);

        auth = FirebaseAuth.getInstance();

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });


    }
    private void resetPassword(){
        String email = edt_email.getText().toString().trim();

        if (email.isEmpty()){
            edt_email.setError("Email is required");
            edt_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edt_email.setError("Please provide a valid email");
            edt_email.requestFocus();
            return;
        }

        progress_bar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this,"Check your email to reset password",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(ResetPasswordActivity.this,"Something went wrong! Try Again",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}