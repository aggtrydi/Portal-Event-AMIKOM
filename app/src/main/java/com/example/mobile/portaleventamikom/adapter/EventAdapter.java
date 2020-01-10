package com.example.mobile.portaleventamikom.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.portaleventamikom.R;
import com.example.mobile.portaleventamikom.model.EventModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.List;


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
        DatabaseReference drUser, drEvent;

        ImageView eImageView, eFav, eLike;
        TextView eJudul, eDeskripsi;
        LinearLayout btnMore;


        String userID;

        public eventHolder(@NonNull View itemView){
            super(itemView);
            eImageView = itemView.findViewById(R.id.eImagePoster);
            eJudul = itemView.findViewById(R.id.eJudulView);
            eDeskripsi = itemView.findViewById(R.id.eDeskripsiView);

            eFav = itemView.findViewById(R.id.imgFavoritEvent);
//            eLike = itemView.findViewById(R.id.imgLikeEvent);

            uAuth = FirebaseAuth.getInstance();
            user = uAuth.getCurrentUser();
            db = FirebaseDatabase.getInstance();
            drUser = db.getReference("User");
            drEvent = db.getReference("Event");


            FirebaseUser user = uAuth.getCurrentUser();
            userID = user.getUid();
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
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("User").child(uIdFav).child("Favorite");
        dbref.child(timestamp).setValue(hashMap);
    }
}
