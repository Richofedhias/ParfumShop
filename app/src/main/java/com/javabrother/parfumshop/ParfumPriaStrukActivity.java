package com.javabrother.parfumshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javabrother.parfumshop.model.KeranjangList;

import java.util.ArrayList;
import java.util.HashMap;

public class ParfumPriaStrukActivity extends AppCompatActivity {
    TextView nama, harga, jumlah, total;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button bayar;
    String extranama, extraharga, extrajumlah;
    private String Uid = "";
    int TotalHarga= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parfum_pria_struk);

        nama = findViewById(R.id.tV_NamaStrukPria);
        harga = findViewById(R.id.tV_HargaStrukPria);
        jumlah = findViewById(R.id.tV_JumlahStrukPria);
        total = findViewById(R.id.tV_TotalStrukPria);
        bayar = findViewById(R.id.btn_bayar);

        extranama = getIntent().getStringExtra("nama");
        extraharga = getIntent().getStringExtra("harga");
        extrajumlah = getIntent().getStringExtra("jumlah");
        String extratotal = getIntent().getStringExtra("Total");

        nama.setText(extranama);
        harga.setText("Rp. " + extraharga);
        jumlah.setText(extrajumlah);
        TotalHarga = TotalHarga + (Integer.parseInt(String.valueOf(extraharga)) * Integer.parseInt(String.valueOf(extrajumlah)));
        total.setText(String.valueOf(TotalHarga));
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Keranjang");

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order();
            }
        });
    }

    public void Order() {
        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference();
        Uid = orderRef.push().getKey();
        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("nama", nama.getText().toString());
        orderMap.put("harga", harga.getText().toString());
        orderMap.put("jumlah", jumlah.getText().toString());
        orderRef.child("Orders").child(Uid).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference().child("Keranjang").removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(ParfumPriaStrukActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}
