package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_DoiPass;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_QuanLy;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThietLap;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThongKe;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThongTin;
import com.ming6464.ungdungquanlykhachsanmctl.LoginAcitivty;
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


        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.menu_4).setVisible(false);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Đăng Xuất");
                        builder.setMessage("Bạn có chắc là muốn đăng xuất không ");
                        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(getActivity(), LoginAcitivty.class);
                                startActivity(intent1);
                                getActivity().finish();
                                Toast.makeText(getContext(), "Đăng Xuất Thành Công", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

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