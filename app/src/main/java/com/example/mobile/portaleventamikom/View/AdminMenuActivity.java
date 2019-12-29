package com.example.mobile.portaleventamikom.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.fragment.AdminFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMenuActivity extends AppCompatActivity {

    BottomNavigationView bnvHome;
    AdminFragment adminFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        adminFragment = new AdminFragment();

        changeFragment(adminFragment);
        bnvHome =  (BottomNavigationView) findViewById(R.id.bnvMain);

        bnvHome.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menuDashboard:
                        changeFragment(adminFragment);
                        break;
                }

                return false;
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameMain, fragment)
                .commit();
    }
}
