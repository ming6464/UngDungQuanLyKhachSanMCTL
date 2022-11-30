package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.FragmentAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.R;


public class SoDoFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    public SoDoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_so_do, container, false);
        tabLayout = view.findViewById(R.id.tab1);
        viewPager2 = view.findViewById(R.id.view1);
        FragmentAdapter adapter = new FragmentAdapter(requireActivity(),new Fragment[]{PhongFragment.newInstance(),LoaiPhongFragment.newInstance()});
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Sơ Đồ Phòng");
                        break;
                    case 1:
                        tab.setText("Loại Phòng");
                        break;
                }
            }
        }).attach();
        return view;
    }
}