package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.RoomsAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.UserAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.ChucNangDatPhongActivity;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.List;

public class PhongFragment extends Fragment implements RoomsAdapter.IClickItemRooms {

    public RecyclerView rcvRooms;
    private RoomsAdapter roomsAdapter;
    private List<Rooms> mListRooms;
    private List<String> listCategory;
    public static final String KEY_MAPHONG = "maphongkey";
    private KhachSanDAO dao;

    public static PhongFragment newInstance() {
        PhongFragment fragment = new PhongFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = KhachSanDB.getInstance(getContext()).getDAO();
        rcvRooms = view.findViewById(R.id.rcv_rooms);

        roomsAdapter = new RoomsAdapter(this);
        mListRooms = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        rcvRooms.setLayoutManager(gridLayoutManager);
        rcvRooms.setAdapter(roomsAdapter);
        mListRooms = dao.getAllOfRooms();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListRooms = dao.getAllOfRooms();
        listCategory = dao.getListNameCategoryWithRoomId(mListRooms);
        roomsAdapter.setData(mListRooms,listCategory);
    }

    @Override
    public void datPhong(Rooms rooms) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận đặt phòng")
                .setMessage("Bạn có muốn đặt phòng không?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(requireContext(), ChucNangDatPhongActivity.class);
                        intent.putExtra(KEY_MAPHONG,rooms.getId());
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
}