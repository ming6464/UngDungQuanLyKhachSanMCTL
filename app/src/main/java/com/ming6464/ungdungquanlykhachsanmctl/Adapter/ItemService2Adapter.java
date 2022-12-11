package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemService2Adapter extends RecyclerView.Adapter<ItemService2Adapter.MyViewHolder> {
    private OnEventOfItemService2Adapter action;
    private KhachSanDAO dao;
    private NumberFormat format;
    private List<ServiceOrder> list;
    public ItemService2Adapter(Context context, OnEventOfItemService2Adapter action){
        this.action  = action;
        dao = KhachSanDB.getInstance(context).getDAO();
        format = NumberFormat.getInstance(new Locale("en","EN"));
    }

    public void setData(List<ServiceOrder> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_2,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder h, int position) {
        Log.d("a.TAG", "onBindViewHolder: "  + position);
        ServiceOrder obj = list.get(position);
        if(position % 2 == 0)
            h.linear_layout.setBackgroundColor(Color.WHITE);
        else{
            h.linear_layout.setBackgroundResource(R.color.itemServicele);
        }
        Services sv = dao.getObjOfServices(obj.getServiceId());
        h.tv_price.setText(format.format(sv.getPrice()) + "K");
        h.tv_amount.setText(String.valueOf(obj.getAmount()));
        h.tv_stt.setText(String.valueOf(position));
        h.tv_total.setText(format.format(sv.getPrice() * obj.getAmount()) + "K");
        h.tv_name.setText(sv.getName());
        h.linear_layout.setOnClickListener(v -> action.add(h.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public interface OnEventOfItemService2Adapter{
        void add(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_stt,tv_name,tv_price,tv_amount,tv_total;
        private LinearLayoutCompat linear_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_stt = itemView.findViewById(R.id.itemService2_tv_stt);
            tv_name = itemView.findViewById(R.id.itemService2_tv_name);
            tv_price = itemView.findViewById(R.id.itemService2_tv_price);
            tv_amount = itemView.findViewById(R.id.itemService2_tv_amount);
            tv_total = itemView.findViewById(R.id.itemService2_tv_total);
            linear_layout = itemView.findViewById(R.id.itemService2_linear_layout);
        }
    }
}
