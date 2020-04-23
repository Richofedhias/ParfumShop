package com.javabrother.parfumshop.admin_fragment.Pria;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javabrother.parfumshop.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListPriaFragment extends Fragment {
    RecyclerView rV_list;
    PriaAdapter adapter;
    ArrayList<PriaList> lists = new ArrayList<>();
    private DatabaseReference ref;
    private FirebaseDatabase database;

    public ListPriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_pria, container, false);
        rV_list = v.findViewById(R.id.rv_AdminPriaList);
        rV_list.setHasFixedSize(true);

        rV_list.setLayoutManager(new LinearLayoutManager(v.getContext()));
        LinearLayoutManager layoutManager = new  LinearLayoutManager(v.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rV_list.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(v.getContext(),
                layoutManager.getOrientation());
        rV_list.addItemDecoration(dividerItemDecoration);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("parfumecow");

        ref.addListenerForSingleValueEvent(valueEventListener);
        return v;

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                PriaList barang = snapshot.getValue(PriaList.class);
                barang.setKey(snapshot.getKey());

                lists.add(barang);

            }
            adapter = new PriaAdapter(lists,getContext());
            rV_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
