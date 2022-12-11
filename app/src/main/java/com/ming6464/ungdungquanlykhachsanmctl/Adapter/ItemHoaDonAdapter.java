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
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ItemHoaDonAdapter extends RecyclerView.Adapter<ItemHoaDonAdapter.MyViewHolder>{
    private EventOfOrderAdapter action;
    private List<Orders> list;
    private KhachSanDAO dao;
    private NumberFormat format;
    private SimpleDateFormat sdf,sdf1;

    public ItemHoaDonAdapter(Context context, EventOfOrderAdapter action){
        this.action = action;
        dao = KhachSanDB.getInstance(context).getDAO();
        format = NumberFormat.getInstance(new Locale("en","EN"));
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
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don_tong,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder h, int position) {
        Orders obj = list.get(position);
        People people = dao.getObjOfUser(obj.getCustomID());
        Date startDate = obj.getStartDate(),endDate = obj.getEndDate();
        String status;
        h.tv_fullName.setText(people.getFullName());
        h.tv_phoneNumber.setText("Số Điện Thoại :  " + people.getSDT());
        h.tv_total.setText(format.format(obj.getTotal()) + "K");
        int color;
        if(obj.getStatus() == 2){
            h.layout.setBackgroundResource(R.drawable.background_hoadon_huyphong);
            status = "Huỷ";
            color = Color.BLACK;
            h.tv_start.setVisibility(View.GONE);
            h.tv_hourStart.setVisibility(View.GONE);
        }else if(obj.getStatus() == 1){
            status = "Thanh Toán";
            h.layout.setBackgroundResource(R.drawable.background_hoadon_thanhtoan);
            color = Color.BLACK;
        }else {
            status = "Chưa Thanh Toán";
            color = Color.RED;
            h.layout.setBackgroundResource(R.drawable.background_hoadon_chuathanhtoan);
        }
        h.tv_status.setTextColor(color);
        h.tv_status.setText(status);
        h.tv_start.setText("Ngày Bắt Đầu :  " +sdf.format(startDate));
        h.tv_hourStart.setText("Giờ :  " + sdf1.format(startDate));
        h.tv_end.setText("Ngày Kết Thúc :  " +sdf.format(endDate));
        h.tv_hourEnd.setText("Giờ :  " + sdf1.format(endDate));

        String rooms = "";
        List<Rooms> list1 = dao.getListWithOrderIdOfRooms(obj.getId());
        for(int i = 0; i < list1.size(); i ++){
            rooms += list1.get(i).getId();
            if((list1.size() - i) > 1 ){
                rooms += ", ";
            }
        }
        h.tv_rooms.setText("Số Phòng :  " + rooms);

        h.btn_detail.setOnClickListener(v -> action.show(h.getAdapterPosition()));

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
        private TextView tv_fullName,tv_start,tv_hourStart,tv_end,tv_hourEnd,tv_status,tv_total,tv_rooms,tv_phoneNumber;
        private Button btn_detail;
        private LinearLayoutCompat layout;
        public MyViewHolder(@NonNull View i) {
            super(i);
            tv_fullName = i.findViewById(R.id.itemHoaDonTong_tv_fullName);
            tv_start = i.findViewById(R.id.itemHoaDonTong_tv_start);
            tv_end = i.findViewById(R.id.itemHoaDonTong_tv_end);
            tv_status = i.findViewById(R.id.itemHoaDonTong_tv_status);
            tv_total = i.findViewById(R.id.itemHoaDonTong_tv_total);
            tv_rooms = i.findViewById(R.id.itemHoaDonTong_tv_rooms);
            btn_detail = i.findViewById(R.id.itemHoaDonTong_btn_detail);
            tv_hourStart = i.findViewById(R.id.itemHoaDonTong_tv_hourStart);
            tv_hourEnd = i.findViewById(R.id.itemHoaDonTong_tv_hourEnd);
            layout = i.findViewById(R.id.itemHoaDonTong_layout_linearHoaDon);
            tv_phoneNumber = i.findViewById(R.id.itemHoaDonTong_tv_phoneNumber);
        }
    }
}
