package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemService1Adapter;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemService2Adapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.HoaDonPhongFragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddServiceActivity extends AppCompatActivity implements ItemService2Adapter.OnEventOfItemService2Adapter, ItemService1Adapter.EventOfItemService1Adapter {
    private List<Services> list,list1;
    private List<ServiceOrder> list2;
    private int idOrderDetail,total = 0;
    private TextView tv_total;
    private Toolbar tb;
    private String idRoom;
    private NumberFormat format;
    private ItemService2Adapter itemService2Adapter;
    private ItemService1Adapter itemService1Adapter;
    private RecyclerView rc_service1,rc_service2;
    private KhachSanDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        dao = KhachSanDB.getInstance(this).getDAO();
        format = NumberFormat.getInstance(new Locale("en","EN"));
        idOrderDetail = getIntent().getIntExtra(HoaDonPhongFragment.KEY_ORDERDETAILID,0);
        idRoom = dao.getObjOrderDetail(idOrderDetail).getRoomID();
        anhXa();
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        handleRecycler();
    }

    private void handleRecycler() {
        list = dao.getListWithRoomIdOfServices(idRoom);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        for(Services x : list){
            list2.add(new ServiceOrder(x.getId(),idOrderDetail,0));
        }
        itemService1Adapter = new ItemService1Adapter(this);
        itemService2Adapter = new ItemService2Adapter(this,this);

        rc_service2.setAdapter(itemService2Adapter);
        rc_service1.setAdapter(itemService1Adapter);

        rc_service2.setLayoutManager(new LinearLayoutManager(this));
        rc_service1.setHasFixedSize(true);
        rc_service1.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        itemService1Adapter.setData(list1);
        itemService2Adapter.setData(list2);

    }

    private void anhXa() {
        tv_total = findViewById(R.id.actiAddService_tv_total);
        tb = findViewById(R.id.actiAddService_tb);
        rc_service1 = findViewById(R.id.actiAddService_rc_service1);
        rc_service2 = findViewById(R.id.actiAddService_rc_service2);
    }

    @Override
    public void add(int position) {
        Services sv = list.get(position);
        ServiceOrder svo = list2.get(position);
        svo.setAmount(svo.getAmount() + 1);
        list1.add(sv);
        total += sv.getPrice();
        itemService1Adapter.setData(list1);
        itemService2Adapter.notifyItemChanged(position);
        loadTotal();
    }

    @Override
    public void cancel(int position) {
        Services sv = list1.get(position);
        ServiceOrder svo = list2.get(list.indexOf(sv));
        svo.setAmount(svo.getAmount() - 1);
        list1.remove(position);
        total -= sv.getPrice();
        itemService1Adapter.notifyItemRemoved(position);
        itemService2Adapter.setData(list2);
        loadTotal();
    }

    public void handleActionUpdate(View view) {
        boolean check = false;
        for(ServiceOrder x : list2){
            if(x.getAmount() > 0){
                check = true;
                dao.insertOfServiceOrder(x);
            }
        }
        if(check)
            CustomToast.makeText(this,"Dịch vụ đã được thêm !",true).show();
        finish();
    }

    private void loadTotal(){
        tv_total.setText(format.format(total) + "K");
    }

    public void handleActionBack(View view) {
        finish();
    }
}