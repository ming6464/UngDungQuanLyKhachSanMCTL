package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.Date;
import java.util.List;

public class AdapterThongKeLoaiPhong extends RecyclerView.Adapter<AdapterThongKeLoaiPhong.ViewHolder> {
    private Context context;
    private List<Categories> list;
    private List<Integer> listCount;
    KhachSanDAO db;
    //

    public AdapterThongKeLoaiPhong(Context context, List<Categories> list, List<Integer> listCount) {
        this.context = context;
        this.list = list;
        this.listCount = listCount;
        db = KhachSanDB.getInstance(context).getDAO();
    }

    @Override
    public AdapterThongKeLoaiPhong.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loaiphong_thongke, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterThongKeLoaiPhong.ViewHolder holder, int position) {
        Categories loai = list.get(holder.getAdapterPosition());
        holder.tvName.setText(loai.getName());
        holder.tvSoLuong.setText(listCount.get(holder.getAdapterPosition()) + "");
        //
        int pos = loai.getId();
        if (pos % 2 == 0) {
            holder.back.setBackgroundColor(Color.WHITE);
        } else {
            holder.back.setBackgroundResource(R.color.itemServicele);
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