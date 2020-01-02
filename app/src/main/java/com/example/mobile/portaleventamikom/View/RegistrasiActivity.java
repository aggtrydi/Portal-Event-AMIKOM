package com.example.mobile.portaleventamikom.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobile.portaleventamikom.Model.Mahasiswa;
import com.example.mobile.portaleventamikom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrasiActivity extends AppCompatActivity {

//    Deklasrasi
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference dbref;
    EditText regName,regNIM,regJurusan,regEmail, regPengguna, regSandi;

    Button btnReg;
    Mahasiswa mhs;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registrasi_menu);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("User");

        regName = (EditText)findViewById(R.id.reg_name);
        regNIM =  (EditText)findViewById(R.id.reg_nim);
        regJurusan = (EditText)findViewById(R.id.reg_jurusan);
        regEmail = (EditText)findViewById(R.id.reg_email);
        regPengguna =(EditText)findViewById(R.id.reg_pengguna);
        regSandi = (EditText) findViewById(R.id.reg_sandi);

        mhs = new Mahasiswa();
        pd =  new ProgressDialog(this);

        Button btnReg = (Button)findViewById(R.id.btnReg);

        btnReg.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          String nama = regName.getText().toString().trim();
                                          String nim = regNIM.getText().toString().trim();
                                          String jurusan = regJurusan.getText().toString().trim();
                                          String email = regEmail.getText().toString().trim();
                                          String pengguna = regPengguna.getText().toString().trim();
                                          String sandi = regSandi.getText().toString().trim();
                                          if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                              regEmail.setText("salah email");
                                              regEmail.setFocusable(true);
                                          } else if (sandi.length() < 5) {
                                              regSandi.setText("Sandi terlalu Pendek");
                                              regSandi.setFocusable(true);
                                          } else {
                                              registerUser(nama, nim, jurusan, email, pengguna, sandi);
                                          }
                                      }
            });
    }

    private void registerUser(final String nama, final String nim, final String jurusan, String email, final String pengguna, String sandi) {

        pd.show();
        auth.createUserWithEmailAndPassword(email, sandi)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        FirebaseUser user = auth.getCurrentUser();

                        String email = user.getEmail();
                        String uid = user.getUid();

                        mhs.setNama(regName.getText().toString());
                        mhs.setNim(regNIM.getText().toString());
                        mhs.setJurusan(regJurusan.getText().toString());

                        HashMap<Object, String > hashMap =  new HashMap<>();

                        hashMap.put("uid",uid);
                        hashMap.put("nama",nama);
                        hashMap.put("nim", nim);
                        hashMap.put("jurusan", jurusan);
                        hashMap.put("email", email);
                        hashMap.put("pengguna", pengguna);
                        hashMap.put("image", "");

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dbref = database.getReference("user");
                        dbref.child(uid).setValue(hashMap);

                        if(task.isSuccessful()){
                            pd.dismiss();
                        } else {
                            pd.dismiss();
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


//        btnReg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                user.setNama(regName.getText().toString());
//                user.setNim(regNIM.getText().toString());
//                user.setJurusan(regJurusan.getText().toString());
//                user.setEmail(regEmail.getText().toString());
//                user.setPengguna(regPengguna.getText().toString());
//                user.setSandi(regSandi.getText().toString());
//
//                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.child(user.getPengguna().toString()).exists()){
//                            Toast.makeText(RegistrasiActivity.this,"Pengguna sudah ada ", Toast.LENGTH_SHORT).show();
//                        } else {
//                           // dbref.push().setValue(user);
//                            dbref.child(user.getPengguna().toString()).setValue(user);
//                            Toast.makeText(RegistrasiActivity.this,"Anda Sukses Daftar", Toast.LENGTH_SHORT).show();
//                            Intent regIntent = new Intent(RegistrasiActivity.this, LoginActivity.class);
//                            startActivity(regIntent);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });


