package com.javabrother.parfumshop.admin_fragment.Pria;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.javabrother.parfumshop.R;

import java.util.ArrayList;

public class PriaAdapter extends RecyclerView.Adapter<PriaAdapter.myViewHolder> {
    private ArrayList<PriaList> lists;
    private Context mContext;

    public PriaAdapter(ArrayList<PriaList> lists, Context mContext) {
        this.lists = lists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_pria_admin_list, parent, false);
        return new PriaAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final PriaList item = lists.get(position);
        holder.nama.setText(item.getNama());
        holder.harga.setText(item.getHarga());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("parfumecow").child(item.getKey());
                reference.removeValue();
                Toast.makeText(mContext, "Data Sudah Terhapus", Toast.LENGTH_SHORT).show();
            }
        });

//        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), EditParfumPriaActivity.class);
//                intent.putExtra("nama", item.getNama());
//                intent.putExtra("harga", item.getHarga());
//                view.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView nama, harga;
        Button btn_delete, btn_edit;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tV_AdminNamaParfumP);
            harga = itemView.findViewById(R.id.tV_AdminHargaParfumP);
            btn_delete = itemView.findViewById(R.id.btn_HapusP);
            btn_edit = itemView.findViewById(R.id.btn_EditP);
        }
    }
}
