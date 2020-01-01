package com.example.mobile.portaleventamikom.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.portaleventamikom.Filter.EventFilter;
import com.example.mobile.portaleventamikom.R;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private ArrayList<EventFilter> arrayList;

    public EventAdapter(ArrayList<EventFilter> arrayList){
        this.arrayList = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView deskrispiEvent;
        public ImageView gambarEvent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            deskrispiEvent = itemView.findViewById(R.id.txtItemEvent);
            String  dEvent = deskrispiEvent.getText().toString();
            gambarEvent = itemView.findViewById(R.id.imgItemEvent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_dashboard, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String Deskripsi = arrayList.get(position).getDeskripsi();
        holder.deskrispiEvent.setText(Deskripsi);

        holder.gambarEvent.setImageResource(arrayList.get(position).getImageID());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    void setFilter(ArrayList<EventFilter>filterList){
        arrayList = new ArrayList<>();
        arrayList.addAll(filterList);
        notifyDataSetChanged();
    }


}
