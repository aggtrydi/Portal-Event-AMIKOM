package com.example.mobile.portaleventamikom.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobile.portaleventamikom.Model.Admin;
import com.example.mobile.portaleventamikom.Model.User;
import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.fragment.AdminFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    //    Deklasrasi
    FirebaseDatabase database;
    DatabaseReference dbrefUser, dbrefAdmin;
    EditText edtxtUsername, edtxtPassword;

    User user;
    Admin admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Button btnMasuk = (Button)findViewById(R.id.btnLogin);
        Button btnDaftar = (Button)findViewById(R.id.btnDaftar);

        database = FirebaseDatabase.getInstance();
        dbrefUser = database.getReference("User" );
        dbrefAdmin = database.getReference("Admin");

        edtxtUsername = (EditText)findViewById(R.id.edtxtUsername);
        edtxtPassword = (EditText)findViewById(R.id.edtxtPassword);

        user =  new User();
        admin = new Admin();

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prosesAdmin(
                        edtxtUsername.getText().toString(),
                        edtxtPassword.getText().toString()
                ) == true) {

                } else if (prosesMasuk(
                        edtxtUsername.getText().toString(),
                        edtxtPassword.getText().toString())
                        ==true){
                }

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

    //Removable
    //change void
    private boolean prosesAdmin(final String username,final String password) {
        dbrefAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(username).exists()) {
                    Admin login = dataSnapshot.child(username).getValue(Admin.class);
                    if (login.getPassword().equals(password)) {
                        Toast.makeText(LoginActivity.this, "Sukses Login Admin", Toast.LENGTH_SHORT).show();
                        Intent masukAdminIntent = new Intent(LoginActivity.this, AdminMenuActivity.class);
                        startActivity(masukAdminIntent);
                    } else
                        Toast.makeText(LoginActivity.this, "Sandi Admin Salah", Toast.LENGTH_SHORT).show();
                }
                    //Toast.makeText(LoginActivity.this, "Username Admin Salah",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return false;
    }

    //change void
    private boolean prosesMasuk(final String pengguna, final String sandi){
        dbrefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(pengguna).exists()){
                    User login = dataSnapshot.child(pengguna).getValue(User.class);
                    if(login.getSandi().equals(sandi)){
                        Toast.makeText(LoginActivity.this, "Sukses Login",Toast.LENGTH_SHORT).show();
                        Intent masukIntent =  new Intent(LoginActivity.this, MenuActivity.class);
                       startActivity(masukIntent);
                    } else
                        Toast.makeText(LoginActivity.this,"Sandi Salah",Toast.LENGTH_SHORT).show();
                }
                   // Toast.makeText(LoginActivity.this, "Username Salah",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this,"Jaringan Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }


}
