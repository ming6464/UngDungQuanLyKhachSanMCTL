package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;

public class DangNhapAcitivty extends AppCompatActivity {
    private EditText ed_sdt, ed_pass;
    private KhachSanDAO dao;
    private KhachSanSharedPreferences share;
    private CheckBox rdo_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        share = new KhachSanSharedPreferences(this);
        dao = KhachSanDB.getInstance(this).getDAO();
        anhXa();
        checkRemember();
        addData();
    }

    private void anhXa() {
        ed_sdt = findViewById(R.id.actiDangNhap_ed_sdt);
        ed_pass = findViewById(R.id.actiDangNhap_ed_pass);
        rdo_remember = findViewById(R.id.actiDangNhap_rdo_remember);
    }

    private void checkRemember() {
        share.getID();
        String sdt = share.getSDT();
        if(sdt != null){
            ed_sdt.setText(sdt);
            ed_pass.setText(share.getPassword());
            rdo_remember.setChecked(true);
        }
    }

    private void addData() {
        if(!share.getCheck1()){
            People people = new People("admin full name", "0123456789", "001726676330", "hn", 1, 2);
            people.setPassowrd("123");
            dao.insertOfUser(people);
            share.setCheck1(true);
        }
    }

    public void handleActionBtnDangNhap(View view) {
        String phoneNumber = ed_sdt.getText().toString(),password = ed_pass.getText().toString();

        if (phoneNumber.isEmpty() || password.isEmpty()) {
            CustomToast.makeText(DangNhapAcitivty.this, "Vui Lòng Không Để Trống Thông Tin !", false).show();
            return;
        }
        if(!phoneNumber.matches("^0\\d{9}")){
            CustomToast.makeText(DangNhapAcitivty.this, "Số điện thoại hoặc mật khẩu chưa chính xác !", false).show();
            return;
        }

        People people = dao.checkLogin(phoneNumber);
        if (people != null && password.equals(people.getPassowrd())) {
            if(people.getStatus() == 4){
                CustomToast.makeText(DangNhapAcitivty.this, "Tài khoản tạm thời ngừng hoạt động !", false).show();
                return;
            }
            else if(people.getStatus() == 5){
                CustomToast.makeText(DangNhapAcitivty.this, "Tài khoản đã bị ngừng hoạt động !", false).show();
                return;
            }
            Intent intent = new Intent(DangNhapAcitivty.this, MainActivity.class);
            startActivity(intent);
            share.setAccount(people,rdo_remember.isChecked());
            CustomToast.makeText(DangNhapAcitivty.this, "Đăng Nhập Thành Công !", true).show();
            finish();
            return;
        }
        CustomToast.makeText(DangNhapAcitivty.this, "Đăng Nhập Thất Bại !", false).show();
    }

    public void handleActionQuenMatKhau(View view) {
        Intent intent = new Intent(this,QuenMatKhauActivity.class);
        startActivity(intent);
    }

}