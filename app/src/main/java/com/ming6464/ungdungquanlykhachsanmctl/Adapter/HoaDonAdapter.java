package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.HoaDon;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import org.w3c.dom.Text;

import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder>{
    public List<HoaDon> mListHoadon;
    public  void setData(List<HoaDon> list){
        this.mListHoadon = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don,parent,false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDon hoaDon = mListHoadon.get(position);
        if (hoaDon == null){
            return;
        }
        holder.tv_name.setText("Name: "+hoaDon.getTenKh());
        holder.tv_ngayNhan.setText("Ngày nhận: "+hoaDon.getNgayNhan());
        holder.tv_ngayTra.setText("Ngày Trả: "+hoaDon.getNgayTra());
        holder.tv_tenPhong.setText("Tên Phòng: "+hoaDon.getTenPhong());
        holder.tv_dichVu.setText("Dịch Vụ: "+hoaDon.getDichVu());
        holder.tv_status.setText("Trạng thái: "+hoaDon.getStatus());
        holder.tv_tongTien.setText("Tổng Tiền: "+hoaDon.getTongTien());
    }

    @Override
    public int getItemCount() {
        if (mListHoadon != null){
            return mListHoadon.size();
        }
        return 0;
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_ngayNhan,tv_ngayTra,tv_tenPhong,tv_dichVu,tv_tongTien,tv_status;
        private Button btn_chiTiet;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_hd_tenKH);
            tv_ngayNhan = itemView.findViewById(R.id.tv_hd_ngayNhan);
            tv_ngayTra = itemView.findViewById(R.id.tv_hd_ngayTra);
            tv_tenPhong = itemView.findViewById(R.id.tv_hd_tenPhong);
            tv_dichVu = itemView.findViewById(R.id.tv_hd_dichVu);
            tv_status = itemView.findViewById(R.id.tv_hd_trangThai);
            tv_tongTien = itemView.findViewById(R.id.tv_hd_tongTien);
            btn_chiTiet = itemView.findViewById(R.id.btn_hd_chiTiet);

        }
    }
}
