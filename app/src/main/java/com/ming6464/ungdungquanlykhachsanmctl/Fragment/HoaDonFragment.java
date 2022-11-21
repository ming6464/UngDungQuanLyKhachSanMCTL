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

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.HoaDonAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.UserAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.HoaDon;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.List;

public class HoaDonFragment extends Fragment {
    public RecyclerView rcv_hoaDon;

    private HoaDonAdapter hoaDonAdapter;
    private List<HoaDon> mListHoadon;

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
        rcv_hoaDon= view.findViewById(R.id.rcv_hoaDon);

        hoaDonAdapter = new HoaDonAdapter();
        mListHoadon = new ArrayList<>();
        hoaDonAdapter.setData(mListHoadon);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_hoaDon.setLayoutManager(linearLayoutManager);
        rcv_hoaDon.setAdapter(hoaDonAdapter);

        mListHoadon = KhachSanDB.getInstance(getContext()).getDAO().getListHoaDon();
        hoaDonAdapter.setData(mListHoadon);
    }
}