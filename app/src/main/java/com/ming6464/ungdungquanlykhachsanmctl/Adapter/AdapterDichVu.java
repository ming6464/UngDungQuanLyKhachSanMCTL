package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterDichVu extends RecyclerView.Adapter<AdapterDichVu.ViewHolder> {
    Context context;
    List<Services> listService;

    public AdapterDichVu(Context context, List<Services> listService) {
        this.context = context;
        this.listService = listService;
    }

    public void setData(List<Services> lists) {
        this.listService = lists;
        notifyDataSetChanged();
    }




    @Override
    public AdapterDichVu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_dichvu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDichVu.ViewHolder holder, int position) {
        Services services = listService.get(position);
        if (services == null) {
            return;
        }
        holder.tvPrice.setText("Price: " + listService.get(holder.getAdapterPosition()).getPrice());
        holder.tvName.setText("Name: " + listService.get(holder.getAdapterPosition()).getName());

    }

    @Override
    public int getItemCount() {
        return listService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        CardView cardViewService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            cardViewService = itemView.findViewById(R.id.cardViewService);
        }
    }
}
