package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemOrderDetail1Adapter;
import com.ming6464.ungdungquanlykhachsanmctl.AddServiceActivity;
import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.HoaDonChiTietActivity;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HoaDonPhongFragment extends Fragment implements ItemOrderDetail1Adapter.OnEventOfOrderDetailAdpater {
    private List<OrderDetail> list;
    private KhachSanDAO dao;
    private Spinner sp_status;
    private SwipeRefreshLayout rf_rcHoaDonPhong;
    private ItemOrderDetail1Adapter adapter;
    public static final String KEY_ORDERDETAILID = "KEY_ORDERDETAILID";
    public static HoaDonPhongFragment newInstance() {
        return new HoaDonPhongFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoa_don_phong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        RecyclerView rc_orderDetail = view.findViewById(R.id.fragHoaDonPhong_rc);
        sp_status = view.findViewById(R.id.fragHoaDonPhong_sp_status);
        rf_rcHoaDonPhong = view.findViewById(R.id.fragHoaDonPhong_rf_rc);
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        adapter = new ItemOrderDetail1Adapter(requireContext(),this);
        rc_orderDetail.setAdapter(adapter);
        rc_orderDetail.setLayoutManager(new LinearLayoutManager(requireContext()));
        handleSpinner();
        handleAction();
    }

    private void handleAction() {
        rf_rcHoaDonPhong.setOnRefreshListener(() -> {
            int position = sp_status.getSelectedItemPosition();
            loadData(position);
            rf_rcHoaDonPhong.setRefreshing(false);
        });
    }

    private void handleSpinner() {
        List<String> statusList = new ArrayList<>();
        statusList.add("Chưa Trả Phòng");
        statusList.add("Trả Phòng");
        statusList.add("Huỷ");
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,statusList);
        sp_status.setAdapter(arrayAdapter);
        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(sp_status.getSelectedItemPosition());
    }

    @Override
    public void click(int position) {
        OrderDetail obj = list.get(position);
        int status = obj.getStatus();
        if(status == 4 || status == 1){
            Intent intent = new Intent(requireContext(), HoaDonChiTietActivity.class);
            intent.putExtra(HoaDonTongFragment.KEY_ORDER,dao.getObjOfOrders(obj.getOrderID()));
            startActivity(intent);
        }else {
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.dialog_bottomsheet);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getAttributes().windowAnimations = R.style.dialog_slide_bottom;
            window.setGravity(Gravity.BOTTOM);
            //
            Button btn_toOrder = dialog.findViewById(R.id.dialogBottmsheet_btn_toOrder);
            //
            btn_toOrder.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), HoaDonChiTietActivity.class);
                intent.putExtra(HoaDonTongFragment.KEY_ORDER,dao.getObjOfOrders(obj.getOrderID()));
                startActivity(intent);
                dialog.cancel();
            });
            if(status == 0){
                Button btn_checkOut = dialog.findViewById(R.id.dialogBottmsheet_btn_checkOut),
                        btn_addService = dialog.findViewById(R.id.dialogBottmsheet_btn_addService);
                btn_checkOut.setVisibility(View.VISIBLE);
                btn_addService.setVisibility(View.VISIBLE);
                btn_checkOut.setOnClickListener(v -> {
                    obj.setStatus(1);
                    dao.updateOfOrderDetail(obj);
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                    CustomToast.makeText(requireContext(),"Phòng " + obj.getRoomID() + " đã trả thành công !",true).show();
                    dialog.cancel();
                });
                btn_addService.setOnClickListener(v -> {
                    Intent intent = new Intent(requireContext(), AddServiceActivity.class);
                    intent.putExtra(KEY_ORDERDETAILID,obj.getId());
                    startActivity(intent);
                    dialog.cancel();
                });
            }else{
                Button btn_checkIn = dialog.findViewById(R.id.dialogBottmsheet_btn_checkIn),
                        btn_cancel = dialog.findViewById(R.id.dialogBottmsheet_btn_cancel);
                btn_cancel.setVisibility(View.VISIBLE);
                if(status == 3){
                    btn_checkIn.setVisibility(View.VISIBLE);
                    btn_checkIn.setOnClickListener(v -> {
                        obj.setStatus(0);
                        dao.updateOfOrderDetail(obj);
                        adapter.notifyItemChanged(position);
                        CustomToast.makeText(requireContext(),"Phòng " + obj.getRoomID() + " đã nhận phòng thành công !",true).show();
                        dialog.cancel();
                    });
                }
                btn_cancel.setOnClickListener(v -> {
                    obj.setStatus(4);
                    dao.cancelOfOrderDetail(obj.getId(),new Date(System.currentTimeMillis()));
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                    CustomToast.makeText(requireContext(),"Phòng " + obj.getRoomID() + " đã huỷ phòng thành công !",true).show();
                    dialog.cancel();
                });
            }
            dialog.show();
        }
    }

    private void loadData(int position){
        long currentTime = System.currentTimeMillis();
        list.clear();
        for(OrderDetail x : dao.getAllOfOrderDetail()){
            switch (x.getStatus()){
                case 0:
                    if(x.getCheckOut().getTime() < currentTime){
                        x.setStatus(1);
                        dao.updateOfOrderDetail(x);
                    }
                    if(position == 0)
                        list.add(x);
                    break;
                case 1:
                    if(position == 1)
                        list.add(x);
                    break;
                case 2:
                    if(x.getCheckIn().getTime() < currentTime){
                        x.setStatus(3);
                        dao.updateOfOrderDetail(x);
                    }
                    if(position == 0)
                        list.add(x);
                    break;
                case 3:
                    if(x.getCheckOut().getTime() < currentTime){
                        x.setStatus(4);
                        dao.cancelOfOrderDetail(x.getId(),new Date(currentTime));
                    }
                    if(position == 0)
                        list.add(x);
                    break;
                default:
                    if(position == 2)
                        list.add(x);
            }
        }
        adapter.setData(list);
    }

}