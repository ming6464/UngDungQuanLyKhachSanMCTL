package com.ming6464.ungdungquanlykhachsanmctl;

import static com.ming6464.ungdungquanlykhachsanmctl.Fragment.HoaDonFragment.KEY_ORDER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ServiceOfOrderDetailAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class HoaDonChiTietActivity extends AppCompatActivity {
    private Orders ordersObj;
    private People customerObj;
    private int total = 0,changeMoney = 0 ,totalRoom = 0, totalService = 0;
    private NumberFormat format;
    private KhachSanDAO dao;
    private ConstraintLayout constrain_order;
    private ProgressBar pg_load;
    private EditText ed_fullName,ed_sex,ed_phoneNumber,ed_CCCD,ed_address,ed_moneyOfCustomer;
    private TextView tv_totalService,tv_totalRoom,tv_total,tv_changeMoney;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        dao = KhachSanDB.getInstance(this).getDAO();
        ordersObj = (Orders) getIntent().getSerializableExtra(KEY_ORDER);
        customerObj = dao.getWithIdOfUser(ordersObj.getCustomID());
        anhXa();
        if(ordersObj.getStatus() != 0){
            findViewById(R.id.actiHDCT_linear_pay).setVisibility(View.GONE);
            findViewById(R.id.actiHDCT_linear_changeMoney).setVisibility(View.GONE);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isShowOrder(true);
            }
        },800);
        handleToolbar();
        handleInfoCustomer();
        handleInfoOrder();
        handleAction();
    }

    private void isShowOrder(boolean b) {
        if(b){
            constrain_order.setVisibility(View.VISIBLE);
            pg_load.setVisibility(View.GONE);
            return;
        }
        constrain_order.setVisibility(View.GONE);
        pg_load.setVisibility(View.VISIBLE);
    }

    private void handleAction() {
        ed_moneyOfCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String money = ed_moneyOfCustomer.getText().toString();
                if(money.isEmpty()){
                    tv_changeMoney.setText("0 đ");
                }else{
                    changeMoney = Integer.parseInt(money) - total;
                    tv_changeMoney.setText(format.format(changeMoney) + " đ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void handleInfoOrder() {
        addOrderDetail();
        total = ordersObj.getTotal();
        format = NumberFormat.getInstance(new Locale("vi","VN"));
        tv_total.setText(format.format(total)  + " đ");
        tv_totalService.setText(format.format(totalService) + " đ");
        tv_totalRoom.setText(format.format(totalRoom) + " đ");
    }

    private void addOrderDetail() {
        TextView tv_room,tv_roomPrice,tv_checkIn,tv_checkOut,tv_serviceFee,tv_roomFee,tv_hours;
        RecyclerView rc_service;
        LinearLayoutCompat linear = findViewById(R.id.actiHDCT_Linear_orderDetail);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  HH");
        NumberFormat format = NumberFormat.getInstance(new Locale("vi","VN"));
        ServiceOfOrderDetailAdapter subAdapter;
        for(OrderDetail x : dao.getListWithOrderIdOfOrderDetail(ordersObj.getId())){
            View itemView = LayoutInflater.from(linear.getContext()).inflate(R.layout.item_order_detail,null);
            tv_room = itemView.findViewById(R.id.itemOrderDetail_tv_room);
            tv_roomPrice = itemView.findViewById(R.id.itemOrderDetail_tv_roomPrice);
            tv_hours = itemView.findViewById(R.id.itemOrderDetail_tv_hours);
            tv_checkIn = itemView.findViewById(R.id.itemOrderDetail_tv_checkIn);
            tv_checkOut = itemView.findViewById(R.id.itemOrderDetail_tv_checkOut);
            tv_serviceFee = itemView.findViewById(R.id.itemOrderDetail_tv_serviceFee);
            tv_roomFee = itemView.findViewById(R.id.itemOrderDetail_tv_roomFee);
            rc_service = itemView.findViewById(R.id.itemOrderDetail_rc_service);
            ////
            tv_checkIn.setText("Check In :  "  + sdf.format(x.getStartDate()) + "h");
            tv_checkOut.setText("Check In :  "  + sdf.format(x.getEndDate()) + "h");
            tv_room.setText(dao.getWithIDOfRooms(x.getRoomID()).getName());
            int roomPrice = dao.getCategoryWithRoomId(x.getRoomID()).getPrice();
            tv_roomPrice.setText("Giá Phòng :  " + format.format(roomPrice) + " đ");
            int hours = (int) (x.getEndDate().getTime() - x.getStartDate().getTime())/3600000;
            tv_hours.setText(hours + "h");
            totalRoom += roomPrice * hours;
            tv_roomFee.setText(format.format(roomPrice * hours) + " đ");
            int serviceFee = dao.getTotalServiceWithOrderDetailId(x.getId());
            totalService += serviceFee;
            tv_serviceFee.setText(format.format(serviceFee) + " đ");
            subAdapter = new ServiceOfOrderDetailAdapter();
            subAdapter.setData(dao.getListWithOrderDetailIdOfService(x.getId()),
                    dao.getListWithOrderDetailIdOfServiceOrder(x.getId()));
            rc_service.setAdapter(subAdapter);
            rc_service.setLayoutManager(new LinearLayoutManager(this));
            linear.addView(itemView);
        }
    }

    private void handleInfoCustomer() {
        ed_fullName.setText("  " + customerObj.getFullName());
        ed_CCCD.setText("  " + customerObj.getCCCD());
        ed_address.setText("  " + customerObj.getAddress());
        ed_phoneNumber.setText("  " + customerObj.getSDT());
        String sex = "Nam";
        if(customerObj.getSex() == 0)
            sex  = "Nữ";
        ed_sex.setText("  " + sex);
    }

    private void handleToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void anhXa() {
        pg_load = findViewById(R.id.actiHDCT_pg_load);
        constrain_order = findViewById(R.id.actiHDCT_contrains_order);
        ed_fullName = findViewById(R.id.actiHDCT_ed_fullName);
        ed_sex = findViewById(R.id.actiHDCT_ed_Sex);
        ed_phoneNumber = findViewById(R.id.actiHDCT_ed_phoneNumber);
        ed_CCCD = findViewById(R.id.actiHDCT_ed_CCCD);
        ed_address = findViewById(R.id.actiHDCT_ed_address);
        ed_moneyOfCustomer = findViewById(R.id.actiHDCT_ed_moneyOfCustomer);
        tv_total = findViewById(R.id.actiHDCT_tv_total);
        tv_totalRoom = findViewById(R.id.actiHDCT_tv_totalRoom);
        tv_totalService = findViewById(R.id.actiHDCT_tv_totalService);
        tv_changeMoney = findViewById(R.id.actiHDCT_tv_changeMoney);
        toolbar = findViewById(R.id.actiHDCT_tb);
    }

    public void handleActionBack(View view) {
        this.finish();
    }

    public void handleActionPay(View view) {
        if(!ed_moneyOfCustomer.getText().toString().isEmpty() && changeMoney >= 0){
            ordersObj.setStatus(1);
            dao.checkOutRoomOfOrder(ordersObj.getId());
            isShowOrder(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    CustomToast.makeText(HoaDonChiTietActivity.this,"Thanh toán thành công !",true).show();
                    finish();
                }
            },1000);
        }
        else
            CustomToast.makeText(this,"Khách đưa thiếu !",false).show();
    }
}