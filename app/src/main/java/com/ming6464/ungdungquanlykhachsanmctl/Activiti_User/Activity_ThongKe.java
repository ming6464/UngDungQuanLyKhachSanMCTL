package com.ming6464.ungdungquanlykhachsanmctl.Activiti_User;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemCotThongKeAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Activity_ThongKe extends AppCompatActivity {
    private List<Integer> listSoLieu;
    private List<String> listName;
    private ItemCotThongKeAdapter adapterCot;
    private KhachSanDAO dao;
    private RecyclerView rc_Category;
    private TextView tv_checkIn,tv_checkOut;
    private SimpleDateFormat sdf,sdf1;
    private Date d_checkIn,d_checkOut;
    private DatePickerDialog datePicker;
    private Calendar calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = KhachSanDB.getInstance(this).getDAO();
        setContentView(R.layout.activity_thong_ke);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf1 = new SimpleDateFormat("dd/MM/yyyy HH");
        anhXa();
    }

    @Override
    protected void onResume() {
        super.onResume();
        long time = System.currentTimeMillis();
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        d_checkIn = new Date(time - 24*3600000);
        d_checkOut = new Date(time);
        tv_checkOut.setText(sdf.format(d_checkOut));
        tv_checkIn.setText(sdf.format(d_checkIn));
        try {
            d_checkOut = sdf1.parse(tv_checkOut.getText().toString() + " 23");
            d_checkIn = sdf1.parse(tv_checkIn.getText().toString() + " 01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        filterDuLieu();
    }

    private void anhXa() {
        tv_checkIn = findViewById(R.id.actiThongKe_tv_checkIn);
        tv_checkOut = findViewById(R.id.actiThongKe_tv_checkOut);
        rc_Category = findViewById(R.id.actiThongKe_rc_category);
    }

    public void handleActionTvCheckIn(View view) {
        calendar.setTimeInMillis(d_checkIn.getTime());
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_checkIn.setText(formatDate(dayOfMonth) + "/" + (month + 1) + "/" + year);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    public void handleActionTvCheckOut(View view) {
        calendar.setTimeInMillis(d_checkOut.getTime());
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_checkOut.setText(formatDate(dayOfMonth) + "/" + (month + 1) + "/" + year);
                filterDuLieu();
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    private void filterDuLieu() {

    }

    private String formatDate(int date){
        if(date < 10)
            return "0" + date;
        return String.valueOf(date);
    }
}