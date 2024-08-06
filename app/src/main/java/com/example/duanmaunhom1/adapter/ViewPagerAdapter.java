package com.example.duanmaunhom1.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duanmaunhom1.leftfragment;
import com.example.duanmaunhom1.rightfagment1;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new leftfragment();
        if (position == 1)
            return new rightfagment1();

        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
