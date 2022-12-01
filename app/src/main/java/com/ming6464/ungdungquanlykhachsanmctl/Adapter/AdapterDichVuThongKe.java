package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class AdapterDichVuThongKe extends RecyclerView.Adapter<AdapterDichVuThongKe.ViewHolder> {
    private List<Services> list;
    private Context context;
    private List<Integer> listCount;
    KhachSanDAO db;

    public AdapterDichVuThongKe(List<Services> list, Context context, List<Integer> listCount) {
        this.list = list;
        this.context = context;
        this.listCount = listCount;
        db = KhachSanDB.getInstance(context).getDAO();
    }

    @Override
    public AdapterDichVuThongKe.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loai_phong_thongke, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDichVuThongKe.ViewHolder holder, int position) {
        Services services = list.get(holder.getAdapterPosition());

        holder.tvName.setText(services.getName());
//        holder.tvSoLuong.setText(listCount.get(holder.getAdapterPosition()) + "");
        holder.tvSoLuong.setText(listCount.get(holder.getAdapterPosition()) + "");

        int pos = services.getId();
        if (pos % 2 == 0) {
            holder.back.setBackgroundResource(R.drawable.red7_borderblack12_corner8);
        } else {
            holder.back.setBackgroundResource(R.color.user_datTruoc);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSoLuong;
        LinearLayout back;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tenLoaiPhong);
            tvSoLuong = itemView.findViewById(R.id.soLuongLoaiPhong);
            back = itemView.findViewById(R.id.back);
        }
    }
}
