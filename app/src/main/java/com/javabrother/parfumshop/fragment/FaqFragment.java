package com.javabrother.parfumshop.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javabrother.parfumshop.BerandaAdminActivity;
import com.javabrother.parfumshop.FaqAdapter;
import com.javabrother.parfumshop.KeranjangAdapter;
import com.javabrother.parfumshop.R;
import com.javabrother.parfumshop.TambahListActivity;
import com.javabrother.parfumshop.model.FaqList;
import com.javabrother.parfumshop.model.KeranjangList;
import com.javabrother.parfumshop.model.ParfumeCow;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaqFragment extends Fragment {

    private RecyclerView rv_list;
    private FaqAdapter adapter;
    private ArrayList<FaqList> lists;
    private DatabaseReference ref;
    private FirebaseDatabase database;
    private FaqList faqList;
    private FloatingActionButton fab;

    public FaqFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_faq, container, false);
        fab = v.findViewById(R.id.fabfaq);
        rv_list = v.findViewById(R.id.rv_faq);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(v.getContext()));
        LinearLayoutManager layoutManager = new  LinearLayoutManager(v.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(v.getContext(),
                layoutManager.getOrientation());
        rv_list.addItemDecoration(dividerItemDecoration);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Faq");
        list();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog(view);
            }
        });
        return v;
    }

    private void Dialog(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View v = getLayoutInflater().inflate(R.layout.custom_dialog, null);

        final EditText faq = v.findViewById(R.id.eT_Faq);
        Button btn_batal = v.findViewById(R.id.btn_batal);
        Button btn_kirim = v.findViewById(R.id.btn_kirim);

        builder.setView(v);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pertanyaan = faq.getText().toString();

                faqList = new FaqList(pertanyaan);
                String keyId = ref.push().getKey();
                ref.child(keyId).setValue(faqList);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void list() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lists = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FaqList faq = snapshot.getValue(FaqList.class);
                    faq.setKey(snapshot.getKey());

                    lists.add(faq);

                }
                adapter = new FaqAdapter(getContext(),lists);
                rv_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
