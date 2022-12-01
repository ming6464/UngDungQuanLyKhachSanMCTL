package com.ming6464.ungdungquanlykhachsanmctl.Activiti_User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.AdapterDichVuThongKe;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.AdapterThongKeLoaiPhong;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Activity_ThongKe extends AppCompatActivity {
    Toolbar toolbar;
    TextView textView, tvTongTienLoai, tvTongTienDichVu;
    EditText edNgayDen, edNgayDi;
    Calendar calendar = Calendar.getInstance();
    RecyclerView rclLoai, rclService;
    KhachSanDAO db;
    //
    List<Categories> list = new ArrayList<>();
    List<Integer> listCount = new ArrayList<>();
    //
    List<Services> listService = new ArrayList<>();
    List<Integer> listCountService = new ArrayList<>();
    List<ServiceOrder> listOder = new ArrayList<>();
    //chuyển ngày
    SimpleDateFormat smf = new SimpleDateFormat("dd-MM-yyyy");
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        //
        calendar.setTimeInMillis(System.currentTimeMillis()); // lấy ngày hiện tại của hệ thông

        toolbar = findViewById(R.id.toolbar_thong_tin);
        textView = findViewById(R.id.tv1234);
        textView.setText("Thống Kê");
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        //
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //
        anhXa();
        //
        db = KhachSanDB.getInstance(this).getDAO();
        // gọi lịch

        //

        edNgayDi.setText(smf.format(new Date(calendar.getTimeInMillis())));
        calendar.setTimeInMillis(calendar.getTimeInMillis() - (24 * 3600000));
        edNgayDen.setText(smf.format(new Date(calendar.getTimeInMillis())));
        String days = edNgayDi.getText().toString();
        Date date123 = new Date(calendar.getTimeInMillis());
        try {
            Date date = smf.parse(days);
            //
            long startDate = date.getTime();
            long endDate = date123.getTime();
            if (endDate > startDate) {
                edNgayDi.setEnabled(false);
                Toast.makeText(this, "Bạn Không Thể Thay Đổi Ngày Đi", Toast.LENGTH_SHORT).show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        showData();

        edNgayDen.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String month2 = (month + 1) + "";
                    if ((month + 1) < 10)
                        month2 = "0" + month2;
                    edNgayDen.setText(dayOfMonth + "-" + month2 + "-" + year);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
            dialog.show();
        });
        //
        try {
            Date date = smf.parse(days);
            //
            long startDate = date.getTime();
            long endDate = date123.getTime();
            if (endDate < startDate) {
                edNgayDi.setEnabled(false);
                Toast.makeText(this, "Bạn Không Thể Thay Đổi Ngày Đi", Toast.LENGTH_SHORT).show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        edNgayDi.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String month2 = (month + 1) + "";
                    if ((month + 1) < 10)
                        month2 = "0" + month2;
                    edNgayDi.setText(dayOfMonth + "-" + month2 + "-" + year);
                    showData();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
            dialog.show();
            //

        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void anhXa() {
        edNgayDen = findViewById(R.id.edNgayDen);
        edNgayDi = findViewById(R.id.edNgayDi);
        rclLoai = findViewById(R.id.recylerViewPhong);
        rclService = findViewById(R.id.recylerViewDichVu);
        tvTongTienLoai = findViewById(R.id.actiHDCT_tv_totalRoom);
        tvTongTienDichVu = findViewById(R.id.actiHDCT_tv_totalService);
    }

    private void showData() {
        list = new ArrayList<>();
        listService = new ArrayList<>();
        listCountService = new ArrayList<>();
        listOder = new ArrayList<>();
        listCount = new ArrayList<>();

        String st = edNgayDen.getText().toString();
        String en = edNgayDi.getText().toString();
        LinearLayoutManager manager = new LinearLayoutManager(Activity_ThongKe.this);
        rclLoai.setLayoutManager(manager);
        list = db.getAllOfLoaiPhong();
        int sum = 0;
        //
        for (Categories x : list) {
            try {
                listCount.add(db.showSoLuong(x.getId(), smf.parse(st), smf.parse(en)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //
        for (int i = 0; i < listCount.size(); i++) {
            sum += listCount.get(i) * list.get(i).getPrice();
        }
        tvTongTienLoai.setText(sum + "");

        AdapterThongKeLoaiPhong adapter = new AdapterThongKeLoaiPhong(this, list, listCount);
        rclLoai.setAdapter(adapter);
        // dịch vụ
        LinearLayoutManager manager12 = new LinearLayoutManager(Activity_ThongKe.this);
        rclService.setLayoutManager(manager12);
        listService = db.getAllService();

        int sum12 = 0;
        for (Services services : listService) {
            try {
                listCountService.add(db.showCountService(services.getId(), smf.parse(st), smf.parse(en)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //
        for (int i = 0; i < listCountService.size(); i++) {
            sum12 += listCountService.get(i) * listService.get(i).getPrice();
        }
        tvTongTienDichVu.setText(sum12 + "");
        AdapterDichVuThongKe adapter12 = new AdapterDichVuThongKe(listService, this, listCountService);
        rclService.setAdapter(adapter12);

    }

}