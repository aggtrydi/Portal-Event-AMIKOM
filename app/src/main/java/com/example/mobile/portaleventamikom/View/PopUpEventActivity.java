package com.example.mobile.portaleventamikom.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.mobile.portaleventamikom.Adapter.EventAdapter;
import com.example.mobile.portaleventamikom.Model.ModelPostingan;
import com.example.mobile.portaleventamikom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PopUpEventActivity extends AppCompatActivity {

    FirebaseAuth uAuth;
    LinearLayout linearLayout;
    List<ModelPostingan> postinganList;
    EventAdapter eventAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_event);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9), (int)(height*.8));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        uAuth = FirebaseAuth.getInstance();
        linearLayout = findViewById(R.id.groupPopUpEvent);
        postinganList = new ArrayList<>();
        laodPost();

    }

    private void laodPost() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Postingan");


    }

}
