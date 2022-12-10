package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;

public class LoginAcitivty extends AppCompatActivity {
    private EditText edUser, edPass;
    private KhachSanDAO dao;
    private KhachSanSharedPreferences share;
    private CheckBox chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivty);
        share = new KhachSanSharedPreferences(this);
        dao = KhachSanDB.getInstance(this).getDAO();
        anhXa();
        checkRemember();
        addData();
    }

    private void anhXa() {
        edUser = findViewById(R.id.edUser);
        edPass = findViewById(R.id.edPass);
        chk = findViewById(R.id.chkCheck);
    }

    private void checkRemember() {
        share.getID();
        String sdt = share.getSDT();
        if(sdt != null){
            edUser.setText(sdt);
            edPass.setText(share.getPassword());
            chk.setChecked(true);
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

    public void handleActionBtnLogin(View view) {
        String phoneNumber = edUser.getText().toString(),password = edPass.getText().toString();

        if (phoneNumber.isEmpty() || password.isEmpty()) {
            CustomToast.makeText(LoginAcitivty.this, "Vui Lòng Không Để Trống Thông Tin", false).show();
            return;
        }
        if(phoneNumber.length() > 10){
            CustomToast.makeText(LoginAcitivty.this, "Thông tin sai định dạng", false).show();
            return;
        }

        People people = dao.checkLogin(phoneNumber);
        if (people != null && password.equals(people.getPassowrd())) {
            Intent intent = new Intent(LoginAcitivty.this, MainActivity.class);
            startActivity(intent);
            share.setAccount(people,chk.isChecked());
            CustomToast.makeText(LoginAcitivty.this, "Đăng Nhập Thành Công", true).show();
            finish();
            return;
        }
        CustomToast.makeText(LoginAcitivty.this, "Đăng Nhập Thất Bại", false).show();
    }
}