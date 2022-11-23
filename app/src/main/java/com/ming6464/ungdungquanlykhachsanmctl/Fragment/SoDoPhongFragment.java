package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.FragmentAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.R;


public class SoDoPhongFragment extends Fragment {
//    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2;
    private FragmentAdapter fragmentAdapter;
    public static SoDoPhongFragment newInstance() {
        SoDoPhongFragment fragment = new SoDoPhongFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_so_do_phong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        bottomNavigationView = view.findViewById(R.id.fragSoDoPhong_bottomNavi);
        viewPager2 = view.findViewById(R.id.fragSoDoPhong_viewpager);
        fragmentAdapter = new FragmentAdapter(requireActivity(),new Fragment[]{PhongFragment.newInstance(),LoaiPhongFragment.newInstance()});
        viewPager2.setAdapter(fragmentAdapter);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        onReload();
//    }
//
//    private void onReload() {
//        addNaviB();
//        addPager();
//    }
//
//    private void addPager() {
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                switch (position){
//                    case 0:
//                        bottomNavigationView.setSelectedItemId(R.id.fragSoDoPhongMenu_viewPager_phong);
//                        break;
//                    case 1:
//                        bottomNavigationView.setSelectedItemId(R.id.fragSoDoPhongMenu_viewPager_loaiPhong);
//                        break;
//                }
//            }
//        });
//    }
//
//    private void addNaviB() {
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            int id = item.getItemId();
//            if(id == R.id.fragSoDoPhongMenu_viewPager_phong)
//                viewPager2.setCurrentItem(0);
//            else if(id == R.id.fragSoDoPhongMenu_viewPager_loaiPhong)
//                viewPager2.setCurrentItem(1);
//            return true;
//        });
//    }
}