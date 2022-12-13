package com.ming6464.ungdungquanlykhachsanmctl.Activiti_User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
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
    private Toolbar toolbar;
    private KhachSanDAO dao;
    private String sdt;
    private TextView tv_name, tv_sdt, tv_cccd, tv_sex, tv_address, tv_welcome;
    private ImageView img_avatar,img_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);
        sdt = new KhachSanSharedPreferences(this).getSDT2();
        dao = KhachSanDB.getInstance(this).getDAO();
        anhXa();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addAction();
    }

    private void addAction() {
        img_edit.setOnClickListener(v -> {
            Intent intent = new Intent(ThongTinNguoiDungActivity.this, UpdateThongTinActivity.class);
            startActivity(intent);
        });
    }

    private void anhXa() {
        tv_welcome = findViewById(R.id.actiThongTinNguoiDung_tv_welcome);
        tv_name = findViewById(R.id.actiThongTinNguoiDung_tv_name);
        tv_cccd = findViewById(R.id.actiThongTinNguoiDung_tv_cccd);
        tv_sdt = findViewById(R.id.actiThongTinNguoiDung_tv_sdt);
        tv_sex = findViewById(R.id.actiThongTinNguoiDung_tv_sex);
        tv_address = findViewById(R.id.actiThongTinNguoiDung_tv_address);
        img_avatar = findViewById(R.id.actiThongTinNguoiDung_img_avatar);
        toolbar = findViewById(R.id.actiThongTinNguoiDung_tb);
        img_edit = findViewById(R.id.actiThongTinNguoidung_img_edit);
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
        if (people.getSex() == 0){
            tv_sex.setText("Nữ");
            img_avatar.setImageResource(R.drawable.businesswoman_100);
        }
        tv_address.setText(people.getAddress());
        String name = people.getFullName();
        int index = name.lastIndexOf(" ") + 1;
        if(index > 1)
            name = name.substring(index);
        tv_welcome.setText("Welcome to " + name);
        if(people.getStatus() != 2)
            img_edit.setVisibility(View.GONE);
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

}