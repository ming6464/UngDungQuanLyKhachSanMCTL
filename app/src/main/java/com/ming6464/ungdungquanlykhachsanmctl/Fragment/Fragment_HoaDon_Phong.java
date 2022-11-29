package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.OrderDetailAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_HoaDon_Phong extends Fragment implements OrderDetailAdapter.OnEventOfOrderDetailAdpater {
    private List<OrderDetail> list;
    private KhachSanDAO dao;
    private RecyclerView rc_orderDetail;
    private OrderDetailAdapter adapter;
    public static Fragment_HoaDon_Phong newInstance() {
        Fragment_HoaDon_Phong fragment = new Fragment_HoaDon_Phong();
        return fragment;
    }

    public Fragment_HoaDon_Phong() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__hoa_don__phong, container, false);
        return view;
        //Hello Các bạn
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        rc_orderDetail = view.findViewById(R.id.fragHoaDonPhong_rc);
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        adapter = new OrderDetailAdapter(requireContext(),this);
        rc_orderDetail.setAdapter(adapter);
        rc_orderDetail.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        for(OrderDetail x : dao.getAllOfOrderDetail()){
            if(x.getStatus() != 1)
                list.add(x);
        }
        adapter.setData(list);
    }

    @Override
    public void click(int position) {

    }
}