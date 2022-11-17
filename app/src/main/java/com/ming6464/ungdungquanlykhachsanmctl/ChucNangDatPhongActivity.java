package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.room.RoomDatabase;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.PhongFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChucNangDatPhongActivity extends AppCompatActivity {
    private int idRoom;
    private List<String> amountOfPeopleList,userListString;
    private ArrayAdapter amountOfPeopleAdapter;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat sdf,sdf1;
    private Spinner sp_customer,sp_rooms,sp_amountOfPeople;
    private EditText ed_checkIn,ed_checkOut,ed_hourCheckIn,ed_hourCheckOut,ed_fullName,ed_phoneNumber,ed_CCCD,ed_address;
    private TextView tv_total;
    private RadioButton rdo_male,rdo_book,rdo_newCustomer;
    private boolean check = false;
    private KhachSanDAO dao;
    private Date now,checkIn,checkOut;
    private final String TAG = "test.zz";
    private KhachSanSharedPreferences share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang_dat_phong);
        dao = KhachSanDB.getInstance(this).getDAO();
        share = new KhachSanSharedPreferences(this);
        idRoom = getIntent().getIntExtra(PhongFragment.KEY_MAPHONG,dao.getNewIdOfUser());
        anhXa();
        addDate();
        loadTotal();
        addSpinner();
    }

    private void addDate() {
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf1 = new SimpleDateFormat("dd/MM/yyyy HH");
        checkIn = new Date();
        checkOut = new Date();
        now = new Date();
        try {
            now.setTime(calendar.getTime().getTime() + 2400000* 36);
            calendar.setTime(now);
            checkOut = now;
            int hour = calendar.get(Calendar.HOUR_OF_DAY) + 12,hour1;
            if(hour > 24)
                hour -= 24;
            if(calendar.get(Calendar.MINUTE) > 35){
                hour += 1;
            }
            hour1 = hour + 1;
            if(hour == 25)
                hour = 1;
            else if(hour == 24){
                hour = 0;
                hour1 = 1;
            }
            ed_checkIn.setText(sdf.format(now));
            ed_checkOut.setText(sdf.format(checkOut));
            ed_hourCheckIn.setText(String.valueOf(hour));
            ed_hourCheckOut.setText(String.valueOf(hour1)); 
            loadTotal();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void anhXa() {
        ed_fullName = findViewById(R.id.actiCNDP_ed_fullName);
        ed_CCCD = findViewById(R.id.actiCNDP_ed_CCCD);
        ed_address = findViewById(R.id.actiCNDP_ed_address);
        ed_phoneNumber = findViewById(R.id.actiCNDP_ed_phoneNumber);
        rdo_male = findViewById(R.id.actiCNDP_rdo_male);
        rdo_book = findViewById(R.id.actiCNDP_rdo_book);
        rdo_newCustomer = findViewById(R.id.actiCNDP_rdo_newCustomer);
        ed_checkIn = findViewById(R.id.actiCNDP_ed_checkIn);
        ed_checkOut = findViewById(R.id.actiCNDP_ed_checkOut);
        ed_hourCheckIn = findViewById(R.id.actiCNDP_ed_hourCheckIn);
        ed_hourCheckOut = findViewById(R.id.actiCNDP_ed_hourCheckOut);
        sp_customer = findViewById(R.id.actiCNDP_sp_customer);
        sp_rooms = findViewById(R.id.actiCNDP_sp_rooms);
        sp_amountOfPeople = findViewById(R.id.actiCNDP_sp_amountOfPeople);
        tv_total = findViewById(R.id.actiCNDP_tv_total);
        amountOfPeopleList = new ArrayList<>();
    }

    private void addSpinner() {
        userListString = dao.getListAdapterOfUser(dao.getListWithStatusOfUser(0));
        List<Rooms> roomsList = dao.getListWithStatusOfRooms(0);
        roomsList.addAll(dao.getListWithStatusOfRooms(2));
        List<String> roomsListString = dao.getListAdapterOfURooms(roomsList);
        ArrayAdapter arr_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, userListString);
        sp_customer.setAdapter(arr_adapter);
        ArrayAdapter roomAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roomsListString);
        sp_rooms.setAdapter(roomAdapter);
        int index = 0;
        for(Rooms x : roomsList){
            if(idRoom == x.getId())
                break;
            index ++;
        }
        sp_rooms.setSelection(index);
        sp_rooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateListAmountOfPeople(dao.getAmountOfPeopleCategoryWithRoomId(roomsList.get(position).getId()));
                loadTotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        amountOfPeopleAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,amountOfPeopleList);
        sp_amountOfPeople.setAdapter(amountOfPeopleAdapter);
    }

    public void hanlderActionRdoOldCustomer(View view){
        if(userListString.size() > 0){
            findViewById(R.id.actiCNDP_layout_oldCustomer).setVisibility(View.VISIBLE);
            findViewById(R.id.actiCNDP_layout_newCustomer).setVisibility(View.GONE);
        }else{
            rdo_newCustomer.setChecked(true);
            Toast.makeText(this, "Không có khách hàng cũ !", Toast.LENGTH_SHORT).show();
        }


    }

    public void hanlderActionRdoNewCustomer(View view){
        findViewById(R.id.actiCNDP_layout_oldCustomer).setVisibility(View.GONE);
        findViewById(R.id.actiCNDP_layout_newCustomer).setVisibility(View.VISIBLE);
    }

    public void hanlderActionRdoBook (View view){
        ed_checkIn.setFocusable(false);
        check =false;
        ed_checkIn.setText(sdf.format(now));
        loadTotal();
    }
    public void hanlderActionRdoReserve (View view){
        ed_checkIn.setFocusable(true);
        check = true;
    }

    public void hanlderActionBtnSave(View view) {
        String text = sp_rooms.getSelectedItem().toString();
        idRoom = Integer.parseInt(text.substring(1,text.indexOf(" ")));
        int idCustomer,status = 0,amountOfPeople = Integer.parseInt(sp_amountOfPeople.getSelectedItem().toString());
        if(!rdo_book.isChecked())
            status = 2;
        if(rdo_newCustomer.isChecked()){
            String fullName = ed_fullName.getText().toString(),
                    phoneNumber = ed_phoneNumber.getText().toString(),
                    cccd = ed_CCCD.getText().toString(),
                    address = ed_address.getText().toString();
            if(fullName.isEmpty() || phoneNumber.isEmpty() || cccd.isEmpty() || address.isEmpty()){
                Toast.makeText(this, "Thông tin khách hàng không được bỏ trống !", Toast.LENGTH_SHORT).show();
                return;
            }
            int sex = 0;
            if(rdo_male.isChecked())
                sex = 1;
            dao.insertOfUser(new People(fullName,phoneNumber, cccd,address,sex,status));
            idCustomer = dao.getNewIdOfUser();
            Orders orders = new Orders(idCustomer,Integer.parseInt(share.getID2()),null);
            orders.setStatus(status);
            dao.insertOfOrders(orders);
        }else {
            text = sp_customer.getSelectedItem().toString();
            idCustomer = Integer.parseInt(text.substring(1,text.indexOf(" ")));
        }
        OrderDetail orderDetail = new OrderDetail(idRoom,dao.getIdWithPeopleIdOfOrder(idCustomer),
                amountOfPeople,checkIn,checkOut);
        orderDetail.setStatus(status);
        dao.insertOfOrderDetail(orderDetail);
        Toast.makeText(this, "Đặt thành công !", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void hanlderActionBtnCancel(View view) {
        finish();
    }

    public void hanlderActionEdCheckIn(View view) {
        if(check){
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    ed_checkIn.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    loadTotal();
                }
            },calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE));
            datePickerDialog.show();
        }
    }

    public void hanlderActionEdCheckOut(View view) {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ed_checkOut.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                loadTotal();
            }
        },calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }
    public void hanlderActionEdHourCheckIn(View view){
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                ed_hourCheckIn.setText(String.valueOf(hourOfDay));
                loadTotal();
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();

    }
    public void hanlderActionEdHourCheckOut(View view){
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                ed_hourCheckOut.setText(String.valueOf(hourOfDay));
                loadTotal();
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }
    private void loadTotal(){
        try {
            checkIn = sdf1.parse(getTimeCheckIn());
            checkOut = sdf1.parse(getTimeCheckOut());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int soGio = (int) (checkOut.getTime() - checkIn.getTime())/3600000;
        tv_total.setText("Tổng Tiền : " + dao.getPriceWithIdOfRooms(idRoom)  * soGio);
    }

    public String getTimeCheckIn(){
        return ed_checkIn.getText().toString().trim() + " " + ed_hourCheckIn.getText().toString().trim();
    }

    public String getTimeCheckOut (){
        return ed_checkOut.getText().toString().trim() + " " + ed_hourCheckOut.getText().toString().trim();
    }

    public void updateListAmountOfPeople(int amount){
        amountOfPeopleList.clear();
        for(int x = 1; x <= amount; x ++){
            amountOfPeopleList.add(String.valueOf(x));
        }
        amountOfPeopleAdapter.notifyDataSetChanged();
    }

}