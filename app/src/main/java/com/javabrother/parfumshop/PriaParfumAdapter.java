package com.javabrother.parfumshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javabrother.parfumshop.model.PriaParfumList;

import java.util.ArrayList;

public class PriaParfumAdapter extends RecyclerView.Adapter<PriaParfumAdapter.myViewholder> {

    Context context;
    ArrayList<PriaParfumList> lists;

    public PriaParfumAdapter(Context context, ArrayList<PriaParfumList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public PriaParfumAdapter.myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_pria_parfum_list, parent,false);
        final PriaParfumAdapter.myViewholder holder = new PriaParfumAdapter.myViewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewholder holder, int position) {
        final PriaParfumList priaParfumList = lists.get(position);

        holder.nama.setText(priaParfumList.getNamaParfum());
        holder.harga.setText(priaParfumList.getHargaParfum());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailParfumActivity.class);
                intent.putExtra("nama", priaParfumList.getNamaParfum());
                intent.putExtra("harga", priaParfumList.getHargaParfum());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class myViewholder extends RecyclerView.ViewHolder {
        TextView nama, harga;

        public myViewholder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tV_NamaParfumPria);
            harga = itemView.findViewById(R.id.tV_HargaParfumPria);
        }
    }
}
