package com.example.mobile.portaleventamikom.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.adapter.KomentarAdapter;
import com.example.mobile.portaleventamikom.model.KomentarModel;

import java.util.ArrayList;
import java.util.List;

public class CommentEventRecycleView extends AppCompatActivity {
    RecyclerView rcyKomentar;
    KomentarModel komentarModel;
    List<KomentarModel> komentarModelList;
    KomentarAdapter komentarAdapter;

    public CommentEventRecycleView(){

    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_event_beranda);
        rcyKomentar = findViewById(R.id.rcyKomentar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        rcyKomentar.setLayoutManager(linearLayoutManager);

        komentarModelList = new ArrayList<>();
    }

}
