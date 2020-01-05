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
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.eventHolder> {
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
    public void onBindViewHolder(@NonNull eventHolder holder, int position) {

        String uid = eventModelList.get(position).getuId();
        String nama = eventModelList.get(position).getuNama();
        String email = eventModelList.get(position).getuEmail();
        String nim = eventModelList.get(position).getuNim();
        String dp = eventModelList.get(position).getuImage();
        String pId = eventModelList.get(position).geteId();
        String pJudul = eventModelList.get(position).geteJudul();
        String pDescr = eventModelList.get(position).geteDeskripsi();
        String pImage = eventModelList.get(position).geteImage();
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

    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
    }

    class eventHolder extends RecyclerView.ViewHolder {
        ImageView eImageView, uImageView;
        TextView uNama, uNim, uTimes, eJudul, eDeskripsi;
        LinearLayout btnMore;

        public eventHolder(@NonNull View itemView){
            super(itemView);
            eImageView = itemView.findViewById(R.id.eImagePoster);
            eJudul = itemView.findViewById(R.id.eJudulView);
            eDeskripsi = itemView.findViewById(R.id.eDeskripsiView);
        }
    }
}
