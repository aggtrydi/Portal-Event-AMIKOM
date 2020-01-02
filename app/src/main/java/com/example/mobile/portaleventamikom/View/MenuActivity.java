package com.example.mobile.portaleventamikom.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.fragment.CommentFragment;
import com.example.mobile.portaleventamikom.fragment.DashboardFragment;
import com.example.mobile.portaleventamikom.fragment.FavoriteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView bnvHome;
    DashboardFragment dashboardFragment;
    FavoriteFragment favoriteFragment;
    CommentFragment commentFragment;

    FirebaseAuth uAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference dbref;

    StorageReference strgRef;

    String strgPath = "User_Profil_Cover_image/";

    ImageView imgViewUser;
    TextView txtViewUser;
//    Button  btnLogoutUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        dashboardFragment = new DashboardFragment();
        favoriteFragment = new FavoriteFragment();
        commentFragment = new CommentFragment();

        uAuth = FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("user");
        strgRef = FirebaseStorage.getInstance().getReference();


        imgViewUser = findViewById(R.id.imgViewUser);
        txtViewUser = findViewById(R.id.txtViewUser);
        //btnLogoutUser = findViewById(R.id.btnLogoutUser);

        changeFragment(dashboardFragment);
        bnvHome =  (BottomNavigationView) findViewById(R.id.bnvMain);



        //Menampilkan User
        Query query = dbref.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String nama = "" + ds.child("nama").getValue();
                    String image = ""+ds.child("image").getValue();

                    txtViewUser.setText(nama);
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

//        btnLogoutUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uAuth.signOut();
//                checkUserStatus();
//            }
//        });


        //Button Linear layout

        LinearLayout menu_profil = (LinearLayout)findViewById(R.id.groupUser);
        menu_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuProfil = new Intent(MenuActivity.this, ProfilActivity.class);
                startActivity(menuProfil);
            }
        });

        //Button View Menu
        bnvHome.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menuDashboard:
                        changeFragment(dashboardFragment);
                        break;
                    case R.id.menuFav:
                        changeFragment(favoriteFragment);
                        break;
                    case R.id.menuComment:
                        changeFragment(commentFragment);
                        break;
                }
                return true;
            }
        });
    }
    //change Fragment
    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameMain, fragment)
                .commit();
    }

//    private void checkUserStatus(){
//        FirebaseUser user = uAuth.getCurrentUser();
//        if (user!=null){
//
////            txtUser.setText(user.getEmail());
////            txtNama.setText(user.getDisplayName());
//
//        }else {
//            Intent i = new Intent(MenuActivity.this, LoginActivity.class);
//            startActivity(i);
//            finish();
//        }
//    }
}
