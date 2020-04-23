package com.javabrother.parfumshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginAdminActivity extends AppCompatActivity {
    private Button login;
    private TextInputLayout email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        email = findViewById(R.id.eT_loginEmail);
        password = findViewById(R.id.eT_loginPassword);
        login = findViewById(R.id.btn_Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getEditText().getText().toString().equals("admin") && password.getEditText().getText().toString().equals("admin")){
                    startActivity(new Intent(LoginAdminActivity.this,BerandaAdminActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginAdminActivity.this, "bukan admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
