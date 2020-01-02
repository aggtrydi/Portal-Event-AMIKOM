package com.example.mobile.portaleventamikom.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mobile.portaleventamikom.Adapter.EventAdapter;
import com.example.mobile.portaleventamikom.Filter.EventFilter;
import com.example.mobile.portaleventamikom.Model.ModelPostingan;
import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.View.AddEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    FirebaseAuth uAuth;
    RecyclerView rcyView;
    List<ModelPostingan> postinganList;
    EventAdapter eventAdapter;
    FloatingActionButton fabAddEvent;




    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_dashboard = inflater.inflate(R.layout.fragment_dashboard, container, false);
        setHasOptionsMenu(true);

        uAuth = FirebaseAuth.getInstance();
        rcyView = view_dashboard.findViewById(R.id.rcyDashboard);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        rcyView.setLayoutManager(linearLayoutManager);


        fabAddEvent = getActivity().findViewById(R.id.fabAddEvent);

        postinganList = new ArrayList<>();
        loadPost();

        fabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEventDialog();
            }
        });

        return view_dashboard;
    }

    private void showAddEventDialog() {
        Intent addEventIntent = new Intent(getActivity(), AddEvent.class);
        startActivity(addEventIntent);
    }

    private void loadPost() {
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Postingan");
        //mengambil semua data dari dref
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postinganList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ModelPostingan modelPostingan = ds.getValue(ModelPostingan.class);
                    postinganList.add(modelPostingan);

                    eventAdapter = new EventAdapter(getActivity(),postinganList);

                    rcyView.setAdapter(eventAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),""+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
