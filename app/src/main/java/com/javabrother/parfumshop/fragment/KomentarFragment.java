package com.javabrother.parfumshop.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javabrother.parfumshop.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class KomentarFragment extends Fragment {


    public KomentarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_komentar, container, false);
    }

}
