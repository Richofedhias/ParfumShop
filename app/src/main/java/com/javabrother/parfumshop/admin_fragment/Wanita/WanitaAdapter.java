package com.javabrother.parfumshop.admin_fragment.Wanita;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javabrother.parfumshop.R;
import com.javabrother.parfumshop.admin_fragment.Pria.PriaAdapter;
import com.javabrother.parfumshop.admin_fragment.Pria.PriaList;

import java.util.ArrayList;

public class WanitaAdapter extends RecyclerView.Adapter<WanitaAdapter.myViewHolder> {
    private ArrayList<WanitaList> lists;
    private Context mContext;

    public WanitaAdapter(ArrayList<WanitaList> lists, Context mContext) {
        this.lists = lists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_wanita_parfum_list, parent, false);
        return new WanitaAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final WanitaList item = lists.get(position);
        holder.nama.setText(item.getNama());
        holder.harga.setText(item.getHarga());
        holder.jumlah.setText(item.getJumlah());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), DetailLatihanAndJActivity.class);
//                intent.putExtra("judul", item.getJudul());
//                intent.putExtra("penjelasan", item.getDesk());
//                view.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView nama, harga, jumlah;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tV_AdminNamaParfumP);
            harga = itemView.findViewById(R.id.tV_AdminHargaParfumP);
            jumlah = itemView.findViewById(R.id.tV_AdminJumlahParfumP);
        }
    }
}
