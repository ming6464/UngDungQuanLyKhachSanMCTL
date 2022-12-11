package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.FragmentAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.R;


public class PhongFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.fragPhong_tab);
        ViewPager2 viewPager2 = view.findViewById(R.id.fragPhong_viewPager);
        FragmentAdapter adapter = new FragmentAdapter(requireActivity(),new Fragment[]{SoDoPhongFragment.newInstance(),LoaiPhongFragment.newInstance()});
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Sơ Đồ Phòng");
                    break;
                case 1:
                    tab.setText("Loại Phòng");
                    break;
            }
        }).attach();
    }
}