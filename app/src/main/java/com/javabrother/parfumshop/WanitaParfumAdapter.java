package com.javabrother.parfumshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WanitaParfumAdapter extends RecyclerView.Adapter<WanitaParfumAdapter.myViewholder> {

    Context context;
    ArrayList<WanitaParfumList> lists;

    public WanitaParfumAdapter(Context context, ArrayList<WanitaParfumList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public WanitaParfumAdapter.myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_wanita_parfum_list, parent,false);
        final WanitaParfumAdapter.myViewholder holder = new WanitaParfumAdapter.myViewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WanitaParfumAdapter.myViewholder holder, int position) {
        final WanitaParfumList wanitaParfumList = lists.get(position);

        holder.nama.setText(wanitaParfumList.getNama());
        holder.harga.setText(wanitaParfumList.getHarga());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class myViewholder extends RecyclerView.ViewHolder {
        TextView nama, harga;

        public myViewholder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tV_NamaParfumWanita);
            harga = itemView.findViewById(R.id.tV_HargaParfumWanita);
        }
    }
}
