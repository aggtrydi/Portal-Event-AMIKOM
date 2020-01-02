package com.example.mobile.portaleventamikom.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile.portaleventamikom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfilActivity extends AppCompatActivity {

    FirebaseAuth uAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference dbref;

    StorageReference strgRef;

    String strgPath = "User_Profil_cover_Image/";

    ImageView imgViewUser, imgviewCover;
    TextView txtViewNama, txtViewNim, txtViewProdi;

    Button btnLogoutUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        uAuth = FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("user");
        strgRef = FirebaseStorage.getInstance().getReference();

        imgViewUser = findViewById(R.id.imgViewProfil);
        imgviewCover = findViewById(R.id.imgViewCover);
        txtViewNama = findViewById(R.id.txtViewNama);
        txtViewNim = findViewById(R.id.txtViewNim);
        txtViewProdi = findViewById(R.id.txtViewProdi);

        btnLogoutUser = findViewById(R.id.btnLogoutUser);


        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Query query = dbref.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String nama = "" + ds.child("nama").getValue();
                    String nim = "" + ds.child("nim").getValue();
                    String jurusan = ""+ds.child("jurusan").getValue();
                    String image = ""+ds.child("image").getValue();

                    txtViewNama.setText(nama);
                    txtViewNim.setText(nim);
                    txtViewProdi.setText(jurusan);
                    try {
                        Picasso.get().load(image).into(imgViewUser);
                    } catch (Exception e){
                        Picasso.get().load(R.drawable.ic_account_circle_black_24dp).into(imgViewUser);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnLogoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uAuth.signOut();
                checStatusUser();
            }
        });

    }

    private void checStatusUser() {

        FirebaseUser user = uAuth.getCurrentUser();
        if(user!=null){

        }else {
            Intent logoutUser = new Intent(ProfilActivity.this, LoginActivity.class);
            startActivity(logoutUser);
            finish();
        }
    }
}
