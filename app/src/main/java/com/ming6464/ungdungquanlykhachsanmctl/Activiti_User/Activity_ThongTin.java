package com.ming6464.ungdungquanlykhachsanmctl.Activiti_User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanSharedPreferences;
import com.ming6464.ungdungquanlykhachsanmctl.R;

public class Activity_ThongTin extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img1;
    KhachSanDAO dao;
    String sdt;
    TextView tvTen, tvSdt, tvCccd, tvSex, tvDiaChi, tvTitle, tvTbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        dao = KhachSanDB.getInstance(this).getDAO();
        anhXa();
        sdt = new KhachSanSharedPreferences(this).getSDT2();
        tvTbName.setText("Thông Tin Người Dùng");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void anhXa() {
        tvTitle = findViewById(R.id.tv_title_thongTin);
        tvTen = findViewById(R.id.tv_user_thongtin);
        tvCccd = findViewById(R.id.tv_cccd_thongtin);
        tvSdt = findViewById(R.id.tv_sdt_thongtin);
        tvSex = findViewById(R.id.tv_sex_thongtin);
        tvDiaChi = findViewById(R.id.tv_address_thongtin);
        toolbar = findViewById(R.id.toolbar_thong_tin);
        img1 = findViewById(R.id.img_update_thongtin);
        tvTbName = findViewById(R.id.actiThongTin_tv_tbName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        upData();
    }

    private void upData() {
        People people = dao.getUserBy(sdt);
        tvTen.setText(people.getFullName());
        tvCccd.setText(people.getCCCD());
        tvSdt.setText(people.getSDT());
        if (people.getSex() == 1) {
            tvSex.setText("Nam");
        } else {
            tvSex.setText("Nữ");
        }
        tvDiaChi.setText(people.getAddress());
        String name = people.getFullName();
        int index = name.lastIndexOf(" ") + 1;
        if(index > 1)
            name = name.substring(index);
        tvTitle.setText("Welcome to " + name);
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
        Intent intent = new Intent(Activity_ThongTin.this, Update_ThongTin.class);
        startActivity(intent);
    }

    //

}