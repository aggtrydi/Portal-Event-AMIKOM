package com.example.mobile.portaleventamikom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.portaleventamikom.Filter.EventFilter;
import com.example.mobile.portaleventamikom.Model.ModelPostingan;
import com.example.mobile.portaleventamikom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyHolder> {

    Context ctx;
    List<ModelPostingan> postingList;

    public EventAdapter(Context ctx, List<ModelPostingan> postingList) {
        this.ctx = ctx;
        this.postingList = postingList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.item_event_dashboard,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //getData
        String uid = postingList.get(position).getUid();
        String nama = postingList.get(position).getNama();
        String email = postingList.get(position).getEmail();
        String nim = postingList.get(position).getNim();
        String dp = postingList.get(position).getDp();
        String pId = postingList.get(position).getpId();
        String pJudul = postingList.get(position).getpJudul();
        String pDescr = postingList.get(position).getpDescr();
        String pImage = postingList.get(position).getPImage();
        String pTime = postingList.get(position).getpTime();


        //Set Data

        holder.uTitle.setText(pJudul);
        holder.uDeskripsi.setText(pDescr);


        try {
            Picasso.get().load(pImage).into(holder.uImgPostingan);
        } catch (Exception e) {

        }

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return postingList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{


        ImageView uPicture,uImgPostingan;
        TextView uNama,uNim,uTime,uTitle,uDeskripsi;
        LinearLayout btnMore;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //uPicture = itemView.findViewById(R.id.uPicture);
            uImgPostingan = itemView.findViewById(R.id.imgPoster);
            uTitle = itemView.findViewById(R.id.txtItemJudul);
            uDeskripsi = itemView.findViewById(R.id.txtItemDeskripsi);
            btnMore = itemView.findViewById(R.id.btnGroupPostingan);


        }
    }
}
