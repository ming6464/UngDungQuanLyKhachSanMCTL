package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import com.google.android.material.textfield.TextInputEditText;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_QuanLy;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThietLap;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThongKe;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThongTin;
import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanSharedPreferences;
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
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_password, null);
                        TextInputEditText edt_password = view.findViewById(R.id.edt_password);
                        TextInputEditText edt_newPassword = view.findViewById(R.id.edt_newPassword);
                        Button btnSaveChange = view.findViewById(R.id.btnSaveChange);
                        Button btnCanceChange = view.findViewById(R.id.btnCanceChange);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setView(view);

                        AlertDialog dialog1 = builder1.create();
                        dialog1.show();

                        btnSaveChange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String oldpassword = edt_password.getText().toString().trim();
                                String newPassword = edt_newPassword.getText().toString().trim();
                                if (TextUtils.isEmpty(oldpassword)) {
                                    Toast.makeText(getContext(), "Nhập mật khẩu hiện tại của bạn", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(newPassword)) {
                                    Toast.makeText(getContext(), "Nhập mật khẩu mới của bạn", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (newPassword.length() < 3) {
                                    Toast.makeText(getContext(), "Độ dài mật khẩu phải ít nhất 3 ký tự", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                KhachSanSharedPreferences pref = new KhachSanSharedPreferences(requireContext());
                                if (!pref.getPassword().equals(oldpassword)) {
                                    Toast.makeText(getContext(), "Bạn đã nhập sai mật khẩu cũ. Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                pref.setPassword(newPassword);
                                CustomToast.makeText(getContext(),"Đổi mật khẩu thành công",true).show();
                                dialog1.dismiss();
                            }
                        });
                        btnCanceChange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.cancel();
                            }
                        });
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