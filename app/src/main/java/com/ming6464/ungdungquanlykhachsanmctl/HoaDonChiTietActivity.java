package com.ming6464.ungdungquanlykhachsanmctl;

import static com.ming6464.ungdungquanlykhachsanmctl.Fragment.HoaDonFragment.KEY_ORDER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
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

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemService3Adapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class HoaDonChiTietActivity extends AppCompatActivity {
    private Orders ordersObj;
    private People customerObj;
    private int total = 0,changeMoney = 0 ,totalRoom = 0, totalService = 0,totalDeposit,color;
    private NumberFormat format;
    private KhachSanDAO dao;
    private ConstraintLayout constrain_order;
    private ProgressBar pg_load;
    private EditText ed_fullName,ed_sex,ed_phoneNumber,ed_CCCD,ed_address,ed_moneyOfCustomer;
    private TextView tv_totalService,tv_totalRoom,tv_total,tv_changeMoney,tv_totalDeposit;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        dao = KhachSanDB.getInstance(this).getDAO();
        ordersObj = (Orders) getIntent().getSerializableExtra(KEY_ORDER);
        customerObj = dao.getObjOfUser(ordersObj.getCustomID());
        anhXa();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                constrain_order.setVisibility(View.VISIBLE);
                pg_load.setVisibility(View.GONE);
            }
        },800);
        format = NumberFormat.getInstance(new Locale("en","EN"));
        handleToolbar();
        handleInfoCustomer();
        handleAction();
        handleInfoOrder();
        loadChange();
    }

    private void handleAction() {
        ed_moneyOfCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loadChange();
            }
        });
    }

    private void loadChange() {
        String s_money = ed_moneyOfCustomer.getText().toString();
        int money = 0,i = -1;
        color = R.color.coNguoi;
        if(!ed_moneyOfCustomer.getText().toString().isEmpty()){
            money = Integer.parseInt(s_money);
        }
        changeMoney = money - total + totalDeposit;
        if(changeMoney >= 0){
            i = 1;
            color = R.color.blue;
        }
        tv_changeMoney.setTextColor(ContextCompat.getColor(this,color));
        tv_changeMoney.setText(format.format(i * changeMoney) + "K");
    }

    private void handleInfoOrder() {
        addOrderDetail();
        total = ordersObj.getTotal();
        int i_total = total - totalDeposit;
        if(i_total < 0){
            i_total = 0;
            ed_moneyOfCustomer.setFocusableInTouchMode(true);
        }
        if(ordersObj.getStatus() != 0){
            findViewById(R.id.actiHDCT_linear_pay).setVisibility(View.GONE);
        }else{
            tv_total.setText(format.format(i_total)  + "K");
        }
        tv_totalService.setText(format.format(totalService) + "K");
        tv_totalRoom.setText(format.format(totalRoom) + "K");
        tv_totalDeposit.setText(format.format(totalDeposit) + "K");
    }

    private void addOrderDetail() {
        TextView tv_room,tv_roomPrice,tv_checkIn,tv_checkOut,tv_serviceFee,tv_roomFee,tv_hours,tv_deposit;
        RecyclerView rc_service;
        LinearLayoutCompat linear = findViewById(R.id.actiHDCT_Linear_orderDetail),linear_orderDetail;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  HH");
        ItemService3Adapter subAdapter;
        for(OrderDetail x : dao.getListWithOrderIdOfOrderDetail(ordersObj.getId())){
            View itemView = LayoutInflater.from(linear.getContext()).inflate(R.layout.item_order_detail2,null);
            tv_room = itemView.findViewById(R.id.itemOrderDetail2_tv_room);
            tv_roomPrice = itemView.findViewById(R.id.itemOrderDetail2_tv_roomPrice);
            tv_hours = itemView.findViewById(R.id.itemOrderDetail2_tv_hours);
            tv_checkIn = itemView.findViewById(R.id.itemOrderDetail2_tv_checkIn);
            tv_checkOut = itemView.findViewById(R.id.itemOrderDetail2_tv_checkOut);
            tv_serviceFee = itemView.findViewById(R.id.itemOrderDetail2_tv_serviceFee);
            tv_roomFee = itemView.findViewById(R.id.itemOrderDetail2_tv_roomFee);
            tv_deposit = itemView.findViewById(R.id.itemOrderDetail2_tv_deposit);
            rc_service = itemView.findViewById(R.id.itemOrderDetail2_rc_service);
            linear_orderDetail = itemView.findViewById(R.id.itemOrderDetail2_linear_orderDetail2);
            ////
            tv_checkIn.setText("Check In :  "  + sdf.format(x.getStartDate()) + "h");
            tv_checkOut.setText("Check Out :  "  + sdf.format(x.getEndDate()) + "h");
            tv_room.setText(x.getRoomID());
            int status = x.getStatus();
            if(status == 0)
                linear_orderDetail.setBackgroundResource(R.drawable.background_hoadon_chuathanhtoan);
            else if(status == 1)
                linear_orderDetail.setBackgroundResource(R.drawable.background_hoadon_thanhtoan);
            else if(status == 2)
                linear_orderDetail.setBackgroundResource(R.drawable.background_hoadon_dattruoc);
            else  if(status == 3)
                linear_orderDetail.setBackgroundResource(R.drawable.background_hoadon_cothenhanphong);
            else
                linear_orderDetail.setBackgroundResource(R.drawable.background_hoadon_huyphong);
            int roomPrice = dao.getCategoryWithRoomId(x.getRoomID()).getPrice();
            tv_roomPrice.setText("Giá Phòng :  " + format.format(roomPrice) + "K");
            int amount_date = (int) (x.getEndDate().getTime() - x.getStartDate().getTime())/(3600000 * 24) + 1;
            tv_hours.setText("Thời Gian :  " + amount_date + "Ngày");
            totalRoom += roomPrice * amount_date;
            tv_roomFee.setText(format.format(roomPrice * amount_date) + "K");
            int serviceFee = dao.getTotalServiceWithOrderDetailId(x.getId());
            tv_deposit.setText(format.format(x.getDeposit()) + "K");
            totalDeposit += x.getDeposit();
            totalService += serviceFee;
            tv_serviceFee.setText(format.format(serviceFee) + "K");
            subAdapter = new ItemService3Adapter();
            subAdapter.setData(dao.getListWithOrderDetailIdOfService(x.getId()),
                    dao.getListWithOrderDetailIdOfServiceOrder(x.getId()));
            rc_service.setAdapter(subAdapter);
            rc_service.setLayoutManager(new LinearLayoutManager(this));
            linear.addView(itemView);
        }
    }

    private void handleInfoCustomer() {
        ed_fullName.setText(customerObj.getFullName());
        ed_CCCD.setText(customerObj.getCCCD());
        ed_address.setText(customerObj.getAddress());
        ed_phoneNumber.setText(customerObj.getSDT());
        String sex = "Nam";
        if(customerObj.getSex() == 0)
            sex  = "Nữ";
        ed_sex.setText(sex);
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
        tv_totalDeposit = findViewById(R.id.actiHDCT_tv_totalDeposit);
        toolbar = findViewById(R.id.actiHDCT_tb);
    }

    public void handleActionBack(View view) {
        this.finish();
    }

    public void handleActionPay(View view) {
        if(!ed_moneyOfCustomer.getText().toString().isEmpty() && changeMoney >= 0){
            ordersObj.setStatus(1);
            dao.checkOutRoomOfOrder(ordersObj.getId());
            constrain_order.setVisibility(View.GONE);
            pg_load.setVisibility(View.VISIBLE);
            findViewById(R.id.actiHDCT_img_back).setVisibility(View.GONE);
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