package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.DichVuFragment;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterDichVu extends RecyclerView.Adapter<AdapterDichVu.ViewHolder> {
    Context context;
    List<Services> listService;
    private NumberFormat format;

    public AdapterDichVu(Context context, List<Services> listService) {
        this.context = context;
        this.listService = listService;
        format = NumberFormat.getInstance(new Locale("en","EN"));
    }

    public void setData(List<Services> lists) {
        this.listService = lists;
        notifyDataSetChanged();
    }




    @Override
    public AdapterDichVu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dichvu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDichVu.ViewHolder holder, int position) {
        Services services = listService.get(position);
        holder.tvPrice.setText(format.format(services.getPrice()) + "K");
        holder.tvName.setText(services.getName());
        holder.tv_STT.setText(String.valueOf(position));
        if(position % 2 == 0)
            holder.linear_title.setBackgroundColor(Color.WHITE);
        else{
            holder.linear_title.setBackgroundResource(R.color.itemServicele);
        }
    }

    @Override
    public int getItemCount() {
        return listService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice,tv_STT;
        private LinearLayoutCompat linear_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tv_STT = itemView.findViewById(R.id.itemService_tv_stt);
            linear_title = itemView.findViewById(R.id.itemService_linear_title);
        }
    }
}
