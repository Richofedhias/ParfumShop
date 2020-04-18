package com.javabrother.parfumshop.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.javabrother.parfumshop.KeranjangAdapter;
import com.javabrother.parfumshop.R;
import com.javabrother.parfumshop.model.KeranjangList;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class KeranjangFragment extends Fragment {

    private RecyclerView rv_list;
    private KeranjangAdapter adapter;
    private ArrayList<KeranjangList> lists;
    private DatabaseReference ref;
    private FirebaseDatabase database;

    public KeranjangFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_keranjang, container, false);
        rv_list = v.findViewById(R.id.rv_Keranjang);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(v.getContext()));
        LinearLayoutManager layoutManager = new  LinearLayoutManager(v.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(v.getContext(),
                layoutManager.getOrientation());
        rv_list.addItemDecoration(dividerItemDecoration);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        list();

        return v;
    }

    public void list() {

        ref.child("Keranjang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lists = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    KeranjangList barang = snapshot.getValue(KeranjangList.class);
                    barang.setKey(snapshot.getKey());

                    lists.add(barang);

                }
                adapter = new KeranjangAdapter(getContext(),lists);
                rv_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
