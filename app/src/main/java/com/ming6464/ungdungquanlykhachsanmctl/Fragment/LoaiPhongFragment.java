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

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemLoaiPhongAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class LoaiPhongFragment extends Fragment{
    private ItemLoaiPhongAdapter itemLoaiPhongAdapter;
    private KhachSanDAO dao;
    public static LoaiPhongFragment newInstance() {
        LoaiPhongFragment fragment = new LoaiPhongFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loai_phong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        RecyclerView recyclerView = requireView().findViewById(R.id.fragLoaiPhong_rc);
        itemLoaiPhongAdapter = new ItemLoaiPhongAdapter();
        recyclerView.setAdapter(itemLoaiPhongAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Categories> listLoaiPhong = dao.getAllOfLoaiPhong();
        itemLoaiPhongAdapter.setData(listLoaiPhong);
    }

}