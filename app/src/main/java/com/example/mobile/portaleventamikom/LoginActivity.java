package com.example.mobile.portaleventamikom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        setContentView(R.layout.activity_main);
        Button btnMasuk = (Button)findViewById(R.id.btnLogin);
        Button btnDaftar = (Button)findViewById(R.id.btnDaftar);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masukIntent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(masukIntent);
            }
        });
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftarIntent =  new Intent(LoginActivity.this, RegistrasiActivity.class);
                startActivity(daftarIntent);
            }
        });


    }

}
