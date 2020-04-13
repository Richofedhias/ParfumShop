package com.javabrother.parfumshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class WanitaParfumActivity extends AppCompatActivity {

    private RecyclerView rv_list;
    private WanitaParfumAdapter adapter;
    private ArrayList<WanitaParfumList> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanita_parfum);
        rv_list = findViewById(R.id.rv_Wanitalist);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WanitaParfumAdapter(this, data);
        rv_list.setAdapter(adapter);

        list();
    }

    private void list() {
        data.add(new WanitaParfumList("Parfum A", "Rp. 10.000"));
        data.add(new WanitaParfumList("Parfum B", "Rp. 10.000"));
        data.add(new WanitaParfumList("Parfum C", "Rp. 10.000"));
        data.add(new WanitaParfumList("Parfum D", "Rp. 10.000"));
        data.add(new WanitaParfumList("Parfum E", "Rp. 10.000"));
        data.add(new WanitaParfumList("Parfum F", "Rp. 10.000"));
    }
}
