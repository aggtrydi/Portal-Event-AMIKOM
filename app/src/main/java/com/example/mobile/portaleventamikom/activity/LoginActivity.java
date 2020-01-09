package com.example.mobile.portaleventamikom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile.portaleventamikom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database;
    private FirebaseAuth uAuth;
    DatabaseReference dbRef;

    EditText edLoginEmail;
    EditText edLoginSandi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnDaftar =  findViewById(R.id.signUp_text);

        database = FirebaseDatabase.getInstance();
        uAuth = FirebaseAuth.getInstance();
        edLoginEmail = (EditText)findViewById(R.id.edLoginEmail);
        edLoginSandi = (EditText)findViewById(R.id.edLoginSandi);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uEmail = edLoginEmail.getText().toString();
                String uSandi = edLoginSandi.getText().toString();
                loginUser(uEmail,uSandi);
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftarIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(daftarIntent);
            }
        });

    }

    private void loginUser(String uEmail, String uSandi) {
        uAuth.signInWithEmailAndPassword(uEmail, uSandi)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = uAuth.getCurrentUser();
                            Intent masukIntent =  new Intent(LoginActivity.this, UtamaActivity.class);
                            startActivity(masukIntent);
                            finish();
                        } else {

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}
