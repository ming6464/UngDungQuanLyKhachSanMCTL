package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemDichVuAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;
import java.util.ArrayList;
import java.util.List;

public class DichVuFragment extends Fragment {
    public static DichVuFragment newInstance() {
        return new DichVuFragment();
    }

    RecyclerView recyclerView;
    List<Services> list;
    ItemDichVuAdapter itemDichVuAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dich_vu, container, false);
        recyclerView = view.findViewById(R.id.fragDichVu_rc);

        //
        itemDichVuAdapter = new ItemDichVuAdapter(requireContext(),list);
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        list = KhachSanDB.getInstance(getContext()).getDAO().getAllService();
        itemDichVuAdapter.setData(list);
        recyclerView.setAdapter(itemDichVuAdapter);

        return view;
    }

}