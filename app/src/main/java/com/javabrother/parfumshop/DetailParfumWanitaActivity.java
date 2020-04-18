package com.javabrother.parfumshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.javabrother.parfumshop.model.ParfumeCow;

import java.util.HashMap;

public class DetailParfumWanitaActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private TextView detailNamaParfum, detailHargaParfum;
    private ElegantNumberButton detailJumlahParfum;
    private static final String PARFUMECEW = "parfumecew";
    private String namaParfum, hargaParfum;
    private Button btn_keranjang, btn_bayar;
    private String Uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_parfum_wanita);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(PARFUMECEW);

        detailNamaParfum = findViewById(R.id.detail_NamadialogW);
        detailHargaParfum = findViewById(R.id.detail_HargaDialogW);
        detailJumlahParfum = findViewById(R.id.detail_JumlahDialogW);

        namaParfum = getIntent().getStringExtra("nama");
        hargaParfum = getIntent().getStringExtra("harga");

        detailNamaParfum.setText(namaParfum);
        detailHargaParfum.setText(hargaParfum);
        Uid = reference.push().getKey();
        btn_keranjang = findViewById(R.id.btn_keranjang);
        btn_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        btn_bayar = findViewById(R.id.btn_struk);
        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailParfumWanitaActivity.this, ParfumWanitaStrukActivity.class);
                intent.putExtra("nama", detailNamaParfum.getText().toString());
                intent.putExtra("harga", detailHargaParfum.getText().toString());
                intent.putExtra("jumlah", detailJumlahParfum.getNumber());
                startActivity(intent);
            }
        });
    }

    public void addToCart() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("nama", detailNamaParfum.getText().toString());
        cartMap.put("harga", detailHargaParfum.getText().toString());
        cartMap.put("jumlah", detailJumlahParfum.getNumber());

        ref.child("Keranjang").child(Uid).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ref.child("Keranjang").child(Uid).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(DetailParfumWanitaActivity.this, HomeActivity.class));
                        }
                    });
                }
            }
        });
    }
}
