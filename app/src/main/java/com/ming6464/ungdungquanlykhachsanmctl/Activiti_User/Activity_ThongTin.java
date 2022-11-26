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
import android.widget.ImageView;
import android.widget.TextView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

public class Activity_ThongTin extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img1;
    KhachSanDB db;
    TextView tvTen, tvSdt, tvCccd, tvSex, tvDiaChi;
    String u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        toolbar = findViewById(R.id.toolbar_thong_tin);
        img1 = findViewById(R.id.img_update_thongtin);
        toolbar.setTitle("Thông Tin Người Dùng");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //
        db = KhachSanDB.getInstance(this);


        //update thong tin
        img1.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ThongTin.this, Update_ThongTin.class);
            intent.putExtra("s", u);
            Log.d("zzz", "onCreate: " + u);
            startActivity(intent);
        });

        tvTen = findViewById(R.id.tv_user_thongtin);
        tvCccd = findViewById(R.id.tv_cccd_thongtin);
        tvSdt = findViewById(R.id.tv_sdt_thongtin);
        tvSex = findViewById(R.id.tv_sex_thongtin);
        tvDiaChi = findViewById(R.id.tv_address_thongtin);
        u = getIntent().getStringExtra("u");
        People people = db.getDAO().getUserBy(u);
        tvTen.setText(people.getFullName());
        tvCccd.setText(people.getCCCD());
        tvSdt.setText(people.getSDT());
        if (people.getSex() == 1) {
            tvSex.setText("Nam");
        } else {
            tvSex.setText("Nữ");
        }
        tvDiaChi.setText(people.getAddress());

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

    //

}