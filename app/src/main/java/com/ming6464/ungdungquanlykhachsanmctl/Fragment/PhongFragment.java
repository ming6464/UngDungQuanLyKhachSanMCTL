package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.RoomsAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.ChucNangDatPhongActivity;
import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PhongFragment extends Fragment implements RoomsAdapter.IClickItemRooms, View.OnClickListener {

    public RecyclerView rcvRooms;
    private RoomsAdapter roomsAdapter;
    private List<Rooms> mListRooms;
    private SimpleDateFormat sdf,sdf1;
    private Calendar calendar;
    private Date d_checkIn,d_checkOut;
    private ProgressBar pb_load;
    private TextView tv_checkIn,tv_checkOut;
    public static final String KEY_BUNDLE = "KEY_BUNDLE",KEY_ROOM ="KEY_ROOM",
            KEY_CHECKIN = "KEY_CHECKIN",KEY_CHECKOUT ="KEY_CHECKOUT",
            KEY_STATUS = "KEY_STATUS",KEY_AMOUNT_DATE = "KEY_AMOUNT_DATE";
    private KhachSanDAO dao;

    public static PhongFragment newInstance() {
        return new PhongFragment();
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
        calendar = Calendar.getInstance();
        d_checkOut = new Date();
        d_checkIn = new Date();
        sdf = new SimpleDateFormat("dd/MM/yy");
        sdf1 = new SimpleDateFormat("dd/MM/yy HH");
        dao = KhachSanDB.getInstance(getContext()).getDAO();
        tv_checkIn = view.findViewById(R.id.fragPhong_tv_checkIn);
        tv_checkOut = view.findViewById(R.id.fragPhong_tv_checkOut);
        rcvRooms = view.findViewById(R.id.rcv_rooms);
        pb_load= view.findViewById(R.id.fragPhong_pg_load);
        roomsAdapter = new RoomsAdapter(this);
        mListRooms = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        rcvRooms.setLayoutManager(gridLayoutManager);
        rcvRooms.setAdapter(roomsAdapter);
        mListRooms = dao.getAllOfRooms();
        handleAction();
    }

    @Override
    public void onResume() {
        super.onResume();
        long time = 0;
        try {
            time = sdf.parse(sdf.format(new Date(System.currentTimeMillis()))).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTimeInMillis(time);
        d_checkOut.setTime(time + 3600000 * 36);
        d_checkIn.setTime(time + 3600000 * 14);
        tv_checkIn.setText(sdf.format(d_checkIn));
        tv_checkOut.setText(sdf.format(d_checkOut));
        handleFilterRoom();
    }
    @Override
    public void datPhong(Rooms rooms) {
        int status = rooms.getStatus();
        if(status == 0){
            new AlertDialog.Builder(getContext())
                    .setTitle("Xác nhận đặt phòng")
                    .setMessage("Bạn có muốn đặt phòng không?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(requireContext(), ChucNangDatPhongActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(KEY_ROOM,rooms.getId());
                            bundle.putLong(KEY_CHECKIN,d_checkIn.getTime());
                            bundle.putLong(KEY_CHECKOUT,d_checkOut.getTime());
                            bundle.putInt(KEY_AMOUNT_DATE,(int)(d_checkOut.getTime() - d_checkIn.getTime())/(3600000*24) + 1);
                            int status = 0;
                            if(System.currentTimeMillis() < (d_checkIn.getTime()))
                                status  = 2;
                            bundle.putInt(KEY_STATUS,status);
                            intent.putExtra(KEY_BUNDLE,bundle);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Huỷ",null)
                    .show();
        }
    }
    private void showLoad(){
        rcvRooms.setVisibility(View.GONE);
        pb_load.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rcvRooms.setVisibility(View.VISIBLE);
                pb_load.setVisibility(View.GONE);
            }
        },700);
    }
    private void handleFilterRoom(){
        try {
            d_checkIn = sdf1.parse(tv_checkIn.getText().toString() + " 14");
            d_checkOut = sdf1.parse(tv_checkOut.getText().toString() + " 12");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mListRooms = dao.getListRoomWithTime(d_checkIn,d_checkOut);
        showLoad();
        List<String> listCategory = dao.getListNameCategoryWithRoomId(mListRooms);
        roomsAdapter.setData(mListRooms, listCategory);
    }

    private void handleAction() {
        tv_checkIn.setOnClickListener(this);
        tv_checkOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragPhong_tv_checkIn:
                calendar.setTime(d_checkIn);
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_checkIn.setText(dayOfMonth + "/" + (month + 1) + "/" + String.valueOf(year).substring(2));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            default:
                calendar.setTime(d_checkOut);
                datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_checkOut.setText(dayOfMonth + "/" + (month + 1) + "/" + String.valueOf(year).substring(2));
                        handleFilterRoom();
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
        }
    }
}