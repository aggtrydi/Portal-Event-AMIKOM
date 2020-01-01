package com.example.mobile.portaleventamikom.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile.portaleventamikom.Adapter.EventAdapter;
import com.example.mobile.portaleventamikom.Filter.EventFilter;
import com.example.mobile.portaleventamikom.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private EventAdapter adapter;
    private ArrayList<EventFilter> arrayList;

    private String[] Deskripsi ={"event 1", "Event 2"};
    private int[] Gambar ={R.drawable.logo, R.drawable.logo};

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_dashboard = inflater.inflate(R.layout.fragment_dashboard, container, false);
        setHasOptionsMenu(true);
        arrayList = new ArrayList<>();
        RecyclerView recyclerView = view_dashboard.findViewById(R.id.rcyDashboard);
        daftarEvent();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new EventAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        return view_dashboard;
    }

    private void daftarEvent() {

        int count = 0;
        for (String deskripsi : Deskripsi){
            arrayList.add(new EventFilter(deskripsi, Gambar[count]));
            count++;
        }
    }

}
