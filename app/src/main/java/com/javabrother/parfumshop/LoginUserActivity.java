package com.javabrother.parfumshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUserActivity extends AppCompatActivity {
    private Button login;
    private TextInputLayout email, password;
    private TextView register, forget;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog mLoading;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        email = findViewById(R.id.eT_loginEmailUser);
        password = findViewById(R.id.eT_loginPasswordUser);
        login = findViewById(R.id.btn_LoginUser);
        register = findViewById(R.id.tV_registrasi);
        forget = findViewById(R.id.tV_forget);
        mLoading = new ProgressDialog(this);
        mLoading.setMessage("Please Wait..");
        firebaseAuth = FirebaseAuth.getInstance();

//        if (firebaseAuth !=null){
//            startActivity(new Intent(LoginUserActivity.this,HomeActivity.class));
//            finish();
//        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email_user = email.getEditText().getText().toString();
                final String password_user = password.getEditText().getText().toString();

                if (email_user.isEmpty()) {
                    email.setError("Masukan Email");
                    email.setFocusable(true);
                } else if (password_user.isEmpty()) {
                    password.setError("Masukan Password");
                    password.setFocusable(true);
                } else if (!email_user.matches(emailPattern)) {
                    email.setError("Masukan Email yang Valid");
                    email.setFocusable(true);
                } else {
                    loginUser(email_user, password_user);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginUserActivity.this, RegisterUserActivity.class));
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginUserActivity.this,ForgetPasswordActivity.class));
            }
        });
    }

    private void loginUser(String email_user, String password_user) {
        mLoading.show();
        firebaseAuth.signInWithEmailAndPassword(email_user, password_user)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(LoginUserActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(LoginUserActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                        } else {
                            mLoading.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mLoading.dismiss();
                Toast.makeText(LoginUserActivity.this, "Login Gagal " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
