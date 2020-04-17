package com.javabrother.parfumshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javabrother.parfumshop.fragment.KeranjangFragment;
import com.javabrother.parfumshop.model.ParfumeCow;

import java.util.HashMap;

public class DetailParfumActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private TextView detailNamaParfum, detailHargaParfum;
    private ElegantNumberButton detailJumlahParfum;
    private static final String PARFUMECOW = "parfumecow";
    private static final String PARFUMECEW = "parfumecew";
    private String namaParfum, hargaParfum;
    private Button btn_keranjang, btn_bayar;
    ParfumeCow parfumeCow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_parfum);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(PARFUMECOW);

        detailNamaParfum = findViewById(R.id.detail_Namadialog);
        detailHargaParfum = findViewById(R.id.detail_HargaDialog);
        detailJumlahParfum = findViewById(R.id.detail_JumlahDialog);

        namaParfum = getIntent().getStringExtra("nama");
        hargaParfum = getIntent().getStringExtra("harga");

        detailNamaParfum.setText(namaParfum);
        detailHargaParfum.setText(hargaParfum);

        btn_keranjang = findViewById(R.id.btn_keranjang);
        btn_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
    }

    public void addToCart() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Keranjang");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("nama", detailNamaParfum.getText().toString());
        cartMap.put("harga", detailHargaParfum.getText().toString());
        cartMap.put("jumlah", detailJumlahParfum.getNumber());

        ref.child("User View").child("Products").updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ref.child("User View").child("Products").updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(DetailParfumActivity.this, PriaParfumActivity.class));
                        }
                    });
                }
            }
        });
    }
}
