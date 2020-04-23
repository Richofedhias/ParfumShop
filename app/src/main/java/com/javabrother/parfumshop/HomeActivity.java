package com.javabrother.parfumshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.javabrother.parfumshop.fragment.AboutFragment;
import com.javabrother.parfumshop.fragment.BerandaFragment;
import com.javabrother.parfumshop.fragment.FaqFragment;
import com.javabrother.parfumshop.fragment.KeranjangFragment;

public class HomeActivity extends AppCompatActivity {
    private BerandaFragment berandaFragment;
    private KeranjangFragment keranjangFragment;
    private FaqFragment faqFragment;
    private AboutFragment aboutFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        berandaFragment = new BerandaFragment();
        keranjangFragment = new KeranjangFragment();
        faqFragment = new FaqFragment();
        aboutFragment = new AboutFragment();
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
                        case R.id.menu_faq:
                            setFragment(faqFragment);
                            break;
                        case R.id.menu_about:
                            setFragment(aboutFragment);
                            break;
                    }
                    return true;
                }
            };

}
