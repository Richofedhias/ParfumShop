package com.javabrother.parfumshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {
    private TextView nama,jenisKel,alamat,email;
    private Button logout;
    private FloatingActionButton fab;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nama = findViewById(R.id.tVNama);
        email = findViewById(R.id.tVEmail);
        jenisKel =findViewById(R.id.tV_kelamin);
        alamat = findViewById(R.id.tV_nilaAlamat);
        logout = findViewById(R.id.btn_logout);
        fab = findViewById(R.id.fab_edit);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("user").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                nama.setText(documentSnapshot.getString("nama"));
                email.setText(documentSnapshot.getString("email"));
                jenisKel.setText(documentSnapshot.getString("jenisKel"));
                alamat.setText(documentSnapshot.getString("alamat"));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,LoginUserActivity.class));
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));
            }
        });


    }
}
