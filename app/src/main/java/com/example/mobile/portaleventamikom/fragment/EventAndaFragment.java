package com.example.mobile.portaleventamikom.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.adapter.EventAdapter;
import com.example.mobile.portaleventamikom.adapter.EventAndaAdapter;
import com.example.mobile.portaleventamikom.model.EventAndaModel;
import com.example.mobile.portaleventamikom.model.EventModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class EventAndaFragment extends Fragment {

    FirebaseAuth uAuth;
    FirebaseUser user;
    RecyclerView rcyEventAnda;
    EventAndaModel eventAndaModel;
    List<EventAndaModel> eventAndaList;
    EventAndaAdapter eventAndaAdapter;
    String userID;



    public EventAndaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewEventAnda = inflater.inflate(R.layout.fragment_event_anda, container, false);
        setHasOptionsMenu(true);

        uAuth= FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();
        userID = user.getUid();

        rcyEventAnda = viewEventAnda.findViewById(R.id.rcyEventAnda);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        rcyEventAnda.setLayoutManager(linearLayoutManager);

        eventAndaList = new ArrayList<>();
        loadEventAnda(userID);
        return viewEventAnda;
    }

    private void loadEventAnda(String userID) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Event");
        dbRef.orderByChild("uId").equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventAndaList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    EventAndaModel eventAndaModel = ds.getValue(EventAndaModel.class);
                    eventAndaList.add(eventAndaModel);
                    eventAndaAdapter = new EventAndaAdapter(getActivity(), eventAndaList);
                    rcyEventAnda.setAdapter(eventAndaAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
