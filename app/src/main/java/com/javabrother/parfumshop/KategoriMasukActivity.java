package com.javabrother.parfumshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class KategoriMasukActivity extends AppCompatActivity {
    private ImageView loginUser, loginAdmin;
    String myEmail, myUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_masuk);
        loginUser = findViewById(R.id.iV_user);
        loginAdmin = findViewById(R.id.iV_admin);


        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KategoriMasukActivity.this,LoginUserActivity.class));
            }
        });

        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KategoriMasukActivity.this,LoginAdminActivity.class));
            }
        });

        checkUserStatus();
    }

    private void checkUserStatus(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            //user is login
            myEmail = user.getEmail();
            myUid = user.getUid();

        }
        else {
            //user not login
            startActivity(new Intent(this, KategoriMasukActivity.class));
            finish();
        }
    }
}
