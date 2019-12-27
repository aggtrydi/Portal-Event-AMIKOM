package com.example.mobile.portaleventamikom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobile.portaleventamikom.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrasiActivity extends AppCompatActivity {

//    Deklasrasi
    FirebaseDatabase database;
    DatabaseReference dbref;
    EditText regName,regNIM,regJurusan,regEmail, regPengguna, regSandi;

    Button btnReg;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        setContentView(R.layout.activity_registrasi_menu);

        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("User");

        regName = (EditText)findViewById(R.id.reg_name);
        regNIM =  (EditText)findViewById(R.id.reg_nim);
        regJurusan = (EditText)findViewById(R.id.reg_jurusan);
        regEmail = (EditText)findViewById(R.id.reg_email);
        regPengguna =(EditText)findViewById(R.id.reg_pengguna);
        regSandi = (EditText) findViewById(R.id.reg_sandi);

        user = new User();

        Button btnReg = (Button)findViewById(R.id.btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setNama(regName.getText().toString());
                user.setNim(regNIM.getText().toString());
                user.setJurusan(regJurusan.getText().toString());
                user.setEmail(regEmail.getText().toString());
                user.setPengguna(regPengguna.getText().toString());
                user.setSandi(regSandi.getText().toString());

                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getPengguna().toString()).exists()){
                            Toast.makeText(RegistrasiActivity.this,"Pengguna sudah ada ", Toast.LENGTH_SHORT).show();
                        } else {
                            dbref.child(user.getPengguna().toString()).setValue(user);
                            Toast.makeText(RegistrasiActivity.this,"Anda Sukses Daftar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent regIntent = new Intent(RegistrasiActivity.this, LoginActivity.class);
                startActivity(regIntent);
            }
        });
    }
}
