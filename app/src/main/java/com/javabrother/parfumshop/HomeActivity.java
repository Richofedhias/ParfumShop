package com.javabrother.parfumshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.javabrother.parfumshop.fragment.AkunFragment;
import com.javabrother.parfumshop.fragment.BerandaFragment;
import com.javabrother.parfumshop.fragment.KeranjangFragment;
import com.javabrother.parfumshop.fragment.KomentarFragment;

public class HomeActivity extends AppCompatActivity {
    private BerandaFragment berandaFragment;
    private KeranjangFragment keranjangFragment;
    private KomentarFragment komentarFragment;
    private AkunFragment akunFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        berandaFragment = new BerandaFragment();
        keranjangFragment = new KeranjangFragment();
        komentarFragment = new KomentarFragment();
        akunFragment = new AkunFragment();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        setFragment(berandaFragment);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLay, fragment);
        fragmentTransaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.menu_beranda:
                            setFragment(berandaFragment);
                            break;
                        case R.id.menu_keranjang:
                            setFragment(keranjangFragment);
                            break;
                        case R.id.menu_komentar:
                            setFragment(komentarFragment);
                            break;
                        case R.id.menu_akun:
                            setFragment(akunFragment);
                            break;
                    }


                    return true;
                }
            };
}
