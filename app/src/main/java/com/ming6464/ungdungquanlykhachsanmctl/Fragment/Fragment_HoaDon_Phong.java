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
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemOrderDetail1Adapter;
import com.ming6464.ungdungquanlykhachsanmctl.AddServiceActivity;
import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.HoaDonChiTietActivity;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_HoaDon_Phong extends Fragment implements ItemOrderDetail1Adapter.OnEventOfOrderDetailAdpater {
    private List<OrderDetail> list;
    private KhachSanDAO dao;
    private Spinner sp_status;
    private SwipeRefreshLayout refreshLayout;
    private ItemOrderDetail1Adapter adapter;
    public static final String KEY_ROOMID = "KEY_ROOMID";
    public static Fragment_HoaDon_Phong newInstance() {
        Fragment_HoaDon_Phong fragment = new Fragment_HoaDon_Phong();
        return fragment;
    }

    public Fragment_HoaDon_Phong() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__hoa_don__phong, container, false);
        return view;
        //Hello Các bạn
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        RecyclerView rc_orderDetail = view.findViewById(R.id.fragHoaDonPhong_rc);
        sp_status = view.findViewById(R.id.fragHoaDonPhong_sp_status);
        refreshLayout = view.findViewById(R.id.fragHoaDon_rf_recyclerHoaDonPhong);
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        adapter = new ItemOrderDetail1Adapter(requireContext(),this);
        rc_orderDetail.setAdapter(adapter);
        rc_orderDetail.setLayoutManager(new LinearLayoutManager(requireContext()));
        handleSpinner();
        handleAction();
    }

    private void handleAction() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int position = sp_status.getSelectedItemPosition();
                loadData(position);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void handleSpinner() {
        List<String> statusList = new ArrayList<>();
        statusList.add("Tất Cả");
        statusList.add("Đang Sử Dụng");
        statusList.add("Đã Trả Phòng");
        statusList.add("Đặt Trước");
        statusList.add("Có Thể Nhận Phòng");
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
        loadData(0);
    }

    @Override
    public void click(int position) {
        OrderDetail obj = list.get(position);
        int status = obj.getStatus();
        if(status == 4 || status == 1){
            Intent intent = new Intent(requireContext(), HoaDonChiTietActivity.class);
            intent.putExtra(HoaDonFragment.KEY_ORDER,dao.getObjOfOrders(obj.getOrderID()));
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
            btn_toOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(requireContext(), HoaDonChiTietActivity.class);
                    intent.putExtra(HoaDonFragment.KEY_ORDER,dao.getObjOfOrders(obj.getOrderID()));
                    startActivity(intent);
                    dialog.cancel();
                }
            });
            if(status == 0){
                Button btn_checkOut = dialog.findViewById(R.id.dialogBottmsheet_btn_checkOut),
                        btn_addService = dialog.findViewById(R.id.dialogBottmsheet_btn_addService);
                btn_checkOut.setVisibility(View.VISIBLE);
                btn_addService.setVisibility(View.VISIBLE);
                btn_checkOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dao.checkOutOfOrderDetail(obj.getId());
                        obj.setStatus(1);
                        adapter.notifyItemChanged(position);
                        CustomToast.makeText(requireContext(),"Trả phòng thành công !",true).show();
                        dialog.cancel();
                    }
                });
                btn_addService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(requireContext(), AddServiceActivity.class);
                        intent.putExtra(KEY_ROOMID,obj.getRoomID());
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

            }else{
                Button btn_checkIn = dialog.findViewById(R.id.dialogBottmsheet_btn_checkIn),
                        btn_cancel = dialog.findViewById(R.id.dialogBottmsheet_btn_cancel);
                btn_cancel.setVisibility(View.VISIBLE);
                if(status == 3){
                    btn_checkIn.setVisibility(View.VISIBLE);
                    btn_checkIn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            obj.setStatus(0);
                            dao.checkInOfOrderDetail(obj.getId());
                            adapter.notifyItemChanged(position);
                            CustomToast.makeText(requireContext(),"Nhận phòng thành công !",true).show();
                            dialog.cancel();
                        }
                    });
                }
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obj.setStatus(3);
                        dao.cancelOfOrderDetail(obj.getId());
                        adapter.notifyItemChanged(position);
                        CustomToast.makeText(requireContext(),"Phòng " + obj.getRoomID() + " đã huỷ !",true).show();
                        dialog.cancel();
                    }
                });
            }
            dialog.show();
        }
    }

    private void loadData(int position){
        long currentTime = System.currentTimeMillis();
        for(OrderDetail x : dao.getListWithStatusOfOrderDetail(2)){
            if(x.getStartDate().getTime() < currentTime){
                x.setStatus(3);
                dao.updateOfOrderDetail(x);
            }
        }
        if(position == 0)
            list = dao.getAllOfOrderDetail();
        else
            list = dao.getListWithStatusOfOrderDetail(position - 1);
        adapter.setData(list);
    }

}