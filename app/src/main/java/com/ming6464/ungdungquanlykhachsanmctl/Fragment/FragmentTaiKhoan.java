package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_DoiPass;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_QuanLy;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThietLap;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThongKe;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThongTin;
import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanSharedPreferences;
import com.ming6464.ungdungquanlykhachsanmctl.LoginAcitivty;
import com.ming6464.ungdungquanlykhachsanmctl.R;


public class FragmentTaiKhoan extends Fragment {
    private Intent intent;
    private ImageView img_avatar;
    private TextView tv_welcomeUser;
    private boolean check;
    private People people;
    private NavigationView navigationView;
    public FragmentTaiKhoan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tai_khoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_welcomeUser = view.findViewById(R.id.fragTaiKhoan_tv_welcomeUser);
        img_avatar = view.findViewById(R.id.actiThongTin_img_avatar);
        navigationView = view.findViewById(R.id.nava);
        view.findViewById(R.id.fragTaiKhoan_linear_infoUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(requireContext(),Activity_ThongTin.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoad();
        hanldeNavigation();
    }

    private void hanldeNavigation() {
        if(people.getStatus() != 3){
            navigationView.getMenu().findItem(R.id.menu_4).setVisible(false);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_2:
                        intent = new Intent(getActivity(), Activity_DoiPass.class);
                        break;
                    case R.id.menu_3:
                        check = true;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Đăng Xuất");
                        builder.setMessage("Bạn có chắc là muốn đăng xuất không ");
                        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent = new Intent(getActivity(), LoginAcitivty.class);
                                startActivity(intent);
                                getActivity().finish();
                                CustomToast.makeText(getContext(), "Đăng Xuất Thành Công", true).show();
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
                        intent = new Intent(getActivity(), Activity_ThietLap.class);
                        break;
                    case R.id.menu_5:
                        intent = new Intent(getActivity(), Activity_QuanLy.class);
                        break;
                    case R.id.menu_6:
                        intent = new Intent(getActivity(), Activity_ThongKe.class);
                        break;
                }
                if(!check)
                    startActivity(intent);
                check = false;
                return false;
            }
        });
    }

    private void reLoad() {
        people = KhachSanDB.getInstance(requireContext()).getDAO().checkLogin(
                new KhachSanSharedPreferences(requireContext()).getSDT2());
        String name = people.getFullName();
        int index = name.lastIndexOf(" ") + 1;
        if(index > 1)
            name = name.substring(index);
        tv_welcomeUser.setText("Welcome to " + name);
        if(people.getSex() == 0)
            img_avatar.setBackgroundResource(R.drawable.businesswoman_100);
    }
}