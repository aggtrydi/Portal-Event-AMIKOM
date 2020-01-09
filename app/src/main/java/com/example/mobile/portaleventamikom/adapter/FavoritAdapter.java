package com.example.mobile.portaleventamikom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.model.EventModel;
import com.example.mobile.portaleventamikom.model.FavoritModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.favoritHolder> {
    Context ctx;
    List<FavoritModel> favoritModelList;

    class favoritHolder extends RecyclerView.ViewHolder {
        //firebase user
        FirebaseAuth uAuth;
        FirebaseUser user;
        //firebase database
        Query queryRefUser, queryRefEvent;
        FirebaseDatabase db;
        DatabaseReference drUser, drEvent, drFavorit;

        ImageView eImageView;
        TextView txtEJudul, txtEDeskripsi;

        String userID, eventID, favID, favIdUser, favIdEvent;

        public favoritHolder(@NonNull View itemView) {
            super(itemView);
            eImageView = itemView.findViewById(R.id.imgFavoritView);
            txtEJudul = itemView.findViewById(R.id.txtFavoritViewJudul);
            txtEDeskripsi= itemView.findViewById(R.id.txtFavoritViewDeskripsi);

            uAuth = FirebaseAuth.getInstance();
            user = uAuth.getCurrentUser();

            db = FirebaseDatabase.getInstance();
            drUser = db.getReference("User");
            drEvent = db.getReference("Event");
            drFavorit = db.getReference("Favorit");
            //get from USer
            FirebaseUser user = uAuth.getCurrentUser();
            userID = user.getUid();

            //get From database Event
            drEvent.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        String eventId = "" + ds.child("eId").getValue();
                        eventID = eventId;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            //get from database favorite
            queryRefUser = drFavorit.orderByChild("uId").equalTo(userID);
            queryRefUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        String userId = "" + ds.child("uId").getValue();
                        String favId = "" + ds.child("favId").getValue();
                        String eventId = "" + ds.child("eId").getValue();
                        String eventJudulFav = "" + ds.child("eJudulFav").getValue();
                        String eventDesFav = "" + ds.child("eDesFav").getValue();
                        String eventImageFav =""+ ds.child("eImagePoster").getValue();

                        favIdUser = userId;
                        favIdEvent = eventId;
                        favID = favId;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            //view favorite by uId and eId


        }
    }
    public FavoritAdapter(Context ctx, List<FavoritModel> favoritModelList) {
        this.ctx = ctx;
        this.favoritModelList = favoritModelList;
    }

    @NonNull
    @Override
    public favoritHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_event_favorit,parent, false);
        return new favoritHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final favoritHolder holder, int position) {
//        String uId = favoritModelList.get(position).getuId();
        String uId = holder.userID;
        String eId = holder.eventID;
        //event
        String eJudul = favoritModelList.get(position).geteJudulFav();
        String eDescr = favoritModelList.get(position).geteDesFav();
        String eImage = favoritModelList.get(position).geteImagePoster();
        String fid = favoritModelList.get(position).getfId();

        holder.txtEJudul.setText(eJudul);
        holder.txtEDeskripsi.setText(eDescr);
        try {
            Picasso.get().load(eImage).into(holder.eImageView);
        } catch (Exception e) { }
    }
//        String eId = favoritModelList.get(position).geteId();

    @Override
    public int getItemCount() {
        return favoritModelList.size();
    }


}
