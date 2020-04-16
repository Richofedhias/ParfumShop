package com.javabrother.parfumshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.javabrother.parfumshop.model.User;

import java.util.HashMap;
import java.util.Map;

public class RegisterUserActivity extends AppCompatActivity {
    private TextInputLayout nama, email, password;
    private ProgressDialog mLoading;
    private Button signup;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fStore;
    String userId;
    String namaUser,emailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        firebaseAuth = FirebaseAuth.getInstance();
        nama = findViewById(R.id.eT_daftarNama);
        email = findViewById(R.id.eT_daftarEmail);
        password = findViewById(R.id.eT_daftarPassword);
        signup = findViewById(R.id.btn_signUp);
        mLoading = new ProgressDialog(this);
        mLoading.setMessage("Please Wait..");
        fStore = FirebaseFirestore.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 namaUser = nama.getEditText().getText().toString().trim();
                 emailUser = email.getEditText().getText().toString().trim();
                String passwordUser = password.getEditText().getText().toString().trim();

                if (emailUser.isEmpty()) {
                    email.setError("Masukan Email Anda");
                    email.setFocusable(true);
                } else if (!emailUser.matches(emailPattern)) {
                    email.setError("Masukan Format Email yang Benar");
                    email.setFocusable(true);
                } else if (passwordUser.isEmpty()) {
                    password.setError("Masukan Password Anda");
                    password.setFocusable(true);
                } else if (passwordUser.length() < 8) {
                    password.setError("Masukan Password minimal 8");
                    password.setFocusable(true);
                } else {
                    registerUser(emailUser, passwordUser);
                }


            }
        });
    }

    private void registerUser(final String emailUser, String passwordUser) {
        firebaseAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mLoading.dismiss();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    userId = firebaseAuth.getCurrentUser().getUid();
                    Toast.makeText(RegisterUserActivity.this, "Berhasil Registrasi",Toast.LENGTH_SHORT).show();
                    DocumentReference documentReference = fStore.collection("user").document(userId);
                    Map<String,Object> usermap = new HashMap<>();
                    usermap.put("nama",namaUser);
                    usermap.put("email",emailUser);
                    usermap.put("jenisKel","");
                    usermap.put("alamat","");
                    documentReference.set(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterUserActivity.this, userId, Toast.LENGTH_SHORT).show();
                        }
                    });
                    

                } else {
                    mLoading.dismiss();
                    Toast.makeText(RegisterUserActivity.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
