package com.javabrother.parfumshop.admin_fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.javabrother.parfumshop.admin_fragment.Pria.ListPriaFragment;
import com.javabrother.parfumshop.admin_fragment.Wanita.ListWanitaFragment;

public class AdminAdapter extends FragmentPagerAdapter {

    Context mContext;

    public AdminAdapter(Context mContext, @NonNull FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ListPriaFragment();
        } else {
            return new ListWanitaFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
