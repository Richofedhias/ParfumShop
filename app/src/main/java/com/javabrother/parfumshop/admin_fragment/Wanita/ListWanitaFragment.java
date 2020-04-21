package com.javabrother.parfumshop.admin_fragment.Wanita;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javabrother.parfumshop.R;
import com.javabrother.parfumshop.admin_fragment.Pria.PriaAdapter;
import com.javabrother.parfumshop.admin_fragment.Pria.PriaList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListWanitaFragment extends Fragment {
    RecyclerView rV_list;
    WanitaAdapter adapter;
    ArrayList<WanitaList> lists = new ArrayList<>();
    private DatabaseReference ref;
    private FirebaseDatabase database;

    public ListWanitaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_wanita, container, false);
        rV_list = v.findViewById(R.id.rv_AdminWanitaList);
        rV_list.setHasFixedSize(true);

        rV_list.setLayoutManager(new LinearLayoutManager(v.getContext()));
        LinearLayoutManager layoutManager = new  LinearLayoutManager(v.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rV_list.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(v.getContext(),
                layoutManager.getOrientation());
        rV_list.addItemDecoration(dividerItemDecoration);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        init();
        return v;
    }

    private void init() {
        ref.child("parfumecew").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lists = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    WanitaList barang = snapshot.getValue(WanitaList.class);
                    barang.setKey(snapshot.getKey());

                    lists.add(barang);

                }
                adapter = new WanitaAdapter(getContext(),lists);
                rV_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
