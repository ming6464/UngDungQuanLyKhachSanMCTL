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
    private LinearLayoutCompat linear_searchRooms;
    private SimpleDateFormat sdf,sdf1,sdf2;
    private Calendar calendar;
    private Date d_checkIn,d_checkOut,now;
    private boolean check = false;
    private String stringTime;
    private FloatingActionButton float_change;
    private ProgressBar pb_load;
    private TextView tv_checkIn,tv_checkOut,tv_showAll,tv_hourCheckIn,tv_hourCheckOut;
    private ImageView img_search;
    public static final String KEY_BUNDLE = "KEY_BUNDLE",KEY_ROOM ="KEY_ROOM",KEY_CHECKIN = "KEY_CHECKIN",KEY_CHECKOUT ="KEY_CHECKOUT",KEY_STATUS = "KEY_STATUS";
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
        calendar = Calendar.getInstance();
        d_checkOut = new Date();
        d_checkIn = new Date();
        now = new Date();
        sdf = new SimpleDateFormat("dd/MM/yyyy HH");
        sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        sdf2 = new SimpleDateFormat("HH");
        dao = KhachSanDB.getInstance(getContext()).getDAO();
        tv_checkIn = view.findViewById(R.id.fragPhong_tv_checkIn);
        tv_hourCheckIn = view.findViewById(R.id.fragPhong_tv_hourCheckIn);
        tv_checkOut = view.findViewById(R.id.fragPhong_tv_checkOut);
        tv_hourCheckOut = view.findViewById(R.id.fragPhong_tv_hourCheckOut);
        tv_showAll = view.findViewById(R.id.fragPhong_tv_showAll);
        img_search = view.findViewById(R.id.fragPhong_img_search);
        rcvRooms = view.findViewById(R.id.rcv_rooms);
        pb_load= view.findViewById(R.id.fragPhong_pg_load);
        linear_searchRooms = view.findViewById(R.id.fagPhong_linear_searchRooms);
        float_change = view.findViewById(R.id.fragPhong_float_change);
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
        Long time = System.currentTimeMillis();
        if(time % 3600000 > 40)
            time += 3600000;
        time -= time % 3600000;
        calendar.setTimeInMillis(time);
        now.setTime(time);
        d_checkOut.setTime(time + 3600000 * 24);
        d_checkIn.setTime(time);
        tv_checkIn.setText(sdf1.format(d_checkIn));
        tv_hourCheckIn.setText(sdf2.format(d_checkIn) + "h");
        tv_checkOut.setText(sdf1.format(d_checkOut));
        tv_hourCheckOut.setText(sdf2.format(d_checkOut) + "h");
        handleFilterRoom();
    }
    @Override
    public void datPhong(Rooms rooms) {
        int status = rooms.getStatus();
        if(status == 0  && !check){
            new AlertDialog.Builder(getContext())
                    .setTitle("Xác nhận đặt phòng")
                    .setMessage("Bạn có muốn đặt phòng không?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(requireContext(), ChucNangDatPhongActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(KEY_ROOM,rooms.getId());
                            bundle.putLong(KEY_CHECKIN,d_checkIn.getTime());
                            bundle.putLong(KEY_CHECKOUT,d_checkOut.getTime());
                            int status = 0;
                            if(now.getTime() < d_checkIn.getTime())
                                status  = 2;
                            bundle.putInt(KEY_STATUS,status);
                            intent.putExtra(KEY_BUNDLE,bundle);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
        }else if(status == 1){
            CustomToast.makeText(requireContext(),"Chi tiết phòng !",true).show();
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
        if(check)
            mListRooms = dao.getAllOfRooms();
        else {
            try {
                d_checkIn = sdf.parse(tv_checkIn.getText().toString() + " " + tv_hourCheckIn.getText().toString());
                d_checkOut = sdf.parse(tv_checkOut.getText().toString() + " " + tv_hourCheckOut.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date = new Date(d_checkIn.getTime());
            mListRooms = dao.getListRoomEmptyWithTime(date,d_checkOut);
            for(Rooms x : mListRooms){
                x.setStatus(0);
            }
        }
        showLoad();
        List<String> listCategory = dao.getListNameCategoryWithRoomId(mListRooms);
        roomsAdapter.setData(mListRooms, listCategory);
        
    }
    private void updateTime(String text,boolean isCheckOut,boolean isTime){
        Date date,date1;
        try{
            date = sdf.parse(text);
            if(isCheckOut){
                date1 = sdf.parse(tv_checkIn.getText() + " " + tv_hourCheckIn.getText().toString());
                if(date.getTime() > date1.getTime()){
                    if(isTime)
                        tv_hourCheckOut.setText(text.substring(text.indexOf(" ") + 1) + "h");
                    else
                        tv_checkOut.setText(text.substring(0,text.indexOf(" ")));
                }
            }else{
                date1 = sdf.parse(tv_checkOut.getText() + " " + tv_hourCheckOut.getText().toString());
                if(date.getTime() >= now.getTime() && date.getTime() < date1.getTime()){
                    if(isTime)
                        tv_hourCheckIn.setText(text.substring(text.indexOf(" ") + 1) + "h");
                    else
                        tv_checkIn.setText(text.substring(0,text.indexOf(" ")));
                }
            }

            }catch (Exception e){
        }
    }
    private void showOrHideRooms(){
        if(check){
            linear_searchRooms.setVisibility(View.VISIBLE);
            tv_showAll.setVisibility(View.GONE);
            check = false;
            return;
        }
        linear_searchRooms.setVisibility(View.GONE);
        tv_showAll.setVisibility(View.VISIBLE);
        check = true;
    }

    private void handleAction() {
        tv_checkIn.setOnClickListener(this);
        tv_hourCheckIn.setOnClickListener(this);
        tv_checkOut.setOnClickListener(this);
        tv_hourCheckOut.setOnClickListener(this);
        float_change.setOnClickListener(this);
        img_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragPhong_tv_checkIn:
                calendar.setTime(d_checkIn);
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        stringTime = tv_hourCheckIn.getText().toString();
                        updateTime(dayOfMonth + "/" + (month + 1) + "/" + year + " " + stringTime.substring(0, stringTime.length() - 1), false, false);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.fragPhong_tv_hourCheckIn:
                calendar.setTime(d_checkIn);
                TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        updateTime(tv_checkIn.getText().toString() + " " + hourOfDay, false, true);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY) + 1, 0, true);
                timePickerDialog.show();
                break;
            case R.id.fragPhong_tv_checkOut:
                calendar.setTime(d_checkOut);
                datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        stringTime = tv_hourCheckOut.getText().toString();
                        updateTime(dayOfMonth +"/" +(month + 1) + "/" +year + " " + stringTime.substring(0,stringTime.length() - 1), true,false);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.fragPhong_tv_hourCheckOut:
                calendar.setTime(d_checkOut);
                timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        updateTime(tv_checkIn.getText().toString() + " " + hourOfDay, true,true);
                    }
                },calendar.get(Calendar.HOUR_OF_DAY) + 1,0,true);
                timePickerDialog.show();
                break;
            case R.id.fragPhong_img_search:
                handleFilterRoom();
                break;
            default:
                showOrHideRooms();
                handleFilterRoom();
        }
    }
}