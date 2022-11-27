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
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceCategory;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.PhongFragment;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ChucNangDatPhongActivity extends AppCompatActivity implements ServiceOrderAdapter.EventOfServiceOrder {
    private int idRoom,status,totalService = 0,hours;
    private List<String> userListString,serviceListString,serviceListString2;
    private List<Services> serviceList1;
    private List<ServiceOrder> serviceOrderList;
    private SimpleDateFormat sdf;
    private NumberFormat format;
    private Spinner sp_customer,sp_amountOfPeople,sp_service;
    private EditText ed_fullName,ed_phoneNumber,ed_CCCD,ed_address;
    private TextView tv_total,tv_room,tv_checkIn,tv_checkOut;
    private RadioButton rdo_male,rdo_newCustomer;
    private KhachSanDAO dao;
    private ServiceOrderAdapter serviceOrderAdapter;
    private RecyclerView rc_service;
    private Date checkIn,checkOut;
    private KhachSanSharedPreferences share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang_dat_phong);
        dao = KhachSanDB.getInstance(this).getDAO();
        share = new KhachSanSharedPreferences(this);
        anhXa();
        hanldeDataBundle();
        handlerSpinner();
        handlerRecyclerService();
        loadTotal();
    }

    private void hanldeDataBundle() {
        sdf = new SimpleDateFormat("dd/MM/yyyy HH");
        Bundle bundle = getIntent().getBundleExtra(PhongFragment.KEY_BUNDLE);
        idRoom = bundle.getInt(PhongFragment.KEY_ROOM);
        status = bundle.getInt(PhongFragment.KEY_STATUS);
        try {
            checkIn = sdf.parse(bundle.getString(PhongFragment.KEY_CHECKIN));
            checkOut = sdf.parse(bundle.getString(PhongFragment.KEY_CHECKOUT));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        hours = (int) (checkOut.getTime() - checkIn.getTime())/3600000;
        tv_room.setText(dao.getWithIDOfRooms(idRoom).getName());
        tv_checkOut.setText(sdf.format(checkOut));
        tv_checkIn.setText(sdf.format(checkIn));
    }

    private void handlerRecyclerService() {
        serviceOrderAdapter = new ServiceOrderAdapter(this);
        rc_service.setHasFixedSize(true);
        rc_service.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        rc_service.setAdapter(serviceOrderAdapter);
        serviceOrderAdapter.setData(serviceListString);
    }

    private void anhXa() {
        ed_fullName = findViewById(R.id.actiCNDP_ed_fullName);
        ed_CCCD = findViewById(R.id.actiCNDP_ed_CCCD);
        ed_address = findViewById(R.id.actiCNDP_ed_address);
        ed_phoneNumber = findViewById(R.id.actiCNDP_ed_phoneNumber);
        rdo_male = findViewById(R.id.actiCNDP_rdo_male);
        rdo_newCustomer = findViewById(R.id.actiCNDP_rdo_newCustomer);
        sp_customer = findViewById(R.id.actiCNDP_sp_customer);
        sp_amountOfPeople = findViewById(R.id.actiCNDP_sp_amountOfPeople);
        sp_service = findViewById(R.id.actiCNDP_sp_service);
        tv_total = findViewById(R.id.actiCNDP_tv_total);
        tv_room = findViewById(R.id.actiCNDP_tv_room);
        tv_checkIn = findViewById(R.id.actiCNDP_tv_checkIn);
        tv_checkOut = findViewById(R.id.actiCNDP_tv_checkOut);
        rc_service = findViewById(R.id.actiCNDP_rc_service);
        format = NumberFormat.getInstance(new Locale("vi","VN"));
    }

    private void handlerSpinner() {
        serviceOrderList = new ArrayList<>();
        serviceListString = new ArrayList<>();
        userListString = dao.getListAdapterOfUser(dao.getListWithStatusOfUser(0));
        ArrayAdapter userAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, userListString);
        sp_customer.setAdapter(userAdapter);
        sp_customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadInfoOldCustom(Integer.parseInt(userListString.get(position).substring(1,userListString.get(position).indexOf(" "))));
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
        serviceListString2 = new ArrayList<>();
        List<String> serviceString = dao.getListAdapterOfServices(serviceList1);
        ArrayAdapter servicesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, serviceString);
        sp_service.setAdapter(servicesAdapter);
        for (Services x : serviceList1){
            serviceOrderList.add(new ServiceOrder(x.getId(),idRoom,0));
        }
    }

    public void hanlderActionRdoOldCustomer(View view){
        if(userListString.size() > 0){
            findViewById(R.id.actiCNDP_layout_oldCustomer).setVisibility(View.VISIBLE);
            setFocusInfomation(false);
            loadInfoOldCustom(Integer.parseInt(userListString.get(0).substring(1,userListString.get(0).indexOf(" "))));
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


    public void hanlderActionBtnSave(View view) {
        int idCustomer,idOrder, amountOfPeople = Integer.parseInt(sp_amountOfPeople.getSelectedItem().toString()),idOrderDetail;
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
            idOrder = dao.getNewIdOfOrders();
        }
        else {
            String text = sp_customer.getSelectedItem().toString();
            idCustomer = Integer.parseInt(text.substring(1,text.indexOf(" ")));
            Orders orders1 = dao.getWithPeopleIdAndStatusOrderOfOrders(idCustomer,status);
            if(orders1 == null){
                Orders orders = new Orders(idCustomer,Integer.parseInt(share.getID2()),null);
                orders.setStatus(status);
                dao.insertOfOrders(orders);
                idOrder = dao.getNewIdOfOrders();
            }else{
                idOrder = orders1.getId();
            }
        }
        OrderDetail orderDetail = new OrderDetail(idRoom,idOrder,
                amountOfPeople,checkIn,checkOut);
        orderDetail.setStatus(status);
        dao.insertOfOrderDetail(orderDetail);
        idOrderDetail = dao.getNewIdOfOrderDetail();
        if(status != 2)
            for(ServiceOrder x : serviceOrderList){
                if(x.getAmount() != 0){
                    x.setOrderDetailID(idOrderDetail);
                    dao.insertOfServiceOrder(x);
                }

            }
        CustomToast.makeText(this, "Đặt thành công !", true).show();
        finish();
    }

    public void hanlderActionBtnCancel(View view) {
        finish();
    }
    private void loadTotal(){
        tv_total.setText(format.format(dao.getPriceWithIdOfRooms(idRoom)  * hours + totalService) + "đ");
    }
    public void hanlderActionBtnAddService(View view){
        int index = sp_service.getSelectedItemPosition();
        Services sv = serviceList1.get(index);
        serviceListString.add(sv.getName());
        serviceListString2.add(String.valueOf(sv.getId()));
        serviceListString2.add(String.valueOf(sv.getPrice()));
        serviceOrderAdapter.notifyDataSetChanged();
        totalService += serviceList1.get(index).getPrice();
        for(int i = 0; i < serviceOrderList.size(); i++){
            if(serviceOrderList.get(i).getServiceId() == sv.getId()){
                serviceOrderList.get(i).setAmount(serviceOrderList.get(i).getAmount() + 1);
                break;
            }
        }
        loadTotal();
    }
    @Override
    public void cancel(int position) {
        serviceListString.remove(position);
        serviceOrderAdapter.notifyDataSetChanged();
        totalService -= Integer.parseInt(serviceListString2.get(position*2 + 1));
;        for(int i = 0; i < serviceOrderList.size(); i++){
            if(serviceOrderList.get(i).getServiceId() == Integer.parseInt(serviceListString2.get(position * 2))){
                serviceOrderList.get(i).setAmount(serviceOrderList.get(i).getAmount() - 2);
                break;
            }
        }
        serviceListString2.remove(position * 2 + 1);
        serviceListString2.remove(position * 2);
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
    private void loadInfoOldCustom(int id) {
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