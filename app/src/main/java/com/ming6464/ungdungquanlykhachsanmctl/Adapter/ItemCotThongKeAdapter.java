package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemCotThongKeAdapter extends RecyclerView.Adapter<ItemCotThongKeAdapter.MyViewHolder> {
    private List<String> listName;
    private List<Integer> listSoLieu;
    private NumberFormat format;
    private int heightView = 0;
    private ViewGroup.LayoutParams params,params1;

    public ItemCotThongKeAdapter(){
        format = NumberFormat.getInstance(new Locale("en","EN"));
    }

    public void setData(List<String> name,List<Integer> listSoLieu,int heightView){
        this.listName = name;
        this.listSoLieu = listSoLieu;
        this.heightView = heightView;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cot_thongke,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int slThanhToan = listSoLieu.get(position + 1),
        total = listSoLieu.get(position),
        slHuy = listSoLieu.get(position + 2);
        holder.tv_name.setText(listName.get(position));
        holder.tv_total.setText(format.format(total) + "K");

        holder.tv_statusSuccess.setVisibility(View.VISIBLE);
        holder.tv_statusSuccess.setText(String.valueOf(slThanhToan));
        params = holder.tv_statusSuccess.getLayoutParams();
        params.height = slThanhToan * heightView;
        holder.tv_statusSuccess.setLayoutParams(params);

        holder.tv_statusCancel.setVisibility(View.VISIBLE);
        holder.tv_statusCancel.setText(String.valueOf(slHuy));
        params1 = holder.tv_statusCancel.getLayoutParams();
        params1.height = slHuy * heightView;
        holder.tv_statusCancel.setLayoutParams(params1);

    }

    @Override
    public int getItemCount() {
        if(listName == null)
            return 0;
        return listName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_total,tv_name,tv_statusSuccess,tv_statusCancel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_total = itemView.findViewById(R.id.itemCotThongKe_tv_total);
            tv_name = itemView.findViewById(R.id.itemCotThongKe_tv_name);
            tv_statusCancel = itemView.findViewById(R.id.itemCotThongKe_tv_statusCancel);
            tv_statusSuccess = itemView.findViewById(R.id.itemCotThongKe_tv_statusSuccess);
        }
    }
}
