package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    private List<OrderDetail> list;
    private KhachSanDAO dao;
    private OnEventOfOrderDetailAdpater action;
    private SimpleDateFormat sdf,sdf1;
    private NumberFormat numberFormat;
    public OrderDetailAdapter(Context context, OnEventOfOrderDetailAdpater action){
        dao = KhachSanDB.getInstance(context).getDAO();
        this.action  = action;
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf1 = new SimpleDateFormat("HH");
        numberFormat = NumberFormat.getInstance(new Locale("vi","VN"));
    }

    public void setData(List<OrderDetail> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnEventOfOrderDetailAdpater{
        void click(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail1,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder h, int position) {
        OrderDetail obj = list.get(position);
        People people = dao.getWithIdOfUser(dao.getWithIdOfOrders(obj.getOrderID()).getCustomID());
        Date checkOut = obj.getEndDate(),checkIn = obj.getStartDate();
        h.tv_room.setText(obj.getRoomID());
        h.tv_fullName.setText(people.getFullName());
        h.tv_phoneNumber.setText("Số Điện Thoại :  " + people.getSDT());
        String status = "Đang Sử Dụng";
        if(obj.getStatus() == 2){
            status = "Đặt Trước";
            h.layout_linearOrderDetail.setBackgroundResource(R.color.hoadon_dattruoc);
        }

        h.tv_status.setText(status);
        h.tv_checkIn.setText( "Ngày Nhận :  " + sdf.format(checkIn));
        h.tv_checkOut.setText("Ngày Trả :  "  + sdf.format(checkOut));
        h.tv_hourCheckOut.setText("Giờ :  "  + sdf1.format(checkOut) + "h");
        h.tv_hourCheckIn.setText("Giờ :  "  + sdf1.format(checkIn) + "h");
        h.tv_total.setText(numberFormat.format(dao.getCategoryWithRoomId(obj.getRoomID()).getPrice() * ((checkOut.getTime() - checkIn.getTime())/3600000) + dao.getTotalServiceWithOrderDetailId(obj.getId())) + " đ");
        h.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.click(h.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_room,tv_fullName,tv_phoneNumber,tv_checkIn,tv_hourCheckIn,tv_checkOut,tv_hourCheckOut,tv_status,tv_total;
        private Button btn_detail;
        private LinearLayoutCompat layout_linearOrderDetail;
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
            btn_detail = iv.findViewById(R.id.itemHoaDonPhong_btn_detail);
            layout_linearOrderDetail = iv.findViewById(R.id.itemOrderDetail1_layout_linearOrderDetail);
        }
    }
}
