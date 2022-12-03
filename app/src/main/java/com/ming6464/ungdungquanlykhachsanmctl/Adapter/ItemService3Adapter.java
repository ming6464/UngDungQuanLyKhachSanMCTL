package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemService3Adapter extends RecyclerView.Adapter<ItemService3Adapter.MyViewHolder> {
    private List<Services> serviceList;
    private List<ServiceOrder> serviceOrderList;
    private NumberFormat format;

    public ItemService3Adapter(){
        format = NumberFormat.getInstance(new Locale("en","EN"));
    }


    public void setData(List<Services> serviceList,List<ServiceOrder> serviceOrderList){
        this.serviceList = serviceList;
        this.serviceOrderList = serviceOrderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_3,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Services sv = serviceList.get(position);
        ServiceOrder svo = serviceOrderList.get(position);
        holder.tv_price.setText(format.format(sv.getPrice()) + "K");
        holder.tv_name.setText(sv.getName());
        holder.tv_amount.setText(String.valueOf(svo.getAmount()));
    }

    @Override
    public int getItemCount() {
        if(serviceList == null)
            return 0;
        return serviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_amount,tv_price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.itemService3_tv_name);
            tv_amount = itemView.findViewById(R.id.itemService3_tv_amount);
            tv_price = itemView.findViewById(R.id.itemService3_tv_price);
        }
    }
}
