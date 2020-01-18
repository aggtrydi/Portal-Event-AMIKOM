package com.pea.mobile.portaleventamikom.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pea.mobile.portaleventamikom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database;
    private FirebaseAuth uAuth;

    SharedPreferences sharedPreferences;
    String MY_PREF;
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

        sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        edLoginEmail = (EditText)findViewById(R.id.edLoginEmail);
        edLoginSandi = (EditText)findViewById(R.id.edLoginSandi);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uEmail = edLoginEmail.getText().toString();
                    String uSandi = edLoginSandi.getText().toString();
                    loginUser(uEmail,uSandi);
                } catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Pastikan Tidak ada Yang Kosong ",Toast.LENGTH_SHORT).show();
                }

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

    private void loginUser(final String uEmail, final String uSandi) {
        uAuth.signInWithEmailAndPassword(uEmail, uSandi)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent masukIntent =  new Intent(LoginActivity.this, UtamaActivity.class);
                            startActivity(masukIntent);
                            finish();
                        } else {
                            if(uSandi.length() < 6){
                                edLoginSandi.setError("Sandi Kurang Dari 6");
                            } else {
                                Toast.makeText(LoginActivity.this, "Gagal Melakukan Login", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}
