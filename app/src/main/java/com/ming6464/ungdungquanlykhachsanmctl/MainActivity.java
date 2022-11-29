package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThongTin;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceCategory;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.DichVuFragment;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.FragmentTaiKhoan;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.Fragment_Tab_HoaDon;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.HoaDonFragment;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.KhachHangFragment;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.SoDoFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private KhachSanDAO dao;
    private TextView tv_titleTb;
    private KhachSanSharedPreferences share;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        bottomNavigationView.setItemIconTintList(null);
        goiPhonFragment();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.drawable.home_25);
        addData();
        Click();
    }

    private void addData() {
        if (!share.getCheck2()) {
            share.setCheck2(true);
            dao.insertOfLoaiPhong(new Categories("Standard",100000,2));
            dao.insertOfLoaiPhong(new Categories("Superior",125000,3));
            dao.insertOfLoaiPhong(new Categories("Deluxe",160000,3));
            dao.insertOfLoaiPhong(new Categories("Suite",200000,6));
            //
            dao.insertOfService(new Services("Trông trẻ", 100000));
            dao.insertOfService(new Services("Đánh giày", 20000));
            dao.insertOfService(new Services("Giặt Đồ", 30000));
            dao.insertOfService(new Services("Dịch vụ Spa", 100000));
            dao.insertOfService(new Services("Fitness center", 100000));
            dao.insertOfService(new Services("Xe Đưa Đón Sân Bay", 100000));
            dao.insertOfService(new Services("Ăn tại phòng", 100000));
            dao.insertOfService(new Services("Hội Họp, Văn Phòng", 500000));
            //rooms
            dao.insertOfRooms(new Rooms("101", 1));
            dao.insertOfRooms(new Rooms("102", 2));
            dao.insertOfRooms(new Rooms("103", 3));
            dao.insertOfRooms(new Rooms("104", 4));
            dao.insertOfRooms(new Rooms("201", 1));
            dao.insertOfRooms(new Rooms("202", 2));
            dao.insertOfRooms(new Rooms("203", 3));
            dao.insertOfRooms(new Rooms("204", 4));
            dao.insertOfRooms(new Rooms("205", 1));
            dao.insertOfRooms(new Rooms("206", 2));
            dao.insertOfRooms(new Rooms("301", 3));
            dao.insertOfRooms(new Rooms("302", 4));
            dao.insertOfRooms(new Rooms("303", 3));
            dao.insertOfRooms(new Rooms("304", 4));
            dao.insertOfRooms(new Rooms("305", 3));
            dao.insertOfRooms(new Rooms("306", 4));
            //
            dao.insertOfServiceCategory(new ServiceCategory(1, 1));
            dao.insertOfServiceCategory(new ServiceCategory(1, 2));
            dao.insertOfServiceCategory(new ServiceCategory(1, 3));
            dao.insertOfServiceCategory(new ServiceCategory(2, 1));
            dao.insertOfServiceCategory(new ServiceCategory(2, 2));
            dao.insertOfServiceCategory(new ServiceCategory(2, 3));
            dao.insertOfServiceCategory(new ServiceCategory(2, 4));
            dao.insertOfServiceCategory(new ServiceCategory(2, 5));
            dao.insertOfServiceCategory(new ServiceCategory(3, 1));
            dao.insertOfServiceCategory(new ServiceCategory(3, 2));
            dao.insertOfServiceCategory(new ServiceCategory(3, 3));
            dao.insertOfServiceCategory(new ServiceCategory(3, 4));
            dao.insertOfServiceCategory(new ServiceCategory(3, 5));
            dao.insertOfServiceCategory(new ServiceCategory(3, 6));
            dao.insertOfServiceCategory(new ServiceCategory(3, 7));
            dao.insertOfServiceCategory(new ServiceCategory(4, 1));
            dao.insertOfServiceCategory(new ServiceCategory(4, 2));
            dao.insertOfServiceCategory(new ServiceCategory(4, 3));
            dao.insertOfServiceCategory(new ServiceCategory(4, 4));
            dao.insertOfServiceCategory(new ServiceCategory(4, 5));
            dao.insertOfServiceCategory(new ServiceCategory(4, 6));
            dao.insertOfServiceCategory(new ServiceCategory(4, 7));
            dao.insertOfServiceCategory(new ServiceCategory(4, 8));
        }
    }

    //sk gọi frm
    private void goiPhonFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SoDoFragment()).commit();
    }

    //sk click
    private void Click() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                String title = null;
                int logo = 0;
                switch (item.getItemId()) {
                    case R.id.menu_bottom1:
                        title = "Sơ Đồ Phòng";
                        logo = R.drawable.home_25;
                        fragment = new SoDoFragment();
                        break;
                    case R.id.menu_bottom2:
                        title = "Khách Hàng";
                        logo = R.drawable.customer_25;
                        fragment = new KhachHangFragment();
                        break;
                    case R.id.menu_bottom3:
                        title = "Dịch Vụ";
                        logo = R.drawable.services_24;
                        fragment = new DichVuFragment();
                        break;
                    case R.id.menu_bottom4:
                        title = "Hóa Đơn";
                        logo = R.drawable.order_25;
                        fragment = new Fragment_Tab_HoaDon();
                        break;
                    case R.id.menu_bottom5:
                        title = "Tài Khoản";
                        logo = R.drawable.yourselt_25;
                        fragment = new FragmentTaiKhoan();
                        CustomToast.makeText(MainActivity.this, "Đang Update", false).show();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                tv_titleTb.setText(title);
                toolbar.setLogo(logo);
                return true;
            }
        });
    }

    private void anhXa() {
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        toolbar = findViewById(R.id.actiMain_tb);
        dao = KhachSanDB.getInstance(this).getDAO();
        share = new KhachSanSharedPreferences(this);
        tv_titleTb = findViewById(R.id.actiMain_tv_titleTb);
    }
}