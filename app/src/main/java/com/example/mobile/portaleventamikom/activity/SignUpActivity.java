package com.example.mobile.portaleventamikom.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth uAuth;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    EditText edDaftarNama, edDaftarNim, edDaftarJurusan, edDaftarEmail, edDaftarSandi;
    Button btnDaftar;
    UserModel uModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        uAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        edDaftarNama = (EditText)findViewById(R.id.edDaftarNama);
        edDaftarNim = (EditText)findViewById(R.id.edDaftarNim);
        edDaftarJurusan = (EditText)findViewById(R.id.edDaftarJurusan);
        edDaftarEmail = (EditText)findViewById(R.id.edDaftarEmail);
        edDaftarSandi = (EditText)findViewById(R.id.edDaftarSandi);

        uModel = new UserModel();

        btnDaftar = (Button)findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = edDaftarNama.getText().toString().trim();
                String nim = edDaftarNim.getText().toString().trim();
                String jurusan = edDaftarJurusan.getText().toString().trim();
                String email = edDaftarEmail.getText().toString().trim();
                String sandi = edDaftarSandi.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edDaftarEmail.setText("Salah Email");
                    edDaftarEmail.setFocusable(true);
                    Toast.makeText(SignUpActivity.this, "Masukkan Email dengan Benar", Toast.LENGTH_SHORT).show();
                } else if (sandi.length() < 5) {
                    edDaftarSandi.setText("Sandi Terlalu Pendek");
                    edDaftarSandi.setFocusable(true);
                    Toast.makeText(SignUpActivity.this, "Masukkan Sandi dengan Benar", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(nama, nim, jurusan, email, sandi);
                    Toast.makeText(SignUpActivity.this, "Anda Berhasil SignUp", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(i);
                }

//                newActivity();
            }
        });
    }

    private void registerUser(final String nama, final String nim, final String jurusan, String email, String sandi) {

        uAuth.createUserWithEmailAndPassword(email, sandi)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        FirebaseUser user = uAuth.getCurrentUser();

                        String email = user.getEmail();
                        String uid = user.getUid();

                        uModel.setuNama(edDaftarNama.getText().toString());
                        uModel.setuNim(edDaftarNim.getText().toString());
                        uModel.setuJurusan(edDaftarJurusan.getText().toString());

                        HashMap<Object, String > hashMap =  new HashMap<>();

                        hashMap.put("uid",uid);
                        hashMap.put("nama",nama);
                        hashMap.put("nim", nim);
                        hashMap.put("jurusan", jurusan);
                        hashMap.put("email", email);
                        hashMap.put("image", "");

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dbref = database.getReference("User");
                        dbref.child(uid).setValue(hashMap);

                        if(task.isSuccessful()){

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
