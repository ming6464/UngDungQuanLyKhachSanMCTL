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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.RoomsAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.ChucNangDatPhongActivity;
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

public class PhongFragment extends Fragment implements RoomsAdapter.IClickItemRooms {

    public RecyclerView rcvRooms;
    private RoomsAdapter roomsAdapter;
    private List<Rooms> mListRooms;
    private List<String> listCategory;
    private LinearLayoutCompat linear_searchRooms;
    private SimpleDateFormat sdf;
    private String time,time1,time2;
    private Calendar calendar;
    private Date d_checkIn,d_checkOut,now;
    private boolean check = false;
    private String TAG = "tai.g";
    private FloatingActionButton float_change;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private TextView tv_checkIn,tv_checkOut,tv_showAll;
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
        dao = KhachSanDB.getInstance(getContext()).getDAO();
        tv_checkIn = view.findViewById(R.id.fragPhong_tv_checkIn);
        tv_checkOut = view.findViewById(R.id.fragPhong_tv_checkOut);
        tv_showAll = view.findViewById(R.id.fragPhong_tv_showAll);
        img_search = view.findViewById(R.id.fragPhong_img_search);
        rcvRooms = view.findViewById(R.id.rcv_rooms);
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

    private void handleAction() {
        tv_checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setTime(d_checkIn);
                time = tv_checkIn.getText().toString();
                time1 = time.substring(0,time.indexOf(" "));
                time2 = time.substring(time.indexOf(" ") + 1,time.length() - 1);
                datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        time1 = dayOfMonth +"/" +(month + 1) + "/" +year;
                        updateTime(time1 + " " + time2, false);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time2 = String.valueOf(hourOfDay);
                        updateTime(time1 + " " + time2, false);
                    }
                },calendar.get(Calendar.HOUR_OF_DAY) + 1,0,true);
                timePickerDialog.show();
                datePickerDialog.show();
            }
        });
        tv_checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setTime(d_checkOut);
                time = tv_checkOut.getText().toString();
                time1 = time.substring(0,time.indexOf(" "));
                time2 = time.substring(time.indexOf(" ") + 1,time.length() - 1);
                datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        time1 = dayOfMonth +"/" +(month + 1) + "/" +year;
                        updateTime(time1 + " " + time2, true);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time2 = String.valueOf(hourOfDay);
                        updateTime(time1 + " " + time2, true);
                    }
                },calendar.get(Calendar.HOUR_OF_DAY) + 1,0,true);
                timePickerDialog.show();
                datePickerDialog.show();
            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFilterRoom();
            }
        });
        float_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHideRooms();
                handleFilterRoom();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if(calendar.get(Calendar.MINUTE) >= 40)
            calendar.setTimeInMillis(calendar.getTime().getTime() + 3600000);
        now.setTime(calendar.getTime().getTime());
        d_checkOut.setTime(calendar.getTime().getTime() + 2400000 * 36);
        d_checkIn.setTime(calendar.getTime().getTime());
        time1 = sdf.format(d_checkIn);
        time2 = sdf.format(d_checkOut);
        try {
            d_checkIn = sdf.parse(time1);
            d_checkOut = sdf.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tv_checkIn.setText(time1 + "h");
        tv_checkOut.setText(time2 + "h");
        handleFilterRoom();
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
                            Bundle bundle = new Bundle();
                            bundle.putInt(KEY_ROOM,rooms.getId());
                            bundle.putString(KEY_CHECKIN,sdf.format(d_checkIn));
                            bundle.putString(KEY_CHECKOUT,sdf.format(d_checkOut));
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
            Button btn_checkIn = dialog.findViewById(R.id.dialogPDT_btn_checkIn)
                    ,btn_schedule = dialog.findViewById(R.id.dialogPDT_btn_schedule)
                    ,btn_detail = dialog.findViewById(R.id.dialogPDT_btn_detail);
            btn_checkIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(requireContext(), "Nhận phòng !", Toast.LENGTH_SHORT).show();
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

    private void handleFilterRoom(){
        if(check)
            mListRooms = dao.getAllOfRooms();
        else {
            Date date = new Date();
            date.setTime(d_checkIn.getTime() - 3600000);
            mListRooms = dao.getListRoomWithTime(date,d_checkOut);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mListRooms.forEach((x) -> x.setStatus(0));
            }
        }
        listCategory = dao.getListNameCategoryWithRoomId(mListRooms);
        roomsAdapter.setData(mListRooms,listCategory);
        
    }
    private void updateTime(String text,boolean b){
        Date date;
        try{
            date = sdf.parse(text);
            if(b && date.getTime() > d_checkIn.getTime()){
                tv_checkOut.setText(text + "h");
                d_checkOut = date;
            }
            else if(!b && date.getTime() < d_checkOut.getTime()){
                tv_checkIn.setText(text + "h");
                d_checkIn = date;
            }
            }catch (Exception e){
                Log.d(TAG, "updateTime: ");
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
}