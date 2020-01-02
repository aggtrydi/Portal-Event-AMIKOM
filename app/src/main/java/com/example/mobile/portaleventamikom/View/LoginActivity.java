package com.example.mobile.portaleventamikom.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobile.portaleventamikom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    //    Deklasrasi
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    DatabaseReference dbref;
    EditText edtxtUsername, edtxtPassword;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button btnMasuk = (Button)findViewById(R.id.btnLogin);
        Button btnDaftar = (Button)findViewById(R.id.btnDaftar);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        edtxtUsername = (EditText)findViewById(R.id.edtxtUsername);
        edtxtPassword = (EditText)findViewById(R.id.edtxtPassword);

        pd = new ProgressDialog(this);



        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtxtUsername.getText().toString();
                String sandi = edtxtPassword.getText().toString().trim();
                masukUser(email, sandi);
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

    private void masukUser(String email, String sandi) {
        pd.show();

        mAuth.signInWithEmailAndPassword(email, sandi)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            pd.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent masukIntent =  new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(masukIntent);

                            finish();

                        } else {

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            pd.dismiss();
            }
        });
    }
}
