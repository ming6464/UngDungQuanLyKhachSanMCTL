package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    private List<OrderDetail> list;
    private View view;
    private KhachSanDAO dao;
    private OnEvent action;
    public OrderDetailAdapter(Context context, OnEvent action){
        dao = KhachSanDB.getInstance(context).getDAO();
        this.action  = action;
    }

    public void setData(List<OrderDetail> list){
        this.list = list;
        notifyDataSetChanged();
    }

    interface OnEvent{
        void click(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail1,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder h, int position) {
        OrderDetail obj = list.get(position);
        h.
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_room,tv_fullName,tv_phoneNumber,tv_checkIn,tv_hourCheckIn,tv_checkOut,tv_hourCheckOut,tv_status,tv_total;
        public MyViewHolder(@NonNull View iv) {
            super(iv);
            tv_room = iv.findViewById(R.id.itemOrderDetail1_tv_room);
            tv_fullName = iv.findViewById(R.id.itemOrderDetail1_tv_fullName);
            tv_phoneNumber = iv.findViewById(R.id.itemOrderDetail1_tv_phoneNumber);
            tv_checkIn = iv.findViewById(R.id.itemOrderDetail1_tv_checkIn);
            tv_hourCheckIn = iv.findViewById(R.id.itemOrderDetail1_tv_hourCheckIn);
            tv_checkOut = iv.findViewById(R.id.itemOrderDetail1_tv_checkOut);
            tv_hourCheckOut = iv.findViewById(R.id.itemOrderDetail1_tv_hourCheckOut);
            tv_status = iv.findViewById(R.id.itemOrderDetail1_tv_status);
            tv_total = iv.findViewById(R.id.itemOrderDetail1_tv_total);
        }
    }
}
