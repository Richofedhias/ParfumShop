package com.javabrother.parfumshop.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.javabrother.parfumshop.post.PostActivity;
import com.javabrother.parfumshop.PriaParfumActivity;
import com.javabrother.parfumshop.ProfileActivity;
import com.javabrother.parfumshop.R;
import com.javabrother.parfumshop.WanitaParfumActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {

    Button btn_pria, btn_wanita;
    ImageView komen, akun;

    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_beranda, container, false);
        btn_pria = v.findViewById(R.id.btn_pria);
        btn_wanita = v.findViewById(R.id.btn_wanita);
        komen = v.findViewById(R.id.iV_comment);
        akun = v.findViewById(R.id.iV_akun);

        btn_pria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PriaParfumActivity.class));
            }
        });

        btn_wanita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WanitaParfumActivity.class));
            }
        });

        akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        komen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PostActivity.class));
            }
        });

        return v;
    }

}
