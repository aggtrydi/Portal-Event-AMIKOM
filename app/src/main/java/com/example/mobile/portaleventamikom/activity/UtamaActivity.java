package com.example.mobile.portaleventamikom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.fragment.BerandaFragment;
import com.example.mobile.portaleventamikom.fragment.FavoritFragment;
import com.example.mobile.portaleventamikom.fragment.ProfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UtamaActivity extends AppCompatActivity {
    BottomNavigationView bnvUtama;

    //fragment
    BerandaFragment berandaFragment;
    FavoritFragment favoritFragment;
    ProfilFragment profilFragment;

    Button btnAddEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        berandaFragment = new BerandaFragment();
        favoritFragment = new FavoritFragment();
        profilFragment = new ProfilFragment();

        changeFragment(berandaFragment);

        btnAddEvent = (Button)findViewById(R.id.btnAddEvent);
        bnvUtama = findViewById(R.id.bnvUtama);
        bnvUtama.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.bnvBeranda : changeFragment(berandaFragment);break;
                    case R.id.bnvFavorit : changeFragment(favoritFragment);break;
                    case R.id.bnvProfil : changeFragment(profilFragment);break;
                }
                return true;
            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UtamaActivity.this, AddEventActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameUtama, fragment).commit();
    }
}