package com.example.mobile.portaleventamikom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.mobile.portaleventamikom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private SharedPreferences sharedPreferences;
    FirebaseDatabase database;
    FirebaseAuth uAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadPreference();
            }
        },SPLASH_TIME_OUT);
    }

    private void loadPreference() {
        database = FirebaseDatabase.getInstance();
        uAuth = FirebaseAuth.getInstance();
        FirebaseUser user = uAuth.getCurrentUser();
            if (user != null){
                Intent UtamaIntent = new Intent(SplashActivity.this, UtamaActivity.class);
                    startActivity(UtamaIntent);
                    finish();
            } else {
                Intent LoginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
                finish();
            }
    }
}
