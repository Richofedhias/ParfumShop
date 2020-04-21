package com.javabrother.parfumshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.javabrother.parfumshop.model.PriaParfumList;

import java.util.ArrayList;

public class PriaParfumActivity extends AppCompatActivity {

    private RecyclerView rv_list;
    private PriaParfumAdapter adapter;
    private ArrayList<PriaParfumList> data = new ArrayList<>();
    private DatabaseReference ref;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pria_parfum);
        rv_list = findViewById(R.id.rv_Prialist);
        rv_list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new  LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                layoutManager.getOrientation());
        rv_list.addItemDecoration(dividerItemDecoration);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        list();
    }

    private void list() {
        Query query = ref.child("parfumecow");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PriaParfumList lists = new PriaParfumList();
                    lists.setNamaParfum(snapshot.child("nama").getValue().toString());
                    lists.setHargaParfum(snapshot.child("harga").getValue().toString());

                    data.add(lists);
                }
                adapter = new PriaParfumAdapter(PriaParfumActivity.this, data);
                rv_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
