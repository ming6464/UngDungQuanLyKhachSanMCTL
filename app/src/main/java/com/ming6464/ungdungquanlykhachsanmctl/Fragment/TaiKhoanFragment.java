package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.QuanLyActivity;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.ThongKeActivity;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.ThongTinNguoiDungActivity;
import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanSharedPreferences;
import com.ming6464.ungdungquanlykhachsanmctl.DangNhapAcitivty;
import com.ming6464.ungdungquanlykhachsanmctl.R;


public class TaiKhoanFragment extends Fragment {
    private Intent intent;
    private ImageView img_avatar;
    private TextView tv_welcomeUser;
    private KhachSanSharedPreferences share;
    private boolean check;
    private KhachSanDAO dao;
    private People people;
    private NavigationView navigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tai_khoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        check = true;
        tv_welcomeUser = view.findViewById(R.id.fragTaiKhoan_tv_welcomeUser);
        img_avatar = view.findViewById(R.id.actiThongTin_img_avatar);
        navigationView = view.findViewById(R.id.nava);
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        share = new KhachSanSharedPreferences(requireContext());
        view.findViewById(R.id.fragTaiKhoan_linear_infoUser).setOnClickListener(v -> {
            intent = new Intent(requireContext(), ThongTinNguoiDungActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoad();
        hanldeNavigation();
    }

    private void hanldeNavigation() {
        if(people.getStatus() != 2){
            navigationView.getMenu().findItem(R.id.menu_5).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_6).setVisible(false);
        }
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_2:
                    check = false;
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_password, null);
                    EditText edt_password = view.findViewById(R.id.dialogUpdatePassword_ed_oldPass);
                    EditText edt_newPassword = view.findViewById(R.id.dialogUpdatePassword_ed_newPass);
                    EditText edt_confimPass = view.findViewById(R.id.dialogUpdatePassword_ed_reNewPass);
                    Button btnSaveChange = view.findViewById(R.id.dialogUpdatePassword_btn_yes);
                    ImageButton btnCanceChange = view.findViewById(R.id.dialogUpdatePassword_imgBtn_cancel);
                    People people = dao.getObjOfUser(share.getID());

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setView(view);

                    AlertDialog dialog1 = builder1.create();
                    Window window = dialog1.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                    window.getAttributes().windowAnimations = R.style.dialog_slide_left_to_right;
                    dialog1.show();

                    btnSaveChange.setOnClickListener(v -> {
                        String oldpassword = edt_password.getText().toString();
                        String newPassword = edt_newPassword.getText().toString();
                        String confimPass = edt_confimPass.getText().toString();
                        if (oldpassword.isEmpty() || newPassword.isEmpty() || confimPass.isEmpty()) {
                            CustomToast.makeText(getContext(), "Thông tin thiếu !", false).show();
                            return;
                        }
                        if (newPassword.length() < 3) {
                            CustomToast.makeText(getContext(), "Độ dài mật khẩu phải ít nhất 3 ký tự", false).show();
                            return;
                        }

                        if (!newPassword.equals(confimPass)) {
                            CustomToast.makeText(getContext(), "Mật khẩu mới không khớp. Mời nhập lại", false).show();
                            return;
                        }
                        if(!people.getPassowrd().equals(oldpassword)){
                            CustomToast.makeText(getContext(), "Mật khẩu sai !", false).show();
                            return;
                        }
                        people.setPassowrd(newPassword);
                        dao.UpdateUser(people);
                        CustomToast.makeText(getContext(),"Đổi mật khẩu thành công",true).show();
                        dialog1.dismiss();
                    });
                    btnCanceChange.setOnClickListener(v -> dialog1.cancel());
                    break;
                case R.id.menu_3:
                    check = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Đăng Xuất");
                    builder.setMessage("Bạn có chắc là muốn đăng xuất không ");
                    builder.setNegativeButton("Có", (dialog, which) -> {
                        intent = new Intent(getActivity(), DangNhapAcitivty.class);
                        startActivity(intent);
                        getActivity().finish();
                        CustomToast.makeText(getContext(), "Đăng Xuất Thành Công", true).show();
                    });
                    builder.setPositiveButton("Không", (dialog, which) -> {});
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    break;
                case R.id.menu_5:
                    intent = new Intent(getActivity(), QuanLyActivity.class);
                    break;
                case R.id.menu_6:
                    intent = new Intent(getActivity(), ThongKeActivity.class);
                    break;
            }
            if(check)
                startActivity(intent);
            check = true;
            return false;
        });
    }

    private void reLoad() {
        people = dao.checkLogin(share.getSDT2());
        String name = people.getFullName();
        int index = name.lastIndexOf(" ") + 1;
        if(index > 1)
            name = name.substring(index);
        tv_welcomeUser.setText("Welcome to " + name);
        if(people.getSex() == 0)
            img_avatar.setBackgroundResource(R.drawable.businesswoman_100);
    }
}