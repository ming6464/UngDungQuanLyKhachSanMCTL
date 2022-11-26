package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.RoomDatabase;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ServiceOrderAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.PhongFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChucNangDatPhongActivity extends AppCompatActivity implements ServiceOrderAdapter.EventOfServiceOrder {
    private int idRoom,totalService = 0;
    private List<String> userListString,serviceListString;
    private List<Services> serviceList1,serviceList2;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat sdf,sdf1;
    private Spinner sp_customer,sp_amountOfPeople,sp_service;
    private EditText ed_checkIn,ed_checkOut,ed_hourCheckIn,ed_hourCheckOut,ed_fullName,ed_phoneNumber,ed_CCCD,ed_address;
    private TextView tv_total,tv_room;
    private RadioButton rdo_male,rdo_book,rdo_newCustomer;
    private KhachSanDAO dao;
    private ServiceOrderAdapter serviceOrderAdapter;
    private RecyclerView rc_service;
    private Date now,checkIn,checkOut;
    private int roomStatus;
    private List<OrderDetail> roomReserveList;
    private final String TAG = "test.zz";
    private KhachSanSharedPreferences share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang_dat_phong);
        dao = KhachSanDB.getInstance(this).getDAO();
        share = new KhachSanSharedPreferences(this);
        idRoom = getIntent().getIntExtra(PhongFragment.KEY_MAPHONG,dao.getNewIdOfUser());
        roomStatus = getIntent().getIntExtra(PhongFragment.KEY_STATUS,0);
        if(roomStatus == 2)
            roomReserveList = dao.getAllWithStatusOfOrderDetail(roomStatus,idRoom);
        anhXa();
        tv_room.setText(dao.getWithIDOfRooms(idRoom).getName());
        handlerDate();
        handlerSpinner();
        handlerRecyclerService();
        loadTotal();
    }

    private void handlerRecyclerService() {
        serviceOrderAdapter = new ServiceOrderAdapter(this);
        rc_service.setHasFixedSize(true);
        rc_service.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        rc_service.setAdapter(serviceOrderAdapter);
        serviceOrderAdapter.setData(serviceListString);
    }

    private void handlerDate() {
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
        sp_amountOfPeople = findViewById(R.id.actiCNDP_sp_amountOfPeople);
        sp_service = findViewById(R.id.actiCNDP_sp_service);
        tv_total = findViewById(R.id.actiCNDP_tv_total);
        tv_room = findViewById(R.id.actiCNDP_tv_room);
        rc_service = findViewById(R.id.actiCNDP_rc_service);

    }

    private void handlerSpinner() {
        serviceList2 = new ArrayList<>();
        serviceListString = new ArrayList<>();
        userListString = dao.getListAdapterOfUser(dao.getListWithStatusOfUser(0));
        ArrayAdapter userAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, userListString);
        sp_customer.setAdapter(userAdapter);
        sp_customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadInfoOldCustom();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> amountOfPeopleList = new ArrayList<>();
        for(int x = 1; x <= dao.getAmountOfPeopleCategoryWithRoomId(idRoom); x ++){
            amountOfPeopleList.add(String.valueOf(x));
        }
        ArrayAdapter amountOfPeopleAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, amountOfPeopleList);
        sp_amountOfPeople.setAdapter(amountOfPeopleAdapter);
        serviceList1 = dao.getListServiceCategoryWithRoomId(idRoom);
        List<String> serviceString = dao.getListAdapterOfServices(serviceList1);
        ArrayAdapter servicesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, serviceString);
        sp_service.setAdapter(servicesAdapter);
    }

    

    public void hanlderActionRdoOldCustomer(View view){
        if(userListString.size() > 0){
            findViewById(R.id.actiCNDP_layout_oldCustomer).setVisibility(View.VISIBLE);
            setFocusInfomation(false);
            loadInfoOldCustom();
            return;
        }
        rdo_newCustomer.setChecked(true);
        CustomToast.makeText(this, "Không có khách hàng cũ !", false).show();


    }

    public void hanlderActionRdoNewCustomer(View view){
        setFocusInfomation(true);
        findViewById(R.id.actiCNDP_layout_oldCustomer).setVisibility(View.GONE);
        ed_address.setText("");
        ed_CCCD.setText("");
        ed_fullName.setText("");
        ed_phoneNumber.setText("");

    }

    public void hanlderActionRdoBook (View view){
        ed_checkIn.setFocusableInTouchMode(false);
        ed_checkIn.setText(sdf.format(now));
        findViewById(R.id.actiCNDP_layout_services).setVisibility(View.VISIBLE);
        loadTotal();
    }
    public void hanlderActionRdoReserve (View view){
        ed_checkIn.setFocusableInTouchMode(true);
        findViewById(R.id.actiCNDP_layout_services).setVisibility(View.GONE);
    }

    public void hanlderActionBtnSave(View view) {
        int idCustomer,status = 0,
                amountOfPeople = Integer.parseInt(sp_amountOfPeople.getSelectedItem().toString()),idOrderDetail;
        if(!rdo_book.isChecked())
            status = 2;
        if(rdo_newCustomer.isChecked()){
            String fullName = ed_fullName.getText().toString(),
                    phoneNumber = ed_phoneNumber.getText().toString(),
                    cccd = ed_CCCD.getText().toString(),
                    address = ed_address.getText().toString();
            if(fullName.isEmpty() || phoneNumber.isEmpty() || cccd.isEmpty() || address.isEmpty()){
                CustomToast.makeText(this, "Thông tin khách hàng không được bỏ trống !", false).show();
                return;
            }
            if(!fullName.matches("\"^[A-Za-z]+$\"")){
                CustomToast.makeText(this, "Tên không phù hợp", false).show();
                return;
            }
            if(!phoneNumber.matches("^0\\d{9}")){
                CustomToast.makeText(this, "Số điện thoại không đúng !", false).show();
                return;
            }
            if(cccd.length() < 12){
                CustomToast.makeText(this, "CCCD/CMND Không chính xác !", false).show();
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
            String text = sp_customer.getSelectedItem().toString();
            idCustomer = Integer.parseInt(text.substring(1,text.indexOf(" ")));
            if(status == 2){
                Orders orders = new Orders(idCustomer,Integer.parseInt(share.getID2()),null);
                orders.setStatus(status);
                dao.insertOfOrders(orders);
            }
        }
        OrderDetail orderDetail = new OrderDetail(idRoom,dao.getIdWithPeopleIdOfOrder(idCustomer,status),
                amountOfPeople,checkIn,checkOut);
        orderDetail.setStatus(status);
        dao.insertOfOrderDetail(orderDetail);
        idOrderDetail = dao.getNewIdOfOrderDetail();
        if(status != 2)
            for(Services x : serviceList2){
                dao.insertOfServiceOrder(new ServiceOrder(x.getId(),idOrderDetail));
            }
        CustomToast.makeText(this, "Đặt thành công !", true).show();
        for(Orders x : dao.getAllOfOrders()){
            Log.d(TAG, x.toString());
        }
        finish();
    }

    public void hanlderActionBtnCancel(View view) {
        finish();
    }

    public void hanlderActionEdCheckIn(View view) {
        if(!rdo_book.isChecked()){
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
    public void hanlderActionBtnAddService(View view){
        int index = sp_service.getSelectedItemPosition();
        serviceListString.add(serviceList1.get(index).getName());
        serviceList2.add(serviceList1.get(index));
        serviceOrderAdapter.notifyDataSetChanged();
        totalService += serviceList1.get(index).getPrice();
        loadTotal();
    }
    private void loadTotal(){
        try {
            checkIn = sdf1.parse(getTimeCheckIn());
            checkOut = sdf1.parse(getTimeCheckOut());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int soGio = (int) (checkOut.getTime() - checkIn.getTime())/3600000;
        tv_total.setText(String.valueOf(dao.getPriceWithIdOfRooms(idRoom)  * soGio + totalService ));
    }

    public String getTimeCheckIn(){
        return ed_checkIn.getText().toString().trim() + " " + ed_hourCheckIn.getText().toString().trim();
    }

    public String getTimeCheckOut (){
        return ed_checkOut.getText().toString().trim() + " " + ed_hourCheckOut.getText().toString().trim();
    }

    @Override
    public void cancel(int position) {
        serviceListString.remove(position);
        serviceOrderAdapter.notifyDataSetChanged();
        totalService -= serviceList2.get(position).getPrice();
        serviceList2.remove(position);
        loadTotal();
    }

    private void setFocusInfomation(boolean b){
        ed_fullName.setFocusableInTouchMode(b);
        ed_CCCD.setFocusableInTouchMode(b);
        ed_address.setFocusableInTouchMode(b);
        ed_phoneNumber.setFocusableInTouchMode(b);
        rdo_male.setEnabled(b);
        findViewById(R.id.actiCNDP_rdo_feMale).setEnabled(b);
    }
    private void loadInfoOldCustom() {
        String text = sp_customer.getSelectedItem().toString();
        int id = Integer.parseInt(text.substring(1,text.indexOf(" ")));
        People people = dao.getWithIdOfUser(id);
        ed_phoneNumber.setText(people.getSDT());
        ed_fullName.setText(people.getFullName());
        ed_CCCD.setText(people.getCCCD());
        ed_address.setText(people.getAddress());
        if(people.getSex() == 1)
            rdo_male.setChecked(true);
        else{
            RadioButton rdo_feMale = findViewById(R.id.actiCNDP_rdo_feMale);
            rdo_feMale.setChecked(true);
        }

    }
}