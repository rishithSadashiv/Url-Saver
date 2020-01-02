package com.example.urlsaver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button signup;
    ProgressBar progressbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.editTextEmailSignup);
        password = findViewById(R.id.editTextPasswordSignup);
        signup = findViewById(R.id.buttonSignupSignup);
        progressbar = findViewById(R.id.progressBarSignup);

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();

                if (e.isEmpty()) {
                    email.setError("Email is required");
                    email.requestFocus();
                } else if (p.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
                    email.setError("Please enter valid email");
                    email.requestFocus();

                } else if (p.length() < 6) {
                    password.setError("Minimum password length should be 6");
                    password.requestFocus();
                } else {
                    progressbar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressbar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                finish();
                                Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignupActivity.this, UrlActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                    Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_LONG).show();

                                } else {
                                    try {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    } catch (NullPointerException e) {
                                        e.getStackTrace();
                                    }
                                }
                            }
                        }
                    });

                }

            }
        });


    }
}
