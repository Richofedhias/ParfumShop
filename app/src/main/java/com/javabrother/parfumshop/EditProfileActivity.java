package com.javabrother.parfumshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private TextInputLayout editNama, editEmail, editAlamat, editJenis;
    private Button simpan;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editNama = findViewById(R.id.eT_editNama);
        editEmail = findViewById(R.id.eT_editEmail);
        editAlamat = findViewById(R.id.eT_editAlamat);
        editJenis = findViewById(R.id.eT_editJenisKel);
        simpan = findViewById(R.id.btn_simpan);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();


        DocumentReference documentReference = fStore.collection("user").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                editNama.getEditText().setText(documentSnapshot.getString("nama"));
                editEmail.getEditText().setText(documentSnapshot.getString("email"));
                editJenis.getEditText().setText(documentSnapshot.getString("jenisKel"));
                editAlamat.getEditText().setText(documentSnapshot.getString("alamat"));
            }
        });


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userId != null) {
                    String namaUser = editNama.getEditText().getText().toString();
                    String emailUser = editEmail.getEditText().getText().toString();
                    String jenisKel = editJenis.getEditText().getText().toString();
                    String alamat = editAlamat.getEditText().getText().toString();

                    DocumentReference documentReference = fStore.collection("user").document(userId);
                    Map<String, Object> usermap = new HashMap<>();
                    usermap.put("nama", namaUser);
                    usermap.put("email", emailUser);
                    usermap.put("jenisKel", jenisKel);
                    usermap.put("alamat", alamat);
                    documentReference.set(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(EditProfileActivity.this,ProfileActivity.class));
                            finish();


                        }
                    });
                }

            }
        });
    }
}
