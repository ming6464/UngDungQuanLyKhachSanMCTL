package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.AdapterDichVu;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.List;

public class DichVuFragment extends Fragment {
    public static DichVuFragment newInstance() {
        DichVuFragment fragment = new DichVuFragment();
        return fragment;
    }

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    List<Services> list;
    AdapterDichVu adapterDichVu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dich_vu, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewDichVu);

        //
        adapterDichVu = new AdapterDichVu(getContext(), list);
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        list = KhachSanDB.getInstance(getContext()).getDAO().getAllService();
        adapterDichVu.setData(list);
        recyclerView.setAdapter(adapterDichVu);

        return view;
    }

}