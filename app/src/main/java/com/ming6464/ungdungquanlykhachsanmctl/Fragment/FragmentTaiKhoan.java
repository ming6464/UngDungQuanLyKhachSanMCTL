package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;

import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_DoiPass;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_Logout;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_QuanLy;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThietLap;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThongKe;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThongTin;
import com.ming6464.ungdungquanlykhachsanmctl.R;


public class FragmentTaiKhoan extends Fragment {


    public FragmentTaiKhoan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        NavigationView navigationView = view.findViewById(R.id.nava);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_1:
                        Intent intent = new Intent(getActivity(), Activity_ThongTin.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_2:
                        Intent intent2 = new Intent(getActivity(), Activity_DoiPass.class);
                        startActivity(intent2);
                        break;
                    case R.id.menu_3:
                        Intent intent1 = new Intent(getActivity(), Activity_Logout.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_4:
                        Intent intent3 = new Intent(getActivity(), Activity_ThietLap.class);
                        startActivity(intent3);
                        break;
                    case R.id.menu_5:
                        Intent intent4 = new Intent(getActivity(), Activity_QuanLy.class);
                        startActivity(intent4);
                        break;
                    case R.id.menu_6:
                        Intent intent5 = new Intent(getActivity(), Activity_ThongKe.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });
        return view;
    }
}