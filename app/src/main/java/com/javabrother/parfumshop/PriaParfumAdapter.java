package com.javabrother.parfumshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

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
    public void onBindViewHolder(@NonNull myViewholder holder, int position) {
        final PriaParfumList priaParfumList = lists.get(position);

        holder.nama.setText(priaParfumList.getNama());
        holder.harga.setText(priaParfumList.getHarga());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
                final View v = LayoutInflater.from(context).inflate(R.layout.bottom_dialog, (ConstraintLayout) view.findViewById(R.id.container_dialog));

                v.findViewById(R.id.btn_keranjang).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                v.findViewById(R.id.btn_struk).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ParfumPriaStrukActivity.class);
                        view.getContext().startActivity(intent);
                    }
                });

                bottomSheetDialog.setContentView(v);
                bottomSheetDialog.show();
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
