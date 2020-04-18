package com.javabrother.parfumshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javabrother.parfumshop.model.KeranjangList;

import java.util.ArrayList;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.myViewholder> {

    Context context;
    ArrayList<KeranjangList> lists;
    int TotalHarga= 0;

    public KeranjangAdapter(Context context, ArrayList<KeranjangList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_keranjang_list, parent,false);
        final KeranjangAdapter.myViewholder holder = new KeranjangAdapter.myViewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewholder holder, int position) {
        final KeranjangList priaParfumList = lists.get(position);
        holder.nama.setText(priaParfumList.getNama());
        holder.harga.setText(priaParfumList.getHarga());
        holder.jumlah.setText(priaParfumList.getJumlah());

        TotalHarga = TotalHarga + (Integer.valueOf(priaParfumList.getHarga()) * Integer.valueOf(priaParfumList.getJumlah()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ParfumPriaStrukActivity.class);
                intent.putExtra("nama", priaParfumList.getNama());
                intent.putExtra("harga", priaParfumList.getHarga());
                intent.putExtra("jumlah", priaParfumList.getJumlah());
                intent.putExtra("Total", String.valueOf(TotalHarga));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class myViewholder extends RecyclerView.ViewHolder {
        TextView nama, harga, jumlah, total;

        public myViewholder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tV_KeranjangNamaParfum);
            harga = itemView.findViewById(R.id.tV_KeranjangHargaParfum);
            jumlah = itemView.findViewById(R.id.tV_KeranjangJumlahParfum);
        }
    }
}
