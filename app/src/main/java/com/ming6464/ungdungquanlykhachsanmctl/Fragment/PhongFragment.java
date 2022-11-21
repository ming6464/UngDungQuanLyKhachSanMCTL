package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.RoomsAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.ChucNangDatPhongActivity;
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
    public static final String KEY_MAPHONG = "maphongkey",KEY_STATUS ="STATUS_room";
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
        int status = rooms.getStatus();
        if(status == 0 ){
            new AlertDialog.Builder(getContext())
                    .setTitle("Xác nhận đặt phòng")
                    .setMessage("Bạn có muốn đặt phòng không?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(requireContext(), ChucNangDatPhongActivity.class);
                            intent.putExtra(KEY_MAPHONG,rooms.getId());
                            intent.putExtra(KEY_STATUS,rooms.getStatus());
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
        }else if(status == 1){
            Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_phong_conguoi);
            Button btn_checkOut = dialog.findViewById(R.id.dialogPCN_btn_checkOut),
                    btn_schedule = dialog.findViewById(R.id.dialogPCN_btn_schedule)
                    ,btn_detail = dialog.findViewById(R.id.dialogPCN_btn_detail);
            btn_checkOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(requireContext(), "Trả phòng !", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            btn_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(requireContext(), "Xem lịch đặt trước !", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(requireContext(), "Xem chi tiết", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.getAttributes().windowAnimations = R.style.dialog_slide_bottom;
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        else if( status == 2){
            Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_phong_dattruoc);
            Button btn_booked = dialog.findViewById(R.id.dialogPDT_btn_booked)
                    ,btn_checkIn = dialog.findViewById(R.id.dialogPDT_btn_checkIn)
                    ,btn_schedule = dialog.findViewById(R.id.dialogPDT_btn_schedule)
                    ,btn_detail = dialog.findViewById(R.id.dialogPDT_btn_detail);
            btn_checkIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(requireContext(), "Nhận phòng !", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            btn_booked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(requireContext(), ChucNangDatPhongActivity.class);
                    intent.putExtra(KEY_MAPHONG,rooms.getId());
                    intent.putExtra(KEY_STATUS,rooms.getStatus());
                    startActivity(intent);
                    dialog.cancel();
                }
            });
            btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(requireContext(), "Xem chi tiết", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            btn_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(requireContext(), "Xem danh sách đặt trước", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.getAttributes().windowAnimations = R.style.dialog_slide_bottom;
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}