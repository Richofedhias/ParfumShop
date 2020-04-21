package com.javabrother.parfumshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.javabrother.parfumshop.model.ParfumeCew;
import com.javabrother.parfumshop.model.ParfumeCow;

import java.util.HashMap;

public class TambahListActivity extends AppCompatActivity {
    private TextInputLayout namaParfume, harga;
    private Button tambahparfumeCowo, tambahparfumeCewe;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseDatabase database1;
    DatabaseReference referenceCew;
    private static final String PARFUMECOW = "parfumecow";
    private static final String PARFUMECEW = "parfumecew";
    private static final String TAG = "TambahListActivity";
    ParfumeCow parfumeCow;
    ParfumeCew parfumeCew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_list);

        namaParfume = findViewById(R.id.eT_AdminTambahNamaParfum);
        harga = findViewById(R.id.eT_AdminTambahHargaParfum);
        tambahparfumeCowo = findViewById(R.id.btn_TambahListPria);
        tambahparfumeCewe = findViewById(R.id.btn_TambahListWanita);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(PARFUMECOW);
        database1 = FirebaseDatabase.getInstance();
        referenceCew = database.getReference(PARFUMECEW);


        tambahparfumeCowo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaParDat = namaParfume.getEditText().getText().toString();
                String hargaDat = harga.getEditText().getText().toString();
                if (namaParDat.isEmpty()) {
                    namaParfume.setError("Mohon Diisi Nama Parfume");
                    namaParfume.setFocusable(true);
                }
                if (hargaDat.isEmpty()) {
                    harga.setError("Mohon Diisi Harga Parfume");
                    harga.setFocusable(true);
                }
                else {
                    parfumeCow = new ParfumeCow(namaParDat, hargaDat);
                    String keyId = reference.push().getKey();
                    reference.child(keyId).setValue(parfumeCow);
                    Intent intent = new Intent(TambahListActivity.this,BerandaAdminActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        tambahparfumeCewe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaParDat = namaParfume.getEditText().getText().toString();
                String hargaDat = harga.getEditText().getText().toString();
                if (namaParDat.isEmpty()) {
                    namaParfume.setError("Mohon Diisi Nama Parfume");
                    namaParfume.setFocusable(true);
                }
                if (hargaDat.isEmpty()) {
                    harga.setError("Mohon Diisi Harga Parfume");
                    harga.setFocusable(true);
                }
                else {
                    parfumeCew = new ParfumeCew(namaParDat, hargaDat);
                    String keyId = referenceCew.push().getKey();
                    referenceCew.child(keyId).setValue(parfumeCew);
                    Intent intent = new Intent(TambahListActivity.this,BerandaAdminActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });


    }
}
