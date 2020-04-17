package com.javabrother.parfumshop.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private ArrayList<KeranjangList> lists = new ArrayList<>();
    private Button btn_next;
    private TextView Total;

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

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        list();

        btn_next = v.findViewById(R.id.btn_Next);
        Total = v.findViewById(R.id.tV_JumlahTotal);

        return v;
    }

    public void list() {
        Query query = ref.child("Keranjang").child("User View").child("Products");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    KeranjangList contoh = new KeranjangList();
                    contoh.setNama(snapshot.child("nama").getValue().toString());
                    contoh.setHarga(snapshot.child("harga").getValue().toString());
                    contoh.setJumlah(snapshot.child("jumlah").getValue().toString());

                    lists.add(contoh);
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
