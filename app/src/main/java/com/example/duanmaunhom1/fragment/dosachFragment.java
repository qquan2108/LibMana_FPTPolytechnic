package com.example.duanmaunhom1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.duanmaunhom1.DocSach;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class dosachFragment extends Fragment {

    ViewPager2 vp;
    TabLayout tabLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabfragment, container, false);
        vp=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);


        ViewPagerAdapter adapter=new ViewPagerAdapter(getActivity());
        vp.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, vp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
                tab.setText("Trang " + i);
            }
        }).attach();

        return v;

    }
}
