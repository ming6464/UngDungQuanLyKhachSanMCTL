package com.ming6464.ungdungquanlykhachsanmctl.Activiti_User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanSharedPreferences;
import com.ming6464.ungdungquanlykhachsanmctl.R;

public class ThongTinNguoiDungActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img1;
    KhachSanDAO dao;
    String sdt;
    TextView tv_name, tv_sdt, tv_cccd, tv_sex, tv_address, tv_welcome, tv_tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);
        dao = KhachSanDB.getInstance(this).getDAO();
        anhXa();
        sdt = new KhachSanSharedPreferences(this).getSDT2();
        tv_tb.setText("Thông Tin Người Dùng");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void anhXa() {
        tv_welcome = findViewById(R.id.actiThongTinNguoiDung_tv_welcome);
        tv_name = findViewById(R.id.actiThongTinNguoiDung_tv_name);
        tv_cccd = findViewById(R.id.actiThongTinNguoiDung_tv_cccd);
        tv_sdt = findViewById(R.id.actiThongTinNguoiDung_tv_sdt);
        tv_sex = findViewById(R.id.actiThongTinNguoiDung_tv_sex);
        tv_address = findViewById(R.id.actiThongTinNguoiDung_tv_address);
        toolbar = findViewById(R.id.toolbar_thong_tin);
        img1 = findViewById(R.id.actiThongTinNguoiDung_img_updateInfo);
        tv_tb = findViewById(R.id.actiThongTinNguoiDung_tv_tb);
    }

    @Override
    protected void onResume() {
        super.onResume();
        upData();
    }

    private void upData() {
        People people = dao.getUserBy(sdt);
        tv_name.setText(people.getFullName());
        tv_cccd.setText(people.getCCCD());
        tv_sdt.setText(people.getSDT());
        if (people.getSex() == 1) {
            tv_sex.setText("Nam");
        } else {
            tv_sex.setText("Nữ");
        }
        tv_address.setText(people.getAddress());
        String name = people.getFullName();
        int index = name.lastIndexOf(" ") + 1;
        if(index > 1)
            name = name.substring(index);
        tv_welcome.setText("Welcome to " + name);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void handleActionUpdate(View view) {
        Intent intent = new Intent(ThongTinNguoiDungActivity.this, UpdateThongTinActivity.class);
        startActivity(intent);
    }

    //

}