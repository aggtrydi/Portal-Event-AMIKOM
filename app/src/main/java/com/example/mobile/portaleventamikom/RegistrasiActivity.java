package com.example.mobile.portaleventamikom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        setContentView(R.layout.activity_registrasi_menu);

        Button btnReg = (Button)findViewById(R.id.btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(RegistrasiActivity.this, LoginActivity.class);
                startActivity(regIntent);
            }
        });
    }
}
