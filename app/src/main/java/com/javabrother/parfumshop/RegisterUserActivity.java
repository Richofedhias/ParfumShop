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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.javabrother.parfumshop.model.User;

public class RegisterUserActivity extends AppCompatActivity {
    private TextInputLayout nama, email, password;
    private ProgressDialog mLoading;
    private Button signup;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private static final String USER = "user";
    private static final String TAG = "RegisterUserActivity";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(USER);
        nama = findViewById(R.id.eT_daftarNama);
        email = findViewById(R.id.eT_daftarEmail);
        password = findViewById(R.id.eT_daftarPassword);
        signup = findViewById(R.id.btn_signUp);
        mLoading = new ProgressDialog(this);
        mLoading.setMessage("Please Wait..");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaUser = nama.getEditText().getText().toString().trim();
                String emailUser = email.getEditText().getText().toString().trim();
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
                     user = new User(emailUser,namaUser);
                    registerUser(emailUser, passwordUser);
                }


            }
        });
    }

    private void registerUser(String emailUser, String passwordUser) {
        firebaseAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mLoading.dismiss();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Toast.makeText(RegisterUserActivity.this, "Berhasil Registrasi",Toast.LENGTH_SHORT).show();
                    updateUi(user);

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

    private void updateUi(FirebaseUser currentUser){
        String keyId = reference.push().getKey();
        reference.child(keyId).setValue(user);
        startActivity(new Intent(RegisterUserActivity.this, LoginUserActivity.class));
        finish();
    }
}
