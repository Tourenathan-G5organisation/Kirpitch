package com.example.android.kirpitch;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText registerEmail, registerPassword;
    Button loginButton, registerButton;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        registerEmail = findViewById(R.id.e_mail);
        registerPassword = findViewById(R.id.password);
        registerButton = findViewById(R.id.button_registerRA) ;
        loginButton = findViewById(R.id.button_loginRA);


        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registerEmailString = Objects.requireNonNull(registerEmail.getText()).toString();
                String registerPasswordString = Objects.requireNonNull(registerPassword.getText()).toString();

                if(TextUtils.isEmpty(registerEmailString)){
                    Toast.makeText(getApplicationContext(),"Please fill in the email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(registerPasswordString)){
                    Toast.makeText(getApplicationContext(), "Please fill in the passeword", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(registerPasswordString.length()<6){
                    Toast.makeText(getApplicationContext(), "registerPassword must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.createUserWithEmailAndPassword(registerEmailString, registerPasswordString)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "E-mail or registerPassword is wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

    }
}
