package com.example.mobile.portaleventamikom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.model.EventAndaModel;
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

public class EventAndaAdapter extends RecyclerView.Adapter<EventAndaAdapter.eventAndaHolder> {
    Context ctx;
    List<EventAndaModel> eventAndaModelList;

    public EventAndaAdapter( Context ctx, List<EventAndaModel> eventAndaModelList) {
        this.ctx = ctx;
        this.eventAndaModelList = eventAndaModelList;
    }

    @NonNull
    @Override
    public eventAndaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_event_anda,parent, false);
        return new eventAndaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final eventAndaHolder holder, int position) {

        final String pId = eventAndaModelList.get(position).geteId();
        final String pJudul = eventAndaModelList.get(position).geteJudul();
        final String pDescr = eventAndaModelList.get(position).geteDeskripsi();
        final String pImage = eventAndaModelList.get(position).geteImage();
        final String userId = eventAndaModelList.get(position).getuId();
        final String uId = holder.userID;
        //Set Data

        holder.eJudul.setText(pJudul);
        holder.eDeskripsi.setText(pDescr);

        try {
            Picasso.get().load(pImage).into(holder.eImageView);
        } catch (Exception e) {

        }
        holder.hapusEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pIdE = pId.trim();
                deleteEvent(uId,userId, pIdE);
                Toast.makeText(v.getContext(),"Event Anda telah Terhapus", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private  void deleteEvent(String uId,String userId, String pIdE){
        if (uId.equals(userId)){
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Event");
            Query query = dbRef.orderByChild("eId").equalTo(pIdE);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        ds.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

//            DatabaseReference dbRefFav = FirebaseDatabase.getInstance().getReference("User")
//                    .child(uId).child("Favorit").child(pIdE);
//            dbRefFav.setValue(null);
        }
    }


    @Override
    public int getItemCount() {
        return eventAndaModelList.size();
    }

    class eventAndaHolder extends RecyclerView.ViewHolder{
        FirebaseAuth uAuth;
        FirebaseUser user;
        FirebaseDatabase db;
        DatabaseReference dbRefUser, dbRefEvent;

        Button hapusEvent;
        ImageView eImageView, eFav, eLike;
        TextView eJudul, eDeskripsi;

        String userID, uId;
        public eventAndaHolder(@NonNull View itemView){
            super(itemView);
            eImageView = itemView.findViewById(R.id.eImagePoster);
            eJudul = itemView.findViewById(R.id.eJudulView);
            eDeskripsi = itemView.findViewById(R.id.eDeskripsiView);

            hapusEvent = itemView.findViewById(R.id.btnHapusEvent);

            uAuth = FirebaseAuth.getInstance();
            user = uAuth.getCurrentUser();
            userID = user.getUid();
            uId = userID.trim();
            db = FirebaseDatabase.getInstance();

            dbRefEvent = db.getReference("Event");
        }
    }
}
