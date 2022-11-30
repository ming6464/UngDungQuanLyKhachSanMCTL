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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        adapter = new ItemOrderDetail1Adapter(requireContext(),this);
        rc_orderDetail.setAdapter(adapter);
        rc_orderDetail.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        for(OrderDetail x : dao.getAllOfOrderDetail()){
            if(x.getStatus() != 1)
                list.add(x);
        }
        adapter.setData(list);
    }

    @Override
    public void click(int position) {
        OrderDetail obj = list.get(position);
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_bottomsheet);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getAttributes().windowAnimations = R.style.dialog_slide_bottom;
        window.setGravity(Gravity.BOTTOM);
        //
        Button btn_addService = dialog.findViewById(R.id.dialogBottmsheet_btn_addService),
                btn_toOrder = dialog.findViewById(R.id.dialogBottmsheet_btn_toOrder);
        //
        btn_addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AddServiceActivity.class);
                intent.putExtra(KEY_ROOMID,obj.getRoomID());
                startActivity(intent);
                dialog.cancel();
            }
        });
        btn_toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), HoaDonChiTietActivity.class);
                intent.putExtra(HoaDonFragment.KEY_ORDER,dao.getWithIdOfOrders(obj.getOrderID()));
                startActivity(intent);
                dialog.cancel();
            }
        });
        if(obj.getStatus() == 0){
            Button btn_checkOut = dialog.findViewById(R.id.dialogBottmsheet_btn_checkOut);
            btn_checkOut.setVisibility(View.VISIBLE);
            btn_checkOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dao.checkOutOfOrderDetail(obj);
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                    CustomToast.makeText(requireContext(),"Trả phòng thành công !",true).show();
                    dialog.cancel();
                }
            });

        }else if(obj.getStatus() == 3){
            Button btn_checkIn = dialog.findViewById(R.id.dialogBottmsheet_btn_checkIn);
            btn_checkIn.setVisibility(View.VISIBLE);
            btn_checkIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(requireContext(), "CheckIn !", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
        }
        dialog.show();
    }

}