package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemHoaDonAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.HoaDonChiTietActivity;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonTongFragment extends Fragment implements ItemHoaDonAdapter.EventOfOrderAdapter {
    private Spinner sp_status;
    private RecyclerView rc_hoaDon;
    private List<Orders> orderList;
    private SwipeRefreshLayout rf_rcHoaDon;
    private ItemHoaDonAdapter orderAdapter;
    private KhachSanDAO dao;
    public static final String KEY_ORDER = "KEY_ORDER";
    public static HoaDonTongFragment newInstance() {
        return new HoaDonTongFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoa_don_tong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        rc_hoaDon = view.findViewById(R.id.fagHoaDonTong_rc_hoaDon);
        sp_status = view.findViewById(R.id.fragHoaDonTong_sp_status);
        rf_rcHoaDon = view.findViewById(R.id.fragHoaDonTong_rf_rcHoaDon);
        handleRcHoaDon();
        handleSpinner();
        handleAction();
    }

    private void handleAction() {
        rf_rcHoaDon.setOnRefreshListener(() -> {
            loadData(sp_status.getSelectedItemPosition());
            rf_rcHoaDon.setRefreshing(false);
        });
    }

    private void handleSpinner() {
        List<String> statusList = new ArrayList<>();
        statusList.add("Chưa Thanh Toán");
        statusList.add("Thanh Toán");
        statusList.add("Huỷ");
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,statusList);
        sp_status.setAdapter(arrayAdapter);
        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadData(int position) {
        long currentTime = System.currentTimeMillis();
        for(OrderDetail x : dao.getAllOfOrderDetail()){
            switch (x.getStatus()){
                case 0:
                    if(x.getCheckOut().getTime() < currentTime){
                        x.setStatus(1);
                        dao.updateOfOrderDetail(x);
                    }
                    break;
                case 2:
                    if(x.getCheckIn().getTime() < currentTime){
                        x.setStatus(3);
                        dao.updateOfOrderDetail(x);
                    }
                    break;
                case 3:
                    if(x.getCheckOut().getTime() < currentTime){
                        x.setStatus(4);
                        dao.cancelOfOrderDetail(x.getId(),new Date(System.currentTimeMillis()));
                    }
                    break;
            }
        }
        orderList = dao.getListWithStatusOfOrders(position);
        orderAdapter.setData(orderList);
    }

    private void handleRcHoaDon() {
        orderAdapter = new ItemHoaDonAdapter(requireContext(),this);
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
        loadData(sp_status.getSelectedItemPosition());
    }

}