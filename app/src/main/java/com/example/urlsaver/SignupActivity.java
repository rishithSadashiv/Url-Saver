package com.example.urlsaver;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText email = findViewById(R.id.editTextEmailSignup);
    EditText password = findViewById(R.id.editTextPasswordSignup);
    Button signup = findViewById(R.id.buttonSignupSignup);

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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

                } else if(p.length()<6){
                    password.setError("Minimum password length should be 6");
                    password.requestFocus();
                } else{
                    mAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "User regisered successfully", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

            }
        });


    }
}
