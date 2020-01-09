package com.example.mobile.portaleventamikom.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.adapter.FavoritAdapter;
import com.example.mobile.portaleventamikom.model.FavoritModel;
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
public class FavoritFragment extends Fragment {

    FirebaseAuth uAuth;
    FirebaseUser  user;
    RecyclerView rcyFavorit;
    FavoritModel favoritModel;
    List<FavoritModel> favList;
    FavoritAdapter favoritAdapter;

    String userID;

    public FavoritFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFav = inflater.inflate(R.layout.fragment_favorit, container, false);
        setHasOptionsMenu(true);

        uAuth = FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();
        userID = user.getUid();
        rcyFavorit = viewFav.findViewById(R.id.rcyFavorit);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        rcyFavorit.setLayoutManager(linearLayoutManager);

        favList = new ArrayList<>();
        loadFav();
        return viewFav;

    }

    private void loadFav() {
        DatabaseReference dbRefFav = FirebaseDatabase.getInstance().getReference("User").child(userID.trim()).child("Favorite");
        dbRefFav.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    FavoritModel favoritModel = ds.getValue(FavoritModel.class);
                    favList.add(favoritModel);
                    favoritAdapter = new FavoritAdapter(getActivity(), favList);
                    rcyFavorit.setAdapter(favoritAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
