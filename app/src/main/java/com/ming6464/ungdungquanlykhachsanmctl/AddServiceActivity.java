package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemService1Adapter;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemService2Adapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.Fragment_HoaDon_Phong;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddServiceActivity extends AppCompatActivity implements ItemService2Adapter.OnEventOfItemService2Adapter, ItemService1Adapter.EventOfItemService1Adapter {
    private List<Services> list,list1;
    private List<ServiceOrder> list2;
    private int idOrderDetail,total = 0;
    private String idRoom;
    private TextView tv_total;
    private Toolbar tb;
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
        format = NumberFormat.getInstance(new Locale("vi","VN"));
        idRoom = getIntent().getStringExtra(Fragment_HoaDon_Phong.KEY_ROOMID);
        idOrderDetail = dao.getWithRoomIdOfOrderDetail(idRoom).getOrderID();
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
        list2.set(position,svo);
        list1.add(sv);
        total += sv.getPrice();
        itemService1Adapter.notifyItemInserted(list1.size() - 1);
        itemService2Adapter.notifyItemChanged(position);
        loadTotal();
    }

    @Override
    public void cancel(int position) {
        Services sv = list1.get(position);
        int index = list.indexOf(sv);
        ServiceOrder svo = list2.get(index);
        svo.setAmount(svo.getAmount() - 1);
        list2.set(position,svo);
        list1.remove(position);
        total += sv.getPrice();
        itemService1Adapter.notifyItemRemoved(position);
        itemService2Adapter.notifyItemChanged(position);
        loadTotal();
    }

    public void handleActionUpdate(View view) {
        for(ServiceOrder x : list2){
            if(x.getAmount() > 0)
                dao.insertOfServiceOrder(x);
        }
        finish();
    }

    private void loadTotal(){
        tv_total.setText(format.format(total));
    }

    public void handleActionBack(View view) {
        finish();
    }
}