package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class ItemService1Adapter extends RecyclerView.Adapter<ItemService1Adapter.MyViewHolder>{
    private List<Services> list;
    private EventOfItemService1Adapter action;

    public ItemService1Adapter(EventOfItemService1Adapter action){
        this.action = action;
    }
    public void setData(List<Services> list){
        this.list = list;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_1,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.imgBtn_cancel.setOnClickListener(v -> action.cancel(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public interface EventOfItemService1Adapter{
        void cancel(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageButton imgBtn_cancel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.itemService1_tv_name);
            imgBtn_cancel = itemView.findViewById(R.id.itemService1_imgBtn_cancel);
        }
    }
}
