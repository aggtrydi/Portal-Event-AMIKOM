package com.example.mobile.portaleventamikom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobile.portaleventamikom.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    //    Deklasrasi
    FirebaseDatabase database;
    DatabaseReference dbref;
    EditText edtxtUsername, edtxtPassword;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        setContentView(R.layout.activity_main);
        Button btnMasuk = (Button)findViewById(R.id.btnLogin);
        Button btnDaftar = (Button)findViewById(R.id.btnDaftar);

        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("User");

        edtxtUsername = (EditText)findViewById(R.id.edtxtUsername);
        edtxtPassword = (EditText)findViewById(R.id.edtxtPassword);

        user =  new User();

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesMasuk(
                        edtxtUsername.getText().toString(),
                        edtxtPassword.getText().toString()
                );
            }
        });
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void prosesMasuk(final String pengguna, final String sandi){
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(pengguna).exists()){
                    User login = dataSnapshot.child(pengguna).getValue(User.class);
                    if(login.getSandi().equals(sandi)){
                        Toast.makeText(LoginActivity.this, "Sukses Login",Toast.LENGTH_SHORT).show();
                        Intent daftarIntent =  new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(daftarIntent);
                    } else
                        Toast.makeText(LoginActivity.this,"Sandi Salah",Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(LoginActivity.this, "Username Salah",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this,"Jaringan Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
