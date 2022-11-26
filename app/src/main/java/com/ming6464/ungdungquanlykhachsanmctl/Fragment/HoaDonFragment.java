package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.OrderAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.HoaDonChiTietActivity;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.List;

public class HoaDonFragment extends Fragment implements OrderAdapter.EventOfOrderAdapter {
    private Spinner sp_status;
    private RecyclerView rc_hoaDon;
    private List<Orders> orderList;
    private OrderAdapter orderAdapter;
    private KhachSanDAO dao;
    public static final String KEY_ORDER = "KEY_ORDER";
    public static HoaDonFragment newInstance() {
        HoaDonFragment fragment = new HoaDonFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        rc_hoaDon = view.findViewById(R.id.fagHoaDon_rc_hoaDon);
        sp_status = view.findViewById(R.id.fragHoaDon_sp_status);
        handlerRcHoaDon();
        handlerSpinner();
    }

    private void handlerSpinner() {
        List<String> statusList = new ArrayList<>();
        statusList.add("Tất Cả");
        statusList.add("Chưa Thanh Toán");
        statusList.add("Thanh Toán");
        statusList.add("Đặt Trước");
        statusList.add("Huỷ");
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,statusList);
        sp_status.setAdapter(arrayAdapter);
        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    orderList = dao.getAllOfOrders();
                    orderAdapter.setData(orderList);
                }else {
                    orderList = dao.getListWithStatusOfOrders(position - 1);
                    orderAdapter.setData(orderList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void handlerRcHoaDon() {
        orderAdapter = new OrderAdapter(requireContext(),this);
        rc_hoaDon.setAdapter(orderAdapter);
        rc_hoaDon.setLayoutManager(new LinearLayoutManager(requireContext()));
    }


    @Override
    public void show(int position) {
        Intent intent = new Intent(requireContext(), HoaDonChiTietActivity.class);
        intent.putExtra(KEY_ORDER,orderList.get(position));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        orderList = dao.getAllOfOrders();
        orderAdapter.setData(orderList);
    }
}