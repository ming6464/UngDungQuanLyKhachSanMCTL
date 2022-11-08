package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.R;
import java.util.List;

public class LoaiPhongAdapter extends RecyclerView.Adapter<LoaiPhongAdapter.MyViewHolder> {
    private List<Categories> list;
    public void setData(List<Categories> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_phong,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Categories obj = list.get(position);
        holder.amountOfPeople.setText(String.valueOf(obj.getAmountOfPeople()));
        holder.name.setText(obj.getName());
        int id = obj.getId();
        holder.id.setText(String.valueOf(id));
        if(id < 10)
            holder.id.setText("0" + id);
        holder.price.setText(String.valueOf(obj.getPrice()));
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView id,name,price,amountOfPeople;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.itemLoaiPhong_tv_id);
            name = itemView.findViewById(R.id.itemLoaiPhong_tv_name);
            price = itemView.findViewById(R.id.itemLoaiPhong_tv_price);
            amountOfPeople = itemView.findViewById(R.id.itemLoaiPhong_tv_amountOfPeople);
        }
    }
}
