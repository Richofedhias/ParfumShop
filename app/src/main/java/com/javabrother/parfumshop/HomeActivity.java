package com.javabrother.parfumshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.javabrother.parfumshop.fragment.AkunFragment;
import com.javabrother.parfumshop.fragment.BerandaFragment;
import com.javabrother.parfumshop.fragment.KeranjangFragment;
import com.javabrother.parfumshop.fragment.KomentarFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.menu_beranda:
                            selectedFragment = new BerandaFragment();
                            break;
                        case R.id.menu_keranjang:
                            selectedFragment = new KeranjangFragment();
                            break;
                        case R.id.menu_komentar:
                            selectedFragment = new KomentarFragment();
                            break;
                        case R.id.menu_akun:
                            selectedFragment = new AkunFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLay, selectedFragment).commit();

                    return true;
                }
            };
}
