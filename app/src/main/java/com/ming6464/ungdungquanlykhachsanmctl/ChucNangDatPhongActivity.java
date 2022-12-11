package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemService1Adapter;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemServiceSpinnerAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.SoDoPhongFragment;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChucNangDatPhongActivity extends AppCompatActivity implements ItemService1Adapter.EventOfItemService1Adapter {
    private int status,totalService = 0,total,amount_date = 1;
    private String idRoom;
    private List<String> userListString;
    private List<Services> serviceList,serviceList1;
    private List<ServiceOrder> serviceOrderList;
    private NumberFormat format;
    private Spinner sp_customer,sp_amountOfPeople,sp_service;
    private EditText ed_fullName,ed_phoneNumber,ed_CCCD,ed_address;
    private TextView tv_total,tv_room,tv_checkIn,tv_checkOut;
    private AppCompatCheckBox chk_prepay;
    private RadioButton rdo_male,rdo_newCustomer;
    private KhachSanDAO dao;
    private ItemService1Adapter itemServiceOrderAdapter;
    private RecyclerView rc_service;
    private Date checkIn,checkOut;
    private KhachSanSharedPreferences share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang_dat_phong);
        dao = KhachSanDB.getInstance(this).getDAO();
        share = new KhachSanSharedPreferences(this);
        format = NumberFormat.getInstance(new Locale("en","EN"));
        anhXa();
        hanldeDataBundle();
        handleSpinner();
        loadTotal();
        if(status == 2){
            findViewById(R.id.actiCNDP_linear_services).setVisibility(View.GONE);
            chk_prepay.setVisibility(View.VISIBLE);
        }else {
            serviceList1 = new ArrayList<>();
            serviceList = dao.getListServiceCategoryWithRoomId(idRoom);
            //
            ItemServiceSpinnerAdapter itemServiceSpinnerAdapter = new ItemServiceSpinnerAdapter();
            sp_service.setAdapter(itemServiceSpinnerAdapter);
            itemServiceSpinnerAdapter.setDate(serviceList);
            //////
            for (Services x : serviceList){
                serviceOrderList.add(new ServiceOrder(x.getId(),0,0));
            }
            handleRecyclerService();
        }
    }

    private void hanldeDataBundle() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  HH");
        Bundle bundle = getIntent().getBundleExtra(SoDoPhongFragment.KEY_BUNDLE);
        idRoom = bundle.getString(SoDoPhongFragment.KEY_ROOM);
        status = bundle.getInt(SoDoPhongFragment.KEY_STATUS);
        checkOut = new Date(bundle.getLong(SoDoPhongFragment.KEY_CHECKOUT));
        checkIn = new Date(bundle.getLong(SoDoPhongFragment.KEY_CHECKIN));
        amount_date = bundle.getInt(SoDoPhongFragment.KEY_AMOUNT_DATE);
        tv_room.setText(idRoom);
        tv_checkOut.setText(sdf.format(checkOut) + "h");
        tv_checkIn.setText(sdf.format(checkIn) + "h");
    }

    private void handleRecyclerService() {
        itemServiceOrderAdapter = new ItemService1Adapter(this);
        rc_service.setHasFixedSize(true);
        rc_service.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        rc_service.setAdapter(itemServiceOrderAdapter);
        itemServiceOrderAdapter.setData(serviceList1);
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
        chk_prepay = findViewById(R.id.actiCNDP_chk_prepay);
    }

    private void handleSpinner() {
        serviceOrderList = new ArrayList<>();
        userListString = new ArrayList<>();
        ////
        for(People x : dao.getListKhachHangOfUser()){
            userListString.add(formatId(x.getId()) + " " + x.getFullName());
        }
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
        /////
        List<String> amountOfPeopleList = new ArrayList<>();
        for(int x = 1; x <= dao.getAmountOfPeopleCategoryWithRoomId(idRoom); x ++){
            amountOfPeopleList.add(String.valueOf(x));
        }
        ArrayAdapter amountOfPeopleAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, amountOfPeopleList);
        sp_amountOfPeople.setAdapter(amountOfPeopleAdapter);
        //////
    }
    public String formatId(int id) {
        if (id < 10)
            return "#0" + id;
        return "#" + id;
    }

    public void handleActionRdoOldCustomer(View view){
        if(userListString.size() > 0){
            findViewById(R.id.actiCNDP_layout_oldCustomer).setVisibility(View.VISIBLE);
            setFocusInfomation(false);
            loadInfoOldCustom(Integer.parseInt(userListString.get(0).substring(1,userListString.get(0).indexOf(" "))));
            return;
        }
        rdo_newCustomer.setChecked(true);
        CustomToast.makeText(this, "Không có khách hàng cũ !", false).show();
    }

    public void handleActionRdoNewCustomer(View view){
        setFocusInfomation(true);
        findViewById(R.id.actiCNDP_layout_oldCustomer).setVisibility(View.GONE);
        ed_address.setText("");
        ed_CCCD.setText("");
        ed_fullName.setText("");
        ed_phoneNumber.setText("");
    }
    public void handleActionBtnSave(View view) {
        if(status == 2 && !chk_prepay.isChecked()){
            CustomToast.makeText(this,"Thao tác thanh toán cần phải thực hiện trước !",false).show();
            return;
        }
        Orders orders1;
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
            if(!fullName.matches("^[a-zA-Z ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$")){
                CustomToast.makeText(this, "Tên không phù hợp", false).show();
                return;
            }
            if(!phoneNumber.matches("^0\\d{9}$")){
                CustomToast.makeText(this, "Số điện thoại không đúng !", false).show();
                return;
            }
            if(cccd.length() != 12){
                CustomToast.makeText(this, "CCCD/CMND Không chính xác !", false).show();
                return;
            }
            if(dao.getObjOfUser(phoneNumber) != null){
                CustomToast.makeText(this, "Số điện thoại đã tồn tại !", false).show();
                return;
            }
            if (dao.getObjWithCCCDOfUser(cccd) != null) {
                CustomToast.makeText(this, "CCCD/CMND đã tồn tại !", false).show();
                return;
            }
            int sex = 0;
            if(rdo_male.isChecked())
                sex = 1;
            dao.insertOfUser(new People(fullName,phoneNumber, cccd,address,sex,0));
            idCustomer = dao.getObjOfUser(phoneNumber).getId();
            dao.insertOfOrders(new Orders(idCustomer,share.getID(),checkIn,checkOut));
            idOrder = dao.getNewIdOfOrders();
        }
        else {
            String text = sp_customer.getSelectedItem().toString();
            idCustomer = Integer.parseInt(text.substring(1,text.indexOf(" ")));
            orders1 = dao.getObjUnpaidWithPeopleIdfOrders(idCustomer,checkIn);
            if(orders1 == null){
                dao.insertOfOrders(new Orders(idCustomer,share.getID(),checkIn,checkOut));
                idOrder = dao.getNewIdOfOrders();
            }else{
                idOrder = orders1.getId();
            }
        }
        OrderDetail orderDetail = new OrderDetail(idRoom,idOrder,
                amountOfPeople,checkIn,checkOut);
        if(status == 2){
            orderDetail.setPrepay(total);
            orderDetail.setStatus(status);
        }

        dao.insertOfOrderDetail(orderDetail);
        dao.reLoadEndOfOrders(idOrder);
        idOrderDetail = dao.getNewIdOfOrderDetail();
        for(ServiceOrder x : serviceOrderList){
            if(x.getAmount() != 0){
                x.setOrderDetailID(idOrderDetail);
                dao.insertOfServiceOrder(x);
            }
        }
        //
        CustomToast.makeText(this, "Đặt thành công !", true).show();
        finish();
    }

    public void handleActionBtnCancel(View view) {
        finish();
    }
    private void loadTotal(){
        total = dao.getPriceWithIdOfRooms(idRoom)  * amount_date + totalService;
        tv_total.setText(format.format(total) + "K");
    }
    public void handleActionBtnAddService(View view){
        int index = sp_service.getSelectedItemPosition();
        Services sv = serviceList.get(index);
        serviceList1.add(sv);
        totalService += sv.getPrice();
        for(int i = 0; i < serviceOrderList.size(); i++){
            if(serviceOrderList.get(i).getServiceId() == sv.getId()){
                serviceOrderList.get(i).setAmount(serviceOrderList.get(i).getAmount() + 1);
                break;
            }
        }
        itemServiceOrderAdapter.notifyDataSetChanged();
        loadTotal();
    }
    @Override
    public void cancel(int position) {
        Services sv = serviceList1.get(position);
        totalService -= sv.getPrice();
        serviceList1.remove(position);
;        for(int i = 0; i < serviceOrderList.size(); i++){
            if(serviceOrderList.get(i).getServiceId() == sv.getId()){
                serviceOrderList.get(i).setAmount(serviceOrderList.get(i).getAmount() - 1);
                break;
            }
        }
        itemServiceOrderAdapter.notifyDataSetChanged();
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
        People people = dao.getObjOfUser(id);
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