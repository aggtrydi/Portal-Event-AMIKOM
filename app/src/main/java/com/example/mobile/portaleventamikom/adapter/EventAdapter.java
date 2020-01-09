package com.example.mobile.portaleventamikom.adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureSessionId;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.activity.AddEventActivity;
import com.example.mobile.portaleventamikom.activity.LoginActivity;
import com.example.mobile.portaleventamikom.model.EventModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.eventHolder> {


   //List RCV
    Context ctx;
    List<EventModel> eventModelList;

    public EventAdapter(Context ctx, List<EventModel> eventModelList){
        this.ctx = ctx;
        this.eventModelList = eventModelList;
    }

    @NonNull
    @Override
    public eventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_event_beranda,parent, false);
        return new eventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final eventHolder holder, int position) {

        String uid = eventModelList.get(position).getuId();
        String nama = eventModelList.get(position).getuNama();
        String email = eventModelList.get(position).getuEmail();
        String nim = eventModelList.get(position).getuNim();
        String dp = eventModelList.get(position).getuImage();
        final String pId = eventModelList.get(position).geteId();
        final String pJudul = eventModelList.get(position).geteJudul();
        final String pDescr = eventModelList.get(position).geteDeskripsi();
        final String pImage = eventModelList.get(position).geteImage();
        String pTime = eventModelList.get(position).geteTimes();

        //Set Data

        holder.eJudul.setText(pJudul);
        holder.eDeskripsi.setText(pDescr);

        try {
            Picasso.get().load(pImage).into(holder.eImageView);
        } catch (Exception e) {

        }
//        holder.btnMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent popUpIntent = new Intent(ctx.getApplicationContext(), PopUpEventActivity.class);
//                ctx.startActivity(popUpIntent);
//            }
//        });
        holder.eFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uIdFav = holder.userID.trim();
                String eIdFav = pId.toString().trim();
                String eJudulFav = pJudul.toString().trim();
                String eDesFav = pDescr.toString().trim();
                String eImageView = pImage.toString().trim();
                setFavorite(uIdFav, eIdFav, eJudulFav, eDesFav, eImageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
    }

    class eventHolder extends RecyclerView.ViewHolder {
        //firebase

        FirebaseAuth uAuth;
        FirebaseUser user;
        FirebaseDatabase db;
        DatabaseReference drUser, drEvent, drFavorit;
        Query queryRefUser;

        ImageView eImageView, eFav, eLike;
        TextView uTimes, eJudul, eDeskripsi;
        LinearLayout btnMore;


        String userID, favId, favIdUser, favIdEvent;

        public eventHolder(@NonNull View itemView){
            super(itemView);
            eImageView = itemView.findViewById(R.id.eImagePoster);
            eJudul = itemView.findViewById(R.id.eJudulView);
            eDeskripsi = itemView.findViewById(R.id.eDeskripsiView);

            eFav = itemView.findViewById(R.id.imgFavoritEvent);
            eLike = itemView.findViewById(R.id.imgLikeEvent);

            uAuth = FirebaseAuth.getInstance();
            user = uAuth.getCurrentUser();
            db = FirebaseDatabase.getInstance();
            drUser = db.getReference("User");
            drEvent = db.getReference("Event");
//            drFavorit = db.getReference("Favorit");

            FirebaseUser user = uAuth.getCurrentUser();
            userID = user.getUid();

//            drFavorit.child("Favorit").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot ds: dataSnapshot.getChildren()){
//                        String favoritId = "" + ds.child("fId").getValue();
//                        String favoritIdUser = "" + ds.child("uId").getValue();
//                        String favoritIdEvent = "" + ds.child("eId").getValue();
//                        favId = favoritId;
//                        favIdUser = favoritIdUser;
//                        favIdEvent = favoritIdEvent;
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
        }
    }

    private void setFavorite(String uIdFav, String eIdFav, String eJudulFav, String eDesFav, String eImageView) {

        final String timestamp = String.valueOf(System.currentTimeMillis());

        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("fId", timestamp);
        hashMap.put("uId", uIdFav);
        hashMap.put("eId", eIdFav);
        hashMap.put("eJudulFav", eJudulFav);
        hashMap.put("eDesFav", eDesFav);
        hashMap.put("eImagePoster",eImageView );

//        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Favorit");
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("User").child(uIdFav).child("Favorite");
        dbref.child(timestamp).setValue(hashMap);
    }
}
