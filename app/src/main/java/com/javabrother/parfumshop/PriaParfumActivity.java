package com.javabrother.parfumshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class PriaParfumActivity extends AppCompatActivity {

    private RecyclerView rv_list;
    private PriaParfumAdapter adapter;
    private ArrayList<PriaParfumList> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pria_parfum);
        rv_list = findViewById(R.id.rv_Prialist);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PriaParfumAdapter(this, data);
        rv_list.setAdapter(adapter);

        list();
    }

    private void list() {
        data.add(new PriaParfumList("Parfum A", "Rp. 10.000"));
        data.add(new PriaParfumList("Parfum B", "Rp. 10.000"));
        data.add(new PriaParfumList("Parfum C", "Rp. 10.000"));
        data.add(new PriaParfumList("Parfum D", "Rp. 10.000"));
        data.add(new PriaParfumList("Parfum E", "Rp. 10.000"));
        data.add(new PriaParfumList("Parfum F", "Rp. 10.000"));
    }
}
