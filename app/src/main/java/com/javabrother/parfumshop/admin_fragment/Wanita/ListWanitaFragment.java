package com.javabrother.parfumshop.admin_fragment.Wanita;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.javabrother.parfumshop.R;
import com.javabrother.parfumshop.admin_fragment.Pria.PriaAdapter;
import com.javabrother.parfumshop.admin_fragment.Pria.PriaList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListWanitaFragment extends Fragment {
    RecyclerView rV_list;
    WanitaAdapter adapter;
    ArrayList<WanitaList> list = new ArrayList<>();
    ProgressBar pb;

    public ListWanitaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_wanita, container, false);
        rV_list = v.findViewById(R.id.rv_AdminWanitaList);
        rV_list.setHasFixedSize(true);

        rV_list.setLayoutManager(new LinearLayoutManager(getContext()));
        pb = (ProgressBar) v.findViewById(R.id.pb_Pria);
        pb.setVisibility(View.VISIBLE);

        init();
        return v;
        //        ElegantNumberButton button = (ElegantNumberButton) findViewById(R.id.button);
//        button.setOnClickListener(new ElegantNumberButton.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String num = button.getNumber();
//            }
//        });
    }

    private void init() {

        pb.setVisibility(View.INVISIBLE);
    }

}
