package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.DichVuFragment;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.HoaDonFragment;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.KhachHangFragment;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.SoDoPhongFragment;

public class MainActivity extends AppCompatActivity implements Action {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FragmentManager manager;
    private KhachSanDAO dao;
    private KhachSanSharedPreferences share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        addToolbarNavi();
        addData();
        chuyenFragment(SoDoPhongFragment.newInstance(),manager);
    }
    private void addData() {
        if(!share.booleangetCheckLoaiPhong()){
            dao.insertOfLoaiPhong(new Categories("Standard (STD)",600000,2));
            dao.insertOfLoaiPhong(new Categories("Superior (SUP)",880000,3));
            dao.insertOfLoaiPhong(new Categories("Deluxe (DLX)",1420000,2));
            dao.insertOfLoaiPhong(new Categories("Suite (SUT)",2350000,4));
            share.setCheckLoaiPhong(true);
            dao.insertOfService(new Services("Giat Đồ",99999));
            dao.insertOfService(new Services("Xe Đưa Đón Sân Bay",1823));
            dao.insertOfService(new Services("Hội Họp, Văn Phòng",2374324));
            dao.insertOfService(new Services("Dịch vụ Spa",800000));
            dao.insertOfService(new Services("Fitness center",1000000));
            //insert khach hang
            dao.insertRooms(new Rooms("102",0));
            dao.insertRooms(new Rooms("304",2));
            dao.insertRooms(new Rooms("203",1));
            dao.insertRooms(new Rooms("206",2));
        }
    }

    private void addToolbarNavi() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navi,R.string.close_navi);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(item -> {
            manager = getSupportFragmentManager();
            switch (item.getItemId()){
                case R.id.naviMenu_nv_soDoPhong:
                    chuyenFragment(SoDoPhongFragment.newInstance(),manager);
                    break;
                case R.id.naviMenu_nv_dichVu:
                    chuyenFragment(DichVuFragment.newInstance(),manager);
                    break;
                case R.id.naviMenu_nv_hoaDon:
                    chuyenFragment(HoaDonFragment.newInstance(),manager);
                    break;
                case R.id.naviMenu_nv_khachHang:
                    chuyenFragment(KhachHangFragment.newInstance(),manager);
                    break;
            }
            drawerLayout.closeDrawer(navigationView);
            return true;
        });
    }
    private void anhXa() {
        drawerLayout = findViewById(R.id.actiMain_layut_drawer);
        toolbar = findViewById(R.id.actiMain_tb);
        navigationView = findViewById(R.id.actiMain_nv);
        manager = getSupportFragmentManager();
        dao = KhachSanDB.getInstance(this).getDAO();
        share = new KhachSanSharedPreferences(this);
    }
    @Override
    public void chuyenFragment(Fragment fragment, FragmentManager manager) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.actiMain_layout_linear,fragment);
        transaction.commit();
    }
}