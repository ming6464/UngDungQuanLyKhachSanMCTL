package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.HoaDon;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceCategory;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.DichVuFragment;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.HoaDonFragment;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.KhachHangFragment;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.SoDoPhongFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            share.setCheckLoaiPhong(true);
            dao.insertOfLoaiPhong(new Categories("Standard",600000,2));
            dao.insertOfLoaiPhong(new Categories("Superior",880000,3));
            dao.insertOfLoaiPhong(new Categories("Deluxe",1420000,2));
            dao.insertOfLoaiPhong(new Categories("Suite",2350000,4));
            //
            dao.insertOfService(new Services("Trông trẻ",100000));
            dao.insertOfService(new Services("Đánh giày",20000));
            dao.insertOfService(new Services("Giặt Đồ",30000));
            dao.insertOfService(new Services("Dịch vụ Spa",100000));
            dao.insertOfService(new Services("Fitness center",100000));
            dao.insertOfService(new Services("Xe Đưa Đón Sân Bay",100000));
            dao.insertOfService(new Services("Ăn tại phòng",100000));
            dao.insertOfService(new Services("Hội Họp, Văn Phòng",500000));
            //rooms
            dao.insertOfRooms(new Rooms("101",1));
            dao.insertOfRooms(new Rooms("102",2));
            dao.insertOfRooms(new Rooms("103",3));
            dao.insertOfRooms(new Rooms("104",4));
            dao.insertOfRooms(new Rooms("201",1));
            dao.insertOfRooms(new Rooms("202",2));
            dao.insertOfRooms(new Rooms("203",3));
            dao.insertOfRooms(new Rooms("204",4));
            dao.insertOfRooms(new Rooms("205",1));
            dao.insertOfRooms(new Rooms("206",2));
            dao.insertOfRooms(new Rooms("301",3));
            dao.insertOfRooms(new Rooms("302",4));
            dao.insertOfRooms(new Rooms("303",3));
            dao.insertOfRooms(new Rooms("304",4));
            dao.insertOfRooms(new Rooms("305",3));
            dao.insertOfRooms(new Rooms("306",4));
            //
            dao.insertOfServiceCategory(new ServiceCategory(1,1));
            dao.insertOfServiceCategory(new ServiceCategory(1,2));
            dao.insertOfServiceCategory(new ServiceCategory(1,3));
            dao.insertOfServiceCategory(new ServiceCategory(2,1));
            dao.insertOfServiceCategory(new ServiceCategory(2,2));
            dao.insertOfServiceCategory(new ServiceCategory(2,3));
            dao.insertOfServiceCategory(new ServiceCategory(2,4));
            dao.insertOfServiceCategory(new ServiceCategory(2,5));
            dao.insertOfServiceCategory(new ServiceCategory(3,1));
            dao.insertOfServiceCategory(new ServiceCategory(3,2));
            dao.insertOfServiceCategory(new ServiceCategory(3,3));
            dao.insertOfServiceCategory(new ServiceCategory(3,4));
            dao.insertOfServiceCategory(new ServiceCategory(3,5));
            dao.insertOfServiceCategory(new ServiceCategory(3,6));
            dao.insertOfServiceCategory(new ServiceCategory(3,7));
            dao.insertOfServiceCategory(new ServiceCategory(4,1));
            dao.insertOfServiceCategory(new ServiceCategory(4,2));
            dao.insertOfServiceCategory(new ServiceCategory(4,3));
            dao.insertOfServiceCategory(new ServiceCategory(4,4));
            dao.insertOfServiceCategory(new ServiceCategory(4,5));
            dao.insertOfServiceCategory(new ServiceCategory(4,6));
            dao.insertOfServiceCategory(new ServiceCategory(4,7));
            dao.insertOfServiceCategory(new ServiceCategory(4,8));
            //nhân viên
            People people = new People("admin full name","03123","123123","hn",1,1);
            people.setPassowrd("123");
            share.setAccount(people,true);
            dao.insertOfUser(people);
            //user
            dao.insertOfUser(new People("ten1","sdt1","cccd1","hn",1,0));
            dao.insertOfUser(new People("ten2","sdt2","cccd2","hn1",0,0));
            dao.insertOfUser(new People("ten3","sdt3","cccd3","hn2",1,0));
            dao.insertOfUser(new People("ten4","sdt4","cccd4","hn3",0,0));
            dao.insertOfUser(new People("ten5","sdt5","cccd5","hn4",1,0));
            //orders
            dao.insertOfOrders(new Orders(1,Integer.parseInt(share.getID2()),null));
            dao.insertOfOrders(new Orders(2,Integer.parseInt(share.getID2()),null));
            dao.insertOfOrders(new Orders(3,Integer.parseInt(share.getID2()),null));
            dao.insertOfOrders(new Orders(4,Integer.parseInt(share.getID2()),null));
            dao.insertOfOrders(new Orders(5,Integer.parseInt(share.getID2()),null));
            //
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d1 = null,d2 = null;
            try {
                d1 = sdf.parse("14/11/2022");
                d2 = sdf.parse("16/11/2022");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //
            //orderDetail
            dao.insertOfOrderDetail(new OrderDetail(1,1,2,d1,d2));
            dao.insertOfOrderDetail(new OrderDetail(2,2,2,d1,d2));
            dao.insertOfOrderDetail(new OrderDetail(6,3,2,d1,d2));
            dao.insertOfOrderDetail(new OrderDetail(8,4,2,d1,d2));
            dao.insertOfOrderDetail(new OrderDetail(14,5,2,d1,d2));

            //hoaDon
            dao.insertHoaDon(new HoaDon(1,"toi","17/11/2022","20/11/2022","phong 201","phong 01","Đặt trước",200000));
            dao.insertHoaDon(new HoaDon(2,"chien","19/11/2022","23/11/2022","phong 202","phong 02","Hủy phòng",2400000));
            dao.insertHoaDon(new HoaDon(3,"tu","22/11/2022","23/11/2022","phong 203","phong 03","Thanh toán",250000));
            dao.insertHoaDon(new HoaDon(4,"quy","20/11/2022","26/11/2022","phong 204","phong 04","Chưa thanh toán",370000));
            dao.insertHoaDon(new HoaDon(5,"hien","20/11/2022","22/11/2022","phong 205","phong 05","Thanh toán",220000));
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