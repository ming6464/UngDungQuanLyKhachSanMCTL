package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemServiceThongKeAdapter extends RecyclerView.Adapter<ItemServiceThongKeAdapter.MyViewHolder> {
    private List<String> listName;
    private int [] arr_duLieu;
    private int width;
    private NumberFormat format;
    private ViewGroup.LayoutParams params;

    public ItemServiceThongKeAdapter(){
        format = NumberFormat.getInstance(new Locale("en","EN"));
    }
    public void setData(List<String> listName,int [] arr_duLieu,int width){
        this.listName = listName;
        this.arr_duLieu = arr_duLieu;
        this.width = width;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_thongke,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(listName.get(position));
        holder.tv_total.setText(format.format(arr_duLieu[position*2]) + "K");
        int index = arr_duLieu[position * 2 + 1];
        holder.tv_amount.setText(String.valueOf(index));
        params = holder.tv_amount.getLayoutParams();
        params.width = 35 + width * index;
        holder.tv_amount.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        if(listName == null)
            return 0;
        return listName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_total,tv_amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.itemServiceThongKe_tv_name);
            tv_total = itemView.findViewById(R.id.itemServiceThongKe_tv_total);
            tv_amount = itemView.findViewById(R.id.itemServiceThongKe_tv_amount);
        }
    }
}
