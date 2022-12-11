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


public class HoaDonFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    public HoaDonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        tabLayout = view.findViewById(R.id.fragHoaDon_tab);
        viewPager2 = view.findViewById(R.id.fragHoaDon_viewPager);
        FragmentAdapter adapter = new FragmentAdapter(requireActivity(), new Fragment[]{HoaDonPhongFragment.newInstance(), HoaDonTongFragment.newInstance()});
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Hóa Đơn Phòng");
                        break;
                    case 1:
                        tab.setText("Hóa Đơn Tổng");
                        break;
                }
            }
        }).attach();
        return view;
    }
}