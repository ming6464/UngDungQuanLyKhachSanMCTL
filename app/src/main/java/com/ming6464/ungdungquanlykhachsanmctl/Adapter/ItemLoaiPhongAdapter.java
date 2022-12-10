package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemLoaiPhongAdapter extends RecyclerView.Adapter<ItemLoaiPhongAdapter.MyViewHolder> {
    private List<Categories> list;
    private NumberFormat format;
    public ItemLoaiPhongAdapter(){
        format = NumberFormat.getInstance(new Locale("en","EN"));
    }

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
        holder.amountOfPeople.setText("Số Người Tối Đa :  " + obj.getAmountOfPeople());
        holder.name.setText(obj.getName());
        holder.price.setText("Giá Tiền :  " + format.format(obj.getPrice()) + "K");
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView name,price,amountOfPeople;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemLoaiPhong_tv_name);
            price = itemView.findViewById(R.id.itemLoaiPhong_tv_price);
            amountOfPeople = itemView.findViewById(R.id.itemLoaiPhong_tv_amountOfPeople);
        }
    }
}
