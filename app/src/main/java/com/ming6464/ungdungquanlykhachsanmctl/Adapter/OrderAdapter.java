package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{
    private EventOfOrderAdapter action;
    private List<Orders> list;
    private KhachSanDAO dao;
    private NumberFormat format;
    private SimpleDateFormat sdf,sdf1;

    public OrderAdapter(Context context,EventOfOrderAdapter action){
        this.action = action;
        dao = KhachSanDB.getInstance(context).getDAO();
        format = NumberFormat.getInstance(new Locale("vi","VN"));
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf1 = new SimpleDateFormat("HH");
    }

    public void setData(List<Orders> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder h, int position) {
        Orders obj = list.get(position);
        h.tv_fullName.setText("Họ tên :  " + dao.getWithIdOfUser(obj.getCustomID()).getFullName());
        h.tv_total.setText(format.format(obj.getTotal()) + "đ");
        String status = "";
        if(obj.getStatus() == 0){
            h.layout.setBackgroundResource(R.color.hoadon_chuathanhtoan);
            status = "Chưa Thanh Toán";
        }
        else if(obj.getStatus() == 2){
            h.layout.setBackgroundResource(R.color.hoadon_dattruoc);
            status = "Đặt Trước";
        }
        else if(obj.getStatus() == 3){
            h.layout.setBackgroundResource(R.color.hoadon_huyphong);
            status = "Huỷ";
        }else{
            status = "Thanh Toán";
            h.layout.setBackgroundResource(R.color.hoadon_thanhtoan);
        }
        h.tv_status.setText(status);
        h.tv_checkIn.setText("Ngày Nhập :  " +sdf.format(dao.getMinStatDateWithIdOrderOfOrderDetail(obj.getId())));
        h.tv_hourCheckIn.setText("Giờ :  " +sdf1.format(dao.getMinStatDateWithIdOrderOfOrderDetail(obj.getId())));
        h.tv_checkOut.setText("Ngày Trả :  " +sdf.format(dao.getMaxEndDateWithIdOrderOfOrderDetail(obj.getId())));
        h.tv_hourCheckOut.setText("Giờ :  " +sdf1.format(dao.getMaxEndDateWithIdOrderOfOrderDetail(obj.getId())));

        String rooms = "";
        List<Rooms> list1 = dao.getListWithOrderIdOfRooms(obj.getId());
        for(int i = 0; i < list1.size(); i ++){
            rooms += list1.get(i).getName();
            if((list1.size() - i) > 1 ){
                rooms += ", ";
            }
        }
        h.tv_rooms.setText("Số Phòng :  " + rooms);

        h.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.show(h.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public interface EventOfOrderAdapter{
        void show(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fullName,tv_checkIn,tv_hourCheckIn,tv_checkOut,tv_hourCheckOut,tv_status,tv_total,tv_rooms;
        private Button btn_detail;
        private LinearLayoutCompat layout;
        public MyViewHolder(@NonNull View i) {
            super(i);
            tv_fullName = i.findViewById(R.id.itemHoaDon_tv_fullName);
            tv_checkIn = i.findViewById(R.id.itemHoaDon_tv_checkIn);
            tv_checkOut = i.findViewById(R.id.itemHoaDon_tv_checkOut);
            tv_status = i.findViewById(R.id.itemHoaDon_tv_status);
            tv_total = i.findViewById(R.id.itemHoaDon_tv_total);
            tv_rooms = i.findViewById(R.id.itemHoaDon_tv_rooms);
            btn_detail = i.findViewById(R.id.itemHoaDon_btn_detail);
            tv_hourCheckIn = i.findViewById(R.id.itemHoaDon_tv_hourCheckIn);
            tv_hourCheckOut = i.findViewById(R.id.itemHoaDon_tv_hourCheckOut);
            layout = i.findViewById(R.id.itemHoaDon_layout_linearHoaDon);
        }
    }
}
