package com.javabrother.parfumshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javabrother.parfumshop.model.FaqList;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.myViewholder> {

    Context context;
    ArrayList<FaqList> lists;

    public FaqAdapter(Context context, ArrayList<FaqList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public FaqAdapter.myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_faq_list, parent,false);
        final FaqAdapter.myViewholder holder = new FaqAdapter.myViewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FaqAdapter.myViewholder holder, int position) {
        final FaqList faqList = lists.get(position);
        holder.pertanyaan.setText(faqList.getPertanyaan());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class myViewholder extends RecyclerView.ViewHolder {
        TextView pertanyaan;
        public myViewholder(@NonNull View itemView) {
            super(itemView);
            pertanyaan = itemView.findViewById(R.id.tV_faq);
        }
    }
}
