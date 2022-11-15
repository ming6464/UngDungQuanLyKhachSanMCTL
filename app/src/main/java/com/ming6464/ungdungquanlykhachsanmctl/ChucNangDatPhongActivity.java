package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.room.RoomDatabase;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.PhongFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChucNangDatPhongActivity extends AppCompatActivity {
    private List<People> userList;
    private List<Rooms> roomsList;
    private List<String> userListString,roomsListString;
    private Rooms objRoom;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat sdf;
    private Spinner sp_khachHang,sp_phong;
    private EditText ed_ngayDen,ed_ngayDi;
    private TextView tv_total;
    private ImageButton imgBtn_themKH;
    private boolean check = false;
    private KhachSanDAO dao;
    private People objPeople;
    private Date ngayHienTai,ngayDen,ngayDi;
    private final String TAG = "tao.z";
    private ArrayAdapter userAdapter;
    private KhachSanSharedPreferences share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang_dat_phong);
        dao = KhachSanDB.getInstance(this).getDAO();
        share = new KhachSanSharedPreferences(this);
        objRoom = dao.getWithIDOfRooms(getIntent().getIntExtra(PhongFragment.KEY_MAPHONG,4));
        anhXa();
        addDate();
        loadTotal();
        addSpinner();
    }

    private void addDate() {
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            ngayHienTai = sdf.parse(calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ngayDi = new Date(ngayHienTai.getTime() + 1000);
        ed_ngayDen.setText(sdf.format(ngayHienTai));
        ed_ngayDi.setText(sdf.format(ngayDi));

    }

    private void anhXa() {
        ed_ngayDen = findViewById(R.id.actiCNDP_ed_ngayDen);
        ed_ngayDi = findViewById(R.id.actiCNDP_ed_ngayDi);
        sp_khachHang = findViewById(R.id.actiCNDP_sp_khachHang);
        sp_phong = findViewById(R.id.actiCNDP_sp_phong);
        imgBtn_themKH = findViewById(R.id.actiCNDP_imgBtn_khachHangMoi);
        tv_total = findViewById(R.id.actiCNDP_tv_total);
    }

    private void addSpinner() {
        userList = dao.getListWithStatusOfUser(0);
        userListString = dao.getListAdapterOfUser(userList);
        roomsList = dao.getListWithStatusOfRooms(0);
        roomsListString = dao.getListAdapterOfURooms(roomsList);
        userAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,userListString);
        sp_khachHang.setAdapter(userAdapter);
        if(userList.size() > 0)
            objPeople = userList.get(0);

        sp_khachHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                objPeople = userList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG,"nothing selected");
            }
        });

        ArrayAdapter roomAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,roomsListString);
        sp_phong.setAdapter(roomAdapter);
        int index = 0;
        for(Rooms x : roomsList){
            if(x.getId() == objRoom.getId())
                break;
            index ++;
        }
        sp_phong.setSelection(index);
        sp_phong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                objRoom = roomsList.get(position);
                loadTotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void hanlderActionBtnSave(View view) {
        try {
            if(objPeople.getStatus() == 3){
                dao.insertOfOrders(new Orders(objPeople.getId(),Integer.parseInt(share.getID2()),null));
            }
            dao.insertOfOrderDetail(new OrderDetail(objRoom.getId(),dao.getIdWithPeopleIdOfOrder(objPeople.getId()),2,ngayHienTai,ngayDi));
            Toast.makeText(this, "Đặt thành công !", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hanlderActionBtnCancel(View view) {
        finish();
    }

    private void loadTotal(){
        int soNgay = (int) TimeUnit.DAYS.convert(ngayDi.getTime() - ngayHienTai.getTime(),TimeUnit.MILLISECONDS);
        tv_total.setText("Tổng Tiền : " + dao.getPriceWithIdOfRooms(objRoom.getId())  * soNgay);
    }

    public void hanlderActionEdNgayDen(View view) {
        if(check){

        }
    }

    public void hanlderActionEdNgayDi(View view) {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ed_ngayDi.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                try {
                    ngayDi = sdf.parse(ed_ngayDi.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                loadTotal();
            }
        },calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }
}